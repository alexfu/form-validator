package com.alexfu.formvalidator;

import android.support.annotation.NonNull;
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
    private boolean autoSetErrors = true;
    private List<ValidationResult> errors = new ArrayList<>();

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

    public void removeRule(TextView view, ValidationRule rule) {
        if (!ruleMap.containsKey(view)) {
            return;
        }
        ruleMap.get(view).remove(rule);
    }

    public void removeAllRules(TextView view) {
        if (!ruleMap.containsKey(view)) {
            return;
        }
        ruleMap.get(view).clear();
    }

    public void setAutoSetErrors(boolean autoSetErrors) {
        this.autoSetErrors = autoSetErrors;
    }

    public void validate() {
        triggerOnBeginFormValidation();
        errors.clear();

        for (int i = 0; i < ruleMap.size(); i++) {
            TextView view = ruleMap.keyAt(i);
            validate(view);
        }

        if (errors.isEmpty()) {
            triggerOnSuccessValidation();
        } else {
            triggerOnFailedValidation(errors);
        }
    }

    private void validate(TextView view) {
        ValidationTask task = new ValidationTask(view, ruleMap.get(view));
        ValidationResult result = task.validate();
        if (autoSetErrors) {
            if (result.isValid()) {
                TextViewHelper.setError(view, null);
            } else {
                TextViewHelper.setError(view, result.errors.get(0));
                errors.add(result);
            }
        }

        triggerOnFieldValidated(result);
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

    private void triggerOnFieldValidated(ValidationResult result) {
        if (callback != null) {
            callback.onFieldValidated(result);
        }
    }

    private void triggerOnBeginFormValidation() {
        if (callback != null) {
            callback.onBeginFormValidation();
        }
    }

    private void triggerOnSuccessValidation() {
        if (callback != null) {
            callback.onSuccessValidation();
        }
    }

    private void triggerOnFailedValidation(List<ValidationResult> errors) {
        if (callback != null) {
            callback.onFailedValidation(errors);
        }
    }

    public interface Callback {
        void onFieldValidated(@NonNull ValidationResult result);
        void onBeginFormValidation();
        void onSuccessValidation();
        void onFailedValidation(@NonNull List<ValidationResult> errors);
    }
}
