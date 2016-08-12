package com.alexfu.formvalidatordemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.alexfu.formvalidator.ValidationResult;
import com.alexfu.formvalidator.Validator;
import com.alexfu.formvalidator.rules.EmailRule;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Validator.Callback {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    EditText emailInput = (EditText) findViewById(R.id.email_input);

    Validator validator = new Validator();
    validator.setCallback(this);
    validator.addRule(emailInput, new EmailRule("Invalid email."));
  }

  @Override
  public void onFieldValidationSuccessful(EditText editText) {
    editText.setError(null);
  }

  @Override
  public void onFieldValidationFailed(EditText editText, List<String> errors) {
    editText.setError(errors.get(0)); // Show errors one at a time
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
