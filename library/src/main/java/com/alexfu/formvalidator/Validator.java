package com.alexfu.formvalidator;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.List;

/**
 * Default validator
 */

public class Validator extends AbsValidator<Void> implements ValidationAsyncTask.Callback {
  private final Callback callback;

  public Validator(Callback callback) {
    this.callback = callback;
  }

  @Override public void addRule(TextView view, List<ValidationRule> rules) {
    super.addRule(view, rules);
    watchForTextChanges(view);
  }

  @Override public void addRule(TextView view, ValidationRule... rules) {
    super.addRule(view, rules);
    watchForTextChanges(view);
  }

  @Override public Void performValidate(ValidationTask[] tasks) {
    new ValidationAsyncTask(ValidationAsyncTask.Mode.FORM, this).execute(tasks);
    return null;
  }

  @Override public Void performValidate(ValidationTask task) {
    new ValidationAsyncTask(ValidationAsyncTask.Mode.INPUT, this).execute(task);
    return null;
  }

  @Override
  public void onTaskCompleted(ValidationAsyncTask.Mode mode, List<ValidationResult> results) {
    int invalidHitCount = 0;

    for (int i = 0; i < results.size(); i++) {
      ValidationResult result = results.get(i);
      if (result.isValid()) {
        callback.onFieldValidationSuccessful(result.view);
      } else {
        invalidHitCount++;
        callback.onFieldValidationFailed(result.view, result.errors);
      }
    }

    if (mode == ValidationAsyncTask.Mode.FORM) {
      if (invalidHitCount == 0) {
        callback.onFormValidationSuccessful();
      } else {
        callback.onFormValidationFailed();
      }
    }
  }

  private void watchForTextChanges(final TextView view) {
    TextWatcher watcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Ignore
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Ignore
      }

      @Override public void afterTextChanged(Editable editable) {
        validate(view);
      }
    };

    view.addTextChangedListener(watcher);
  }

  public interface Callback {
    void onFieldValidationSuccessful(TextView view);
    void onFieldValidationFailed(TextView view, List<String> errors);
    void onFormValidationSuccessful();
    void onFormValidationFailed();
  }
}
