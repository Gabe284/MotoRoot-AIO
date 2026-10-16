package dev.motoroot.aio

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProjectBaselineTest {
    @Test
    fun auditIsReadOnlyAndHasExpectedSections() {
        val script = RootAudit.auditScript
        assertTrue(script.contains("=== ROOT SESSION ==="))
        assertTrue(script.contains("=== DEVICE ==="))
        assertTrue(script.contains("=== ROOT MODULE INVENTORY (READ-ONLY) ==="))
        assertFalse(script.contains("rm -rf"))
        assertFalse(script.contains("setprop "))
        assertFalse(script.contains("touch /data/adb/modules"))
        assertFalse(script.contains("uninstall"))
    }
}
