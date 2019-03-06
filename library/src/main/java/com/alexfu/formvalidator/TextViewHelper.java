package com.alexfu.formvalidator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.ViewParent;
import android.widget.TextView;

class TextViewHelper {
    static void setError(@NonNull TextView textView, @Nullable String error) {
        if (textView instanceof TextInputEditText) {
            TextInputLayout inputLayout = getTextInputLayout((TextInputEditText) textView);
            if (inputLayout != null) {
                inputLayout.setError(error);
                return;
            }
        }
        textView.setError(error);
    }

    @Nullable
    private static TextInputLayout getTextInputLayout(TextInputEditText input) {
        ViewParent parent = input.getParent();
        if (parent != null) {
            ViewParent grandParent = parent.getParent();
            if (grandParent instanceof TextInputLayout) {
                return (TextInputLayout) grandParent;
            }
        }
        return null;
    }
}
