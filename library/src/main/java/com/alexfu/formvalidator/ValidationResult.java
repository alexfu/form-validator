package com.alexfu.formvalidator;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    public final TextView view;
    public final List<String> errors = new ArrayList<>();

    public ValidationResult(TextView view) {
        this.view = view;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
