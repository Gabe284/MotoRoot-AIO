package dev.motoroot.aio

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.util.concurrent.Executors

class MainActivity : Activity() {
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var status: TextView
    private lateinit var report: TextView
    private lateinit var runButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val density = resources.displayMetrics.density
        fun dp(value: Int) = (value * density).toInt()

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(20))
        }

        val title = TextView(this).apply {
            text = "MotoRoot AIO"
            textSize = 26f
            setTypeface(typeface, Typeface.BOLD)
        }
        content.addView(title)

        val subtitle = TextView(this).apply {
            text = "Initial development build · Read-only audit"
            textSize = 14f
            setPadding(0, dp(6), 0, dp(18))
        }
        content.addView(subtitle)

        status = TextView(this).apply {
            text = "No audit has run. Tap below to request a root session and inspect device/module state. No changes will be made."
            textSize = 15f
            setPadding(0, 0, 0, dp(14))
        }
        content.addView(status)

        runButton = Button(this).apply {
            text = "Run read-only audit"
            setOnClickListener { runAudit() }
        }
        content.addView(
            runButton,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        report = TextView(this).apply {
            textSize = 12f
            typeface = Typeface.MONOSPACE
            setTextIsSelectable(true)
            setPadding(0, dp(16), 0, dp(24))
        }
        val scroll = ScrollView(this).apply {
            addView(report)
            isFillViewport = true
        }
        content.addView(
            scroll,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        setContentView(content)
    }

    private fun runAudit() {
        runButton.isEnabled = false
        status.text = "Requesting root permission and collecting read-only diagnostics…"
        report.text = ""

        executor.execute {
            val result = RootAudit.run()
            runOnUiThread {
                runButton.isEnabled = true
                result.fold(
                    onSuccess = {
                        status.text = "Audit completed. Results are shown below; nothing was modified."
                        report.text = it
                    },
                    onFailure = {
                        status.text = "Audit could not complete. Check the root-provider prompt/status."
                        report.text = it.message ?: it.javaClass.simpleName
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        executor.shutdownNow()
        super.onDestroy()
    }
}
