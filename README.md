# form-validator

A simple, easy to use, no frills, form validator for Android.

# Usage

```java
EmailRule emailRule = new EmailRule("Invalid email address.");

Validator validator = new Validator(this);
validator.addRule(myEditText, emailRule); // Add rules to your EditText
validator.validate();
```

If you want to have multiple rules...

```java
EmailRule emailRule = new EmailRule("Invalid email address.");
MinLengthRule minLengthRule = new MinLengthRule(5, "Must be at least 5 characters long.")

Validator validator = new Validator(this);
validator.addRule(myEditText, emailRule, minLengthRule); // Add rules to your EditText
validator.validate();
```

Adding rules will also bind a `TextWatcher` to the given `EditText` and validate it on the fly.

# Installation

```
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  compile 'com.github.alexfu:form-validator:0.1'
}
```
