package com.alexfu.formvalidatordemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                validator.validate();
            }
        });
    }

    @Override public void onBeginFormValidation() {
        Log.d("MainActivity", "onBeginFormValidation");
    }

    @Override public void onFieldValidated(@NonNull ValidationResult result) {
        Log.d("MainActivity", "onFieldValidated");
    }

    @Override
    public void onSuccessValidation() {
        Log.d("MainActivity", "onSuccessValidation");
    }

    @Override
    public void onFailedValidation(@NonNull List<ValidationResult> errors) {
        Log.d("MainActivity", "onFailedValidation");
    }

    private void setUpViews() {
        firstNameInput = findViewById(R.id.first_name_input);
        lastNameInput = findViewById(R.id.last_name_input);
        emailInput = findViewById(R.id.email_input);
        validateButton = findViewById(R.id.button_validate);
    }
}
