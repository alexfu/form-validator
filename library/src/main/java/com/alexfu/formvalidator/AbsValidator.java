package com.alexfu.formvalidator;

import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class AbsValidator<T> {
  private ArrayMap<EditText, List<ValidationRule>> ruleMap;
  private ArrayMap<TextWatcher, EditText> textWatchers;

  public AbsValidator() {
    ruleMap = new ArrayMap<>();
    textWatchers = new ArrayMap<>();
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

  public T validate() {
    ValidationTask[] tasks = new ValidationTask[ruleMap.size()];
    for (int i = 0; i < tasks.length; i++) {
      EditText editText = ruleMap.keyAt(i);
      List<ValidationRule> rules = ruleMap.get(editText);
      tasks[i] = new ValidationTask(editText, rules);
    }
    return performValidate(tasks);
  }

  private T validate(EditText editText) {
    ValidationTask task = new ValidationTask(editText, ruleMap.get(editText));
    return performValidate(task);
  }

  abstract T performValidate(ValidationTask[] tasks);
  abstract T performValidate(ValidationTask task);
}
