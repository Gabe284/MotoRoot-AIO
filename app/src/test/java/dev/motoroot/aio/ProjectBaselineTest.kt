package dev.motoroot.aio

import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectBaselineTest {
    @Test
    fun applicationPackageIsStable() {
        assertEquals("dev.motoroot.aio", "dev.motoroot.aio")
    }
}
