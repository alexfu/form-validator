package com.alexfu.formvalidator;

import android.support.annotation.Nullable;
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

public class Validator {
    private final ArrayMap<TextView, List<ValidationRule>> ruleMap = new ArrayMap<>();
    @Nullable private Callback callback;

    public void setCallback(@Nullable Callback callback) {
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
        int invalidHitCount = 0;
        for (int i = 0; i < ruleMap.size(); i++) {
            TextView view = ruleMap.keyAt(i);
            List<ValidationRule> rules = ruleMap.get(view);
            ValidationResult result = new ValidationTask(view, rules).validate();
            if (result.isValid()) {
                if (callback != null) {
                    callback.onFieldValidationSuccessful(view);
                }
            } else {
                invalidHitCount++;
                if (callback != null) {
                    callback.onFieldValidationFailed(view, result.errors);
                }
            }
        }
        if (invalidHitCount == 0) {
            if (callback != null) {
                callback.onFormValidationSuccessful();
            }
        } else {
            if (callback != null) {
                callback.onFormValidationFailed();
            }
        }
    }

    public void validate(TextView view) {
        ValidationTask task = new ValidationTask(view, ruleMap.get(view));
        ValidationResult result = task.validate();
        if (result.isValid()) {
            if (callback != null) {
                callback.onFieldValidationSuccessful(view);
            }
        } else {
            if (callback != null) {
                callback.onFieldValidationFailed(view, result.errors);
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
