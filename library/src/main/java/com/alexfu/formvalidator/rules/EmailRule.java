package com.alexfu.formvalidator.rules;


import com.alexfu.formvalidator.utils.Patterns;

public class EmailRule extends RegexRule {
  public EmailRule(String errorMessage) {
    super(Patterns.EMAIL_ADDRESS, errorMessage);
  }
}
