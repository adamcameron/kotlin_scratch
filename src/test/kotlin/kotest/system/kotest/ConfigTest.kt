package kotest.system.kotest

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.apache.commons.lang3.SystemUtils

class ConfigTest : DescribeSpec( {
    describe("Testing test conditionality") {
        it ("only runs on linux").config(enabled = SystemUtils.IS_OS_LINUX) {
            SystemUtils.IS_OS_LINUX shouldBe true
        }
    }
})
