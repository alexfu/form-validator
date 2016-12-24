package com.alexfu.formvalidatordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexfu.formvalidator.Validator;
import com.alexfu.formvalidator.rules.EmailRule;
import com.alexfu.formvalidator.rules.MinLengthRule;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Validator.Callback {
  private Button validateButton;
  private EditText firstNameInput, lastNameInput, emailInput;
  private Validator validator = new Validator(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setUpViews();
    setUpValidator();
  }

  private void setUpValidator() {
    validator.addRule(firstNameInput, new MinLengthRule(1, "First name cannot be empty."));
    validator.addRule(lastNameInput, new MinLengthRule(1, "Last name cannot be empty."));
    validator.addRule(emailInput, new EmailRule("Invalid email."));

    // Form validation

    validateButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        validator.validate();
      }
    });

    // Input validation

    validateOnTextChange(firstNameInput, lastNameInput, emailInput);
  }

  @Override public void onFieldValidationSuccessful(TextView view) {
    view.setError(null);
  }

  @Override
  public void onFieldValidationFailed(TextView view, List<String> errors) {
    view.setError(errors.get(0));
  }

  @Override public void onFormValidationSuccessful() {
    Toast.makeText(this, "Form is valid!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onFormValidationFailed() {
    // Ignore
  }

  private void validateOnTextChange(TextView...inputs) {
    for (final TextView input : inputs) {
      input.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          // Ignore
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          // Ignore
        }

        @Override public void afterTextChanged(Editable s) {
          validator.validate(input);
        }
      });
    }
  }

  private void setUpViews() {
    firstNameInput = (EditText) findViewById(R.id.first_name_input);
    lastNameInput = (EditText) findViewById(R.id.last_name_input);
    emailInput = (EditText) findViewById(R.id.email_input);
    validateButton = (Button) findViewById(R.id.button_validate);
  }
}
