package com.alexfu.formvalidator;

import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Default validator
 */

public class Validator implements ValidationAsyncTask.Callback {
    private final ArrayMap<TextView, List<ValidationRule>> ruleMap = new ArrayMap<>();
    private final Callback callback;

    public Validator(Callback callback) {
        this.callback = callback;
    }

    public void addRule(TextView view, List<ValidationRule> rules) {
        if (!ruleMap.containsKey(view)) {
            ruleMap.put(view, new ArrayList<ValidationRule>());
        }

        ruleMap.get(view).addAll(rules);
        watchForTextChanges(view);
    }

    public void addRule(TextView view, ValidationRule... rules) {
        if (!ruleMap.containsKey(view)) {
            ruleMap.put(view, new ArrayList<ValidationRule>());
        }

        for (int i = 0; i < rules.length; i++) {
            ruleMap.get(view).add(rules[i]);
        }
        watchForTextChanges(view);
    }

    public void validate() {
        ValidationTask[] tasks = new ValidationTask[ruleMap.size()];
        for (int i = 0; i < tasks.length; i++) {
            TextView view = ruleMap.keyAt(i);
            List<ValidationRule> rules = ruleMap.get(view);
            tasks[i] = new ValidationTask(view, rules);
        }
        performValidate(tasks);
    }

    public void validate(TextView view) {
        ValidationTask task = new ValidationTask(view, ruleMap.get(view));
        performValidate(task);
    }

    public void performValidate(ValidationTask[] tasks) {
        new ValidationAsyncTask(ValidationAsyncTask.Mode.FORM, this).execute(tasks);
    }

    public void performValidate(ValidationTask task) {
        new ValidationAsyncTask(ValidationAsyncTask.Mode.INPUT, this).execute(task);
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
