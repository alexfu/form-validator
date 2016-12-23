package com.alexfu.formvalidator;

import android.widget.EditText;

import java.util.List;

public class DefaultValidationWorker implements ValidationWorker<Void>,ValidationAsyncTask.Callback {
  private final Callback callback;

  public DefaultValidationWorker(Callback callback) {
    this.callback = callback;
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

    for(ValidationResult result : results) {
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
        callback.onFormValidationFailed(results);
      }
    }
  }

  public interface Callback {
    void onFieldValidationSuccessful(EditText editText);
    void onFieldValidationFailed(EditText editText, List<String> errors);
    void onFormValidationSuccessful();
    void onFormValidationFailed(List<ValidationResult> errorValidations);
  }
}
