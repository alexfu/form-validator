package com.alexfu.formvalidatordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alexfu.formvalidator.ValidationResult;
import com.alexfu.formvalidator.Validator;
import com.alexfu.formvalidator.rules.EmailRule;
import com.alexfu.formvalidator.rules.MinLengthRule;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Validator.Callback {
    private Button validateButton;
    private EditText firstNameInput, lastNameInput, emailInput;
    private Validator validator = new Validator();
    private List<ValidationResult> validationErrors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        setUpValidator();
    }

    private void setUpValidator() {
        validator.setCallback(this);
        validator.addRule(firstNameInput, new MinLengthRule(1, "First name cannot be empty."));
        validator.addRule(lastNameInput, new MinLengthRule(1, "Last name cannot be empty."));
        validator.addRule(emailInput, new EmailRule("Invalid email."));

        // Form validation

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                validationErrors.clear();
                validator.validate();
            }
        });
    }

    @Override public void onFieldValidated(ValidationResult result) {
        if (result.isValid()) {
            result.view.setError(null);
        } else {
            result.view.setError(result.errors.get(0));
            validationErrors.add(result);
        }
    }

    @Override public void onFormValidated() {
        if (validationErrors.isEmpty()) {
            Toast.makeText(this, "Form is valid!", Toast.LENGTH_SHORT).show();
        } else {
            validationErrors.get(0).view.requestFocus();
        }
    }

    private void setUpViews() {
        firstNameInput = (EditText) findViewById(R.id.first_name_input);
        lastNameInput = (EditText) findViewById(R.id.last_name_input);
        emailInput = (EditText) findViewById(R.id.email_input);
        validateButton = (Button) findViewById(R.id.button_validate);
    }
}
