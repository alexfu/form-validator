package com.alexfu.formvalidatordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.alexfu.formvalidator.DefaultValidationWorker;
import com.alexfu.formvalidator.ValidationResult;
import com.alexfu.formvalidator.Validator;
import com.alexfu.formvalidator.rules.EmailRule;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DefaultValidationWorker.Callback {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    EditText emailInput = (EditText) findViewById(R.id.email_input);

    Validator validator = new Validator(this);
    validator.addRule(emailInput, new EmailRule("Invalid email."));
  }

  @Override
  public void onFieldValidationSuccessful(TextView view) {
    view.setError(null);
  }

  @Override
  public void onFieldValidationFailed(TextView view, List<String> errors) {
    view.setError(errors.get(0)); // Show errors one at a time
  }

  @Override
  public void onFormValidationSuccessful() {
    // Do something here when form is fully valid
  }

  @Override
  public void onFormValidationFailed(List<ValidationResult> errorValidations) {
    // Do something here when form is invalid
  }
}
