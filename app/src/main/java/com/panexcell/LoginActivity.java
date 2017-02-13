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

import static com.panexcell.Constants.SESSIONUSER_PASSWORD;
import static com.panexcell.Constants.TOKEN;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername,etPassword;
    private String MY_PREFS_NAME = "USERPREF";
    private String SESSIONCONSTANT = "IsSessionActive";
    private String SESSIONUSER = "SessionUser";
    private String username,password;
    private Button btLogin;
    private  SharedPreferences.Editor editor;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
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
                    username = etUsername.getText().toString();
                    password = etPassword.getText().toString();
                    editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString(SESSIONUSER, username);
                    editor.putString(SESSIONUSER_PASSWORD, password);
                    editor.commit();
                    loginMethod();
            }

        });
    }
    public void loginMethod()
    {
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if(success){
                        if(!prefs.getBoolean(SESSIONCONSTANT,FALSE)) {
                            editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putBoolean(SESSIONCONSTANT, TRUE);
                            editor.putString(SESSIONUSER, username);
                            editor.putString(SESSIONUSER_PASSWORD, password);
                            editor.commit();
                        }
                        Intent userAreaIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
                        LoginActivity.this.startActivity(userAreaIntent);
                        finish();
                    }
                    else{
                        btLogin.setEnabled(TRUE);
                        btLogin.setClickable(TRUE);
                        AlertDialog.Builder builder = new  AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Sorry invaild login credentials. Please try again").setNegativeButton("retry", null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        String t = prefs.getString(SESSIONUSER,null);

        LoginRequest loginRequest = new LoginRequest(prefs.getString(SESSIONUSER,null), prefs.getString(SESSIONUSER_PASSWORD,null),prefs.getString(TOKEN,null), responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(loginRequest);
    }
}
