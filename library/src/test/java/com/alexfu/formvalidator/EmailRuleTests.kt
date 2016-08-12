package com.alexfu.formvalidator

import com.alexfu.formvalidator.rules.EmailRule
import org.jetbrains.spek.api.Spek
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EmailRuleTests : Spek({

  describe("email rule") {
    val emailRule = EmailRule("Invalid email.")

    it("should return false for a bad email") {
      val result = emailRule.isValid("badEmail@gmail")
      assertFalse(result)
    }

    it("should return true for a good email") {
      val result = emailRule.isValid("goodEmail@gmail.com")
      assertTrue(result)
    }
  }

})
