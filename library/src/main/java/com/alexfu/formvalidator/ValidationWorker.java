package com.alexfu.formvalidator;

/**
 * The actual worker that does the work of validating
 */
public interface ValidationWorker<T> {
  T performValidate(ValidationTask[] tasks);
  T performValidate(ValidationTask task);
}
