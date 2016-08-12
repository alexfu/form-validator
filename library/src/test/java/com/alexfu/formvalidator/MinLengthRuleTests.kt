package com.alexfu.formvalidator

import com.alexfu.formvalidator.rules.MinLengthRule
import org.jetbrains.spek.api.Spek
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MinLengthRuleTests : Spek({

  describe("min length rule") {
    val rule = MinLengthRule(5, "Must be at least 5 characters long.")

    it("should return true for good input") {
      val result = rule.isValid("12345")
      assertTrue(result)
    }

    it("should return false for bad input") {
      val result = rule.isValid("123")
      assertFalse(result)
    }
  }

})