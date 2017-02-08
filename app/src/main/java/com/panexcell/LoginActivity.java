package com.panexcell;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername,etPassword;
    private String MY_PREFS_NAME = "USERPREF";
    private String SESSIONCONSTANT = "IsSessionActive";
    private String SESSIONUSER = "SessionUser";
    private  SharedPreferences.Editor editor;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btLogin = (Button) findViewById(R.id.btLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegister);
        if(prefs.getBoolean(SESSIONCONSTANT,FALSE))
        {
            loginMethod();
        }

        int ins;
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btLogin.setEnabled(FALSE);
                btLogin.setClickable(FALSE);
                loginMethod();

            }

        });
    }
    public void loginMethod()
    {
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if(success){
                        UserDetails userDetails = new UserDetails();
                        userDetails.setUsername(username);
                        userDetails.setPassword(password);
                        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putBoolean(SESSIONCONSTANT,TRUE);
                        editor.putString(SESSIONUSER,username);
                        editor.commit();
                        Intent userAreaIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
                        LoginActivity.this.startActivity(userAreaIntent);
                        finish();
                    }
                    else{
                        AlertDialog.Builder builder = new  AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Sorry invaild login credentials. Please try again").setNegativeButton("retry", null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(loginRequest);
    }
}
