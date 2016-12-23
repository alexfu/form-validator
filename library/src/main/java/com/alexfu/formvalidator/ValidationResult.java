package com.alexfu.formvalidator;

import android.widget.TextView;

import java.util.List;

public class ValidationResult {
  public final TextView view;
  public final List<String> errors;

  public ValidationResult(TextView view, List<String> errors) {
    this.view = view;
    this.errors = errors;
  }

  public boolean isValid() {
    return errors.isEmpty();
  }
}
