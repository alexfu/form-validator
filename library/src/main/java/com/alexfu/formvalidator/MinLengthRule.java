package com.alexfu.formvalidator;

public class MinLengthRule implements ValidationRule {
  public final int length;
  private final String errorMessage;


  public MinLengthRule(int length, String errorMessage) {
    this.length = length;
    this.errorMessage = errorMessage;
  }

  @Override
  public String errorMessage() {
    return errorMessage;
  }

  @Override
  public boolean isValid(String input) {
    return input.length() >= length;
  }
}
