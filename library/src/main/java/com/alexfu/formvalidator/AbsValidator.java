package com.alexfu.formvalidator;

import android.support.v4.util.ArrayMap;
import android.widget.TextView;

import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsValidator<T> {
  private ArrayMap<TextView, List<ValidationRule>> ruleMap;

  protected AbsValidator() {
    ruleMap = new ArrayMap<>();
  }

  public void addRule(TextView view, List<ValidationRule> rules) {
    if (!ruleMap.containsKey(view)) {
      ruleMap.put(view, new ArrayList<ValidationRule>());
    }

    ruleMap.get(view).addAll(rules);
  }

  public void addRule(TextView view, ValidationRule...rules) {
    if (!ruleMap.containsKey(view)) {
      ruleMap.put(view, new ArrayList<ValidationRule>());
    }

    for (int i = 0; i < rules.length; i++) {
      ruleMap.get(view).add(rules[i]);
    }
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
