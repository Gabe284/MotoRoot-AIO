package dev.motoroot.aio

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

/**
 * First-pass, read-only audit. The command string is fixed in source; no UI
 * values or external input are interpolated into the privileged shell.
 *
 * This class intentionally does not change module state, mounts, properties,
 * permissions, services, or system files.
 */
internal object RootAudit {
    private const val TIMEOUT_SECONDS = 20L

    private val auditScript = """
        echo '=== ROOT SESSION ==='
        id
        echo '=== DEVICE ==='
        printf 'manufacturer='; getprop ro.product.manufacturer
        printf 'model='; getprop ro.product.model
        printf 'device='; getprop ro.product.device
        printf 'android_release='; getprop ro.build.version.release
        printf 'android_sdk='; getprop ro.build.version.sdk
        printf 'build_fingerprint='; getprop ro.build.fingerprint
        printf 'kernel='; uname -r
        printf 'selinux='; getenforce 2>/dev/null
        echo '=== ROOT PROVIDER HINTS ==='
        if [ -d /data/adb/ksu ]; then echo 'kernelSU_data_dir=present'; else echo 'kernelSU_data_dir=absent'; fi
        if [ -d /data/adb/modules ]; then echo 'module_dir=present'; else echo 'module_dir=absent'; fi
        if pm list packages 2>/dev/null | grep -Fq 'com.rifsxd.ksunext'; then
          echo 'ksu_next_manager_package=present'
        else
          echo 'ksu_next_manager_package=not_detected'
        fi
        echo '=== ROOT MODULE INVENTORY (READ-ONLY) ==='
        if [ -d /data/adb/modules ]; then
          for d in /data/adb/modules/*; do
            [ -d "$d" ] || continue
            [ -f "$d/module.prop" ] || continue
            module_id=${d##*/}
            module_name=$(sed -n 's/^name=//p' "$d/module.prop" | head -n 1)
            module_version=$(sed -n 's/^version=//p' "$d/module.prop" | head -n 1)
            module_state=enabled
            [ -e "$d/disable" ] && module_state=disabled
            [ -e "$d/remove" ] && module_state=remove-marked
            printf '%s | %s | %s | %s\n' "$module_id" "$module_name" "$module_version" "$module_state"
          done
        else
          echo 'Module directory unavailable to this root session.'
        fi
        echo '=== END AUDIT ==='
    """.trimIndent()

    fun run(): Result<String> = runCatching {
        val process = ProcessBuilder("su", "-c", auditScript)
            .redirectErrorStream(true)
            .start()

        val readerThread = Thread.currentThread()
        val output = StringBuilder()
        val reader = Thread {
            BufferedReader(InputStreamReader(process.inputStream)).use { stream ->
                var line: String?
                while (stream.readLine().also { line = it } != null) {
                    synchronized(output) {
                        if (output.length < 96_000) {
                            output.append(line).append('\n')
                        }
                    }
                }
            }
        }.apply {
            name = "MotoRoot-Audit-Reader"
            isDaemon = true
            start()
        }

        val finished = process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        if (!finished) {
            process.destroyForcibly()
            throw IllegalStateException("Root audit timed out after $TIMEOUT_SECONDS seconds.")
        }

        reader.join(2_000)
        val report = synchronized(output) { output.toString() }
        if (process.exitValue() != 0) {
            val detail = report.ifBlank { "The root command returned no diagnostic output." }
            throw IllegalStateException("Root audit failed (exit ${process.exitValue()}).\n$detail")
        }
        if (!report.contains("=== ROOT SESSION ===")) {
            throw IllegalStateException(
                "The 'su' command did not return the expected audit output. Root may be denied or the provider may be unsupported.\n$report"
            )
        }
        report
    }
}
