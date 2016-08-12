package com.alexfu.formvalidator.rules;

import java.util.regex.Pattern;

public class RegexRule implements ValidationRule {
  private final Pattern pattern;
  private String errorMessage;

  public RegexRule(Pattern pattern, String errorMessage) {
    this.pattern = pattern;
    this.errorMessage = errorMessage;
  }

  @Override
  public String errorMessage() {
    return errorMessage;
  }

  @Override
  public boolean isValid(String input) {
    return pattern.matcher(input).matches();
  }
}
