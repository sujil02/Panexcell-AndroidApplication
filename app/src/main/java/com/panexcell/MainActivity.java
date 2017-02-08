package com.panexcell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import static com.panexcell.Constants.MY_PREFS_NAME;
import static com.panexcell.Constants.SESSIONCONSTANT;
import static com.panexcell.Constants.SESSIONLOCALE;
import static java.lang.Boolean.FALSE;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bthindi = (Button) findViewById(R.id.btHindi);
        final Button btenglish = (Button) findViewById(R.id.btEnglish);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if(prefs.getBoolean(SESSIONCONSTANT,FALSE))
        {
            Locale locale = new Locale(prefs.getString(SESSIONLOCALE,null));
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            Intent registerIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(registerIntent);
            finish();
        }

        bthindi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String languageToLoad = "hi";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString(SESSIONLOCALE,languageToLoad);
                editor.commit();

                Intent registerIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(registerIntent);
                finish();

            }
        });


        btenglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languageToLoad = "en";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString(SESSIONLOCALE,languageToLoad);
                editor.commit();
                Intent registerIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(registerIntent);
                finish();
            }
        });

    }
}
