package com.alexfu.formvalidator;

import android.widget.EditText;

import java.util.List;

public class ValidationResult {
  public final EditText view;
  public final List<String> errors;

  public ValidationResult(EditText view, List<String> errors) {
    this.view = view;
    this.errors = errors;
  }

  public boolean isValid() {
    return errors.isEmpty();
  }
}
