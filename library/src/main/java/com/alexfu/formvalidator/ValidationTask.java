package com.alexfu.formvalidator;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

class ValidationTask {
  private final List<ValidationRule> rules;
  private final EditText editText;

  ValidationTask(EditText editText, List<ValidationRule> rules) {
    this.rules = rules;
    this.editText = editText;
  }

  public ValidationResult validate() {
    String input = editText.getText().toString();
    List<String> errorMessages = new ArrayList<>();

    for(ValidationRule rule : rules) {
      if (!rule.isValid(input)) {
        errorMessages.add(rule.errorMessage());
      }
    }

    return new ValidationResult(editText, errorMessages);
  }
}
