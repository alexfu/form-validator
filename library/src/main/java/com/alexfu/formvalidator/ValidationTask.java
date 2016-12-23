package com.alexfu.formvalidator;

import android.widget.TextView;

import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public class ValidationTask {
  private final List<ValidationRule> rules;
  private final TextView view;

  ValidationTask(TextView view, List<ValidationRule> rules) {
    this.rules = rules;
    this.view = view;
  }

  public ValidationResult validate() {
    String input = view.getText().toString();
    List<String> errorMessages = new ArrayList<>();

    for(ValidationRule rule : rules) {
      if (!rule.isValid(input)) {
        errorMessages.add(rule.errorMessage());
      }
    }

    return new ValidationResult(view, errorMessages);
  }
}
