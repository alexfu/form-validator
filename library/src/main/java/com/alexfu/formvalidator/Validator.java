package com.alexfu.formvalidator;

import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator implements ValidationAsyncTask.Callback {
  private ArrayMap<EditText, List<ValidationRule>> ruleMap;
  private ArrayMap<TextWatcher, EditText> textWatchers;
  private Callback callback;

  public interface Callback {
    void onFieldValidationSuccessful(EditText editText);
    void onFieldValidationFailed(EditText editText, List<String> errors);
    void onFormValidationSuccessful();
    void onFormValidationFailed(List<ValidationResult> errorValidations);
  }

  public Validator() {
    ruleMap = new ArrayMap<>();
    textWatchers = new ArrayMap<>();
  }

  public void setCallback(Callback callback) {
    this.callback = callback;
  }

  private void watchForTextChange(EditText editText) {
    if (!textWatchers.containsValue(editText)) {
      TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          // No op
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          // No op
        }

        @Override
        public void afterTextChanged(Editable s) {
          EditText editText = textWatchers.get(this);
          validate(editText);
        }
      };

      editText.addTextChangedListener(watcher);
      textWatchers.put(watcher, editText);
    }
  }

  public void addRule(EditText editText, List<ValidationRule> rules) {
    if (!ruleMap.containsKey(editText)) {
      ruleMap.put(editText, new ArrayList<ValidationRule>());
    }

    ruleMap.get(editText).addAll(rules);
    watchForTextChange(editText);
  }

  public void addRule(EditText editText, ValidationRule...rules) {
    if (!ruleMap.containsKey(editText)) {
      ruleMap.put(editText, new ArrayList<ValidationRule>());
    }

    ruleMap.get(editText).addAll(Arrays.asList(rules));
    watchForTextChange(editText);
  }

  public void validate() {
    ValidationTask[] tasks = new ValidationTask[ruleMap.size()];
    for (int i = 0; i < tasks.length; i++) {
      EditText editText = ruleMap.keyAt(i);
      List<ValidationRule> rules = ruleMap.get(editText);
      tasks[i] = new ValidationTask(editText, rules);
    }
    new ValidationAsyncTask(ValidationAsyncTask.Mode.FORM, this).execute(tasks);
  }

  private void validate(EditText editText) {
    ValidationTask task = new ValidationTask(editText, ruleMap.get(editText));
    new ValidationAsyncTask(ValidationAsyncTask.Mode.FORM, this).execute(task);
  }

  @Override
  public void onTaskCompleted(ValidationAsyncTask.Mode mode, List<ValidationResult> results) {
    if (callback == null) return;
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
}
