package com.alexfu.formvalidator;

/**
 * Default validator
 */

public class Validator extends AbsValidator<Void> {
  private final DefaultValidationWorker worker;

  public Validator(DefaultValidationWorker.Callback callback) {
    this.worker = new DefaultValidationWorker(callback);
  }

  @Override protected Void performValidate(ValidationTask[] tasks) {
    return worker.performValidate(tasks);
  }

  @Override protected Void performValidate(ValidationTask task) {
    return worker.performValidate(task);
  }
}
