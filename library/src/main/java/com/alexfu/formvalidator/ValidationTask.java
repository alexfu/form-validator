package com.alexfu.formvalidator;

import android.widget.TextView;

import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.List;

class ValidationTask {
    private final List<ValidationRule> rules;
    private final TextView view;

    ValidationTask(TextView view, List<ValidationRule> rules) {
        this.rules = rules;
        this.view = view;
    }

    ValidationResult validate() {
        String input = view.getText().toString();
        ValidationResult result = new ValidationResult(view);

        for (ValidationRule rule : rules) {
            if (!rule.isValid(input)) {
                result.errors.add(rule.errorMessage());
            }
        }

        return result;
    }
}
