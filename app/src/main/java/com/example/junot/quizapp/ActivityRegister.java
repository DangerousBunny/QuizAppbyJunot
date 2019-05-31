package com.example.junot.quizapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.junot.enums.EnumAsyncTask;
import com.example.junot.interfaces.AsyncTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityRegister extends AppCompatActivity implements View.OnClickListener, AsyncTaskListener {

    private TextInputEditText editTextUsername;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextConfirmPassword;
    private Button btnRegister;
    private Button btnReset;
    private ProgressBar progressBarLoading;
    private AsyncTaskUtils asyncTaskUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeComponent();
    }

    private void initializeComponent() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnReset = findViewById(R.id.btnReset);
        progressBarLoading = findViewById(R.id.progressBarLoading);

        progressBarLoading.setVisibility(View.GONE);
        btnRegister.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (asyncTaskUtils != null && asyncTaskUtils.getStatus() == AsyncTask.Status.RUNNING) {
            asyncTaskUtils.cancel(true);
        }
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnRegister:
                validateUserInput();
                break;
            case R.id.btnReset:
                resetUserInput();
                break;
        }
    }

    private void resetUserInput() {
        editTextUsername.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextConfirmPassword.setText("");

        editTextUsername.setError(null);
        editTextEmail.setError(null);
        editTextPassword.setError(null);
        editTextConfirmPassword.setError(null);
    }

    private void validateUserInput() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (username.isEmpty()) {
            editTextUsername.setError("Please input username");
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Please input email");
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Please input email");
            return;
        }

        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Please input email");
            return;
        }

        if (!confirmPassword.equals(password)) {
            editTextConfirmPassword.setError("Password must same with confirm password");
            return;
        }

        editTextUsername.setError(null);
        editTextEmail.setError(null);
        editTextPassword.setError(null);
        editTextConfirmPassword.setError(null);

        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);

        asyncTaskUtils = new AsyncTaskUtils();
        asyncTaskUtils.setListener(this);
        asyncTaskUtils.setEnums(EnumAsyncTask.ASYNC_TASK_REGISTER);
        asyncTaskUtils.execute(params);
    }

    @Override
    public void onProgressUpdate(EnumAsyncTask enums, Integer... values) {

    }

    @Override
    public void onPreExecute(EnumAsyncTask enums) {
        switch (enums) {
            case ASYNC_TASK_REGISTER:
                toggleButton(false);
                break;
        }

    }

    private void toggleButton(boolean b) {
        if (b) {
            btnReset.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
            progressBarLoading.setVisibility(View.GONE);
        } else {
            btnReset.setVisibility(View.GONE);
            btnRegister.setVisibility(View.GONE);
            progressBarLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String doInBackground(EnumAsyncTask enums, Object... objects) {
        switch (enums) {
            case ASYNC_TASK_REGISTER:
                HashMap<String, String> params = (HashMap<String, String>) objects[0];
                return doRegisterNewUser(params);
        }
        return null;
    }

    private String doRegisterNewUser(HashMap<String, String> params) {
        String url = String.format("%s%s", StaticString.SERVER, StaticString.DO_REGISTER_NEW_USER);
        ConnectionUtils connectionUtils = new ConnectionUtils();
        return connectionUtils.sendRequest(url, params);
    }

    @Override
    public void onPostExecute(EnumAsyncTask enums, String result) {
        switch (enums) {
            case ASYNC_TASK_REGISTER:
                toggleButton(true);
                checkPostExecuteResult(result);
                break;
        }
    }

    private void checkPostExecuteResult(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            boolean result = jsonObject.getBoolean("result");
            String message = jsonObject.getString("message");
            if (result) {
                showDialogResult(message, "Success");
            } else {
                showDialogResult(message, "Error");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showDialogResult(e.getMessage(), "Error");
        }
    }

    private void showDialogResult(String message, final String success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(success);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (success.equalsIgnoreCase("success")) {
                    finish();
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
