package com.example.zverys.to_do;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private CheckBox rememberMeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login();
    }

    private void login() {
        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);

        Button submit = (Button) findViewById(R.id.login_submit);

        rememberMeCheckBox = (CheckBox) findViewById(R.id.login_remember_me);
        final User user = new User(getApplicationContext());
        rememberMeCheckBox.setChecked(user.isRemembered());


        if (user.isRemembered()) {
            username.setText(user.getUsernameForLogin(), TextView.BufferType.EDITABLE);
            password.setText(user.getPasswordForLogin(), TextView.BufferType.EDITABLE);
        } else {
            username.setText("", TextView.BufferType.EDITABLE);
            password.setText("", TextView.BufferType.EDITABLE);
        }


        submit.setOnClickListener(new View.OnClickListener() {
            Authentication authentication = new Authentication();

            public void onClick(View focusView) {


                String username2 = username.getText().toString();
                String password2 = password.getText().toString();

                boolean cancel = false;

                if (!authentication.isValidCredentials(username2)) {
                    username.setError(getString(R.string.login_invalid_username));
                    focusView = username;
                    cancel = true;
                }

                if (!authentication.isValidCredentials(password2)) {
                    password.setError(getString(R.string.login_invalid_password));
                    focusView = password;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    if (rememberMeCheckBox.isChecked()) {
                        user.setUsernameLogin(username2);
                        user.setPasswordForLogin(password2);
                        user.setRemembered(true);
                    } else {
                        user.setUsernameLogin(username2);
                        user.setPasswordForLogin(password2);
                        user.setRemembered(false);
                    }
                    loginToDB(user);
                }
            }
        });

        Button register = (Button) findViewById(R.id.login_register);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent goToRegistryActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToRegistryActivity);
            }
        });
    }

    private void loginToDB(final User user) {
        class Login extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DB database = new DB();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, getString(R.string.Login_please_wait), null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals(Integer.toString(HttpsURLConnection.HTTP_ACCEPTED))) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_LONG).show();
                } else if (s.equals(Integer.toString(HttpsURLConnection.HTTP_NOT_AUTHORITATIVE))) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_failure), Toast.LENGTH_LONG).show();
                }

                loading.dismiss();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("action", "login");
                data.put("username", params[0]);
                data.put("password", params[1]);
                String result = database.sendPostRequest(getString(R.string.URL_DATABASE), data);
                return result;
            }
        }
        Login login = new Login();
        UserName.UserName = user.getUsernameForLogin();
        login.execute(user.getUsernameForLogin(), user.getPasswordForLogin());
    }
}
