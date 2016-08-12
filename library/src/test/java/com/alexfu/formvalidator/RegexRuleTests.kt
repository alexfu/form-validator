package com.alexfu.formvalidator

import com.alexfu.formvalidator.rules.RegexRule
import org.jetbrains.spek.api.Spek
import java.util.regex.Pattern
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RegexRuleTests : Spek({

  describe("regex rule") {
    val rule = RegexRule(Pattern.compile("[0-9]+"), "No match.")

    it("should return true with good input") {
      val result = rule.isValid("123")
      assertTrue(result)
    }

    it("should return true with bad input") {
      val result = rule.isValid("123ABC")
      assertFalse(result)
    }
  }

})
