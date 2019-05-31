package com.example.junot.quizapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.junot.enums.EnumAsyncTask;
import com.example.junot.interfaces.AsyncTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener, AsyncTaskListener {

    private ActionBar actionBar;
    private TextInputEditText editTextUsernameOrEmail;
    private TextInputEditText editTextPassword;
    private Button btnLogin;
    private Button btnRegister;
    private AsyncTaskUtils asyncTaskUtils;
    private ProgressBar progressBarLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponent();
    }

    private void initializeComponent() {
        actionBar = getSupportActionBar();
        editTextUsernameOrEmail = findViewById(R.id.editTextEmailOrUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        actionBar.hide();
        toggleButton(true);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnLogin:
                validateUserInput();
                break;
            case R.id.btnRegister:
                goToRegisterActivity();
                break;
        }
    }

    private void validateUserInput() {
        String emailOrUsername = editTextUsernameOrEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        if (emailOrUsername.isEmpty()) {
            editTextUsernameOrEmail.setError("Please input your email or username");
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please input your email or username");
            return;
        }
        editTextUsernameOrEmail.setError(null);
        editTextPassword.setError(null);

        asyncTaskUtils = new AsyncTaskUtils();
        asyncTaskUtils.setEnums(EnumAsyncTask.ASYNC_TASK_LOGIN);
        asyncTaskUtils.setListener(this);
        asyncTaskUtils.execute(emailOrUsername, password);
    }

    private void goToRegisterActivity() {
        Intent intent = new Intent(this, ActivityRegister.class);
        startActivity(intent);
    }

    @Override
    public void onProgressUpdate(EnumAsyncTask enums, Integer... values) {
        switch (enums) {
            case ASYNC_TASK_LOGIN:
                break;
        }
    }

    @Override
    public void onPreExecute(EnumAsyncTask enums) {
        switch (enums) {
            case ASYNC_TASK_LOGIN:
                toggleButton(false);
                break;
        }
    }

    private void toggleButton(boolean b) {
        if (b) {
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
            progressBarLoading.setVisibility(View.GONE);
        } else {
            btnLogin.setVisibility(View.GONE);
            btnRegister.setVisibility(View.GONE);
            progressBarLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String doInBackground(EnumAsyncTask enums, Object... objects) {
        switch (enums) {
            case ASYNC_TASK_LOGIN:
                return doLogin(objects[0], objects[1]);
        }
        return null;
    }

    @Override
    public void onPostExecute(EnumAsyncTask enums, String result) {
        switch (enums) {
            case ASYNC_TASK_LOGIN:
                toggleButton(true);
                checkPostResult(result);
                break;
        }
    }

    private void checkPostResult(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            boolean result = jsonObject.getBoolean("result");
            if (result) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                String message = jsonObject.getString("message");
                showErrorDialog(message, "Error");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showErrorDialog(e.getMessage(), "Error");
        }
    }

    private void showErrorDialog(String message, String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(error);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private String doLogin(Object object, Object o) {
        String emailOrUsername = object.toString();
        String password = o.toString();
        String url = String.format("%s%s", StaticString.SERVER, StaticString.DO_LOGIN);
        ConnectionUtils connectionUtils = new ConnectionUtils();
        HashMap<String, String> params = new HashMap<>();
        params.put("emailorusername", emailOrUsername);
        params.put("password", password);
        return connectionUtils.sendRequest(url, params);
    }
}

