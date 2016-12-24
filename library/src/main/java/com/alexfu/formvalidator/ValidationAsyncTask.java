package com.alexfu.formvalidator;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

class ValidationAsyncTask extends AsyncTask<ValidationTask, Integer, List<ValidationResult>> {

  enum Mode {
    INPUT, FORM
  }

  interface Callback {
    void onTaskCompleted(Mode mode, List<ValidationResult> results);
  }

  ValidationAsyncTask(Mode mode, Callback callback) {
    this.mode = mode;
    this.callback = callback;
  }

  private final Mode mode;
  private final Callback callback;

  @Override
  protected List<ValidationResult> doInBackground(ValidationTask... tasks) {
    List<ValidationResult> results = new ArrayList<>();
    for(ValidationTask task : tasks) {
      results.add(task.validate());
    }
    return results;
  }

  @Override
  protected void onPostExecute(List<ValidationResult> results) {
    callback.onTaskCompleted(mode, results);
  }
}
