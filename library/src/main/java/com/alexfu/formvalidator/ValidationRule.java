package com.alexfu.formvalidator;

public interface ValidationRule {
  String errorMessage();
  boolean isValid(String input);
}
