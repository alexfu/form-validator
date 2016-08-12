package com.alexfu.formvalidator.rules;

public interface ValidationRule {
  String errorMessage();
  boolean isValid(String input);
}
