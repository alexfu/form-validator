package com.alexfu.formvalidator;

import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbsValidator<T> {
  private ArrayMap<TextView, List<ValidationRule>> ruleMap;
  private ArrayMap<TextWatcher, TextView> textWatchers;

  protected AbsValidator() {
    ruleMap = new ArrayMap<>();
    textWatchers = new ArrayMap<>();
  }

  protected void watchForTextChange(TextView view) {
    if (!textWatchers.containsValue(view)) {
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
          TextView editText = textWatchers.get(this);
          validate(editText);
        }
      };

      view.addTextChangedListener(watcher);
      textWatchers.put(watcher, view);
    }
  }

  public void addRule(TextView view, List<ValidationRule> rules) {
    if (!ruleMap.containsKey(view)) {
      ruleMap.put(view, new ArrayList<ValidationRule>());
    }

    ruleMap.get(view).addAll(rules);
     watchForTextChange(view);
  }

  public void addRule(TextView view, ValidationRule...rules) {
    if (!ruleMap.containsKey(view)) {
      ruleMap.put(view, new ArrayList<ValidationRule>());
    }

    ruleMap.get(view).addAll(Arrays.asList(rules));
     watchForTextChange(view);
  }

  public T validate() {
    ValidationTask[] tasks = new ValidationTask[ruleMap.size()];
    for (int i = 0; i < tasks.length; i++) {
      TextView view = ruleMap.keyAt(i);
      List<ValidationRule> rules = ruleMap.get(view);
      tasks[i] = new ValidationTask(view, rules);
    }
    return performValidate(tasks);
  }

  public T validate(TextView view) {
    ValidationTask task = new ValidationTask(view, ruleMap.get(view));
    return performValidate(task);
  }

  protected abstract T performValidate(ValidationTask[] tasks);
  protected abstract T performValidate(ValidationTask task);
}
