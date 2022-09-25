package junit.system.junit

import org.apache.commons.lang3.SystemUtils
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import org.junit.jupiter.api.Assertions.*

@DisplayName("Tests of annotations for making test-execution conditional")
class ConditionalTestAnnotationsTest {

    @Test
    @DisplayName("The test is only executed on Linux")
    @EnabledOnOs(OS.LINUX)
    fun testOnLinux() {
        assertTrue(SystemUtils.IS_OS_LINUX)
    }
}
