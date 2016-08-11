package com.alexfu.formvalidator;


import com.alexfu.formvalidator.utils.Patterns;

public class EmailRule implements ValidationRule {
  private final String errorMessage;

  public EmailRule(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public String errorMessage() {
    return errorMessage;
  }

  @Override
  public boolean isValid(String input) {
    return Patterns.EMAIL_ADDRESS.matcher(input).matches();
  }
}
