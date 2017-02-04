package com.panexcell;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Boolean.FALSE;

public class SpecificProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_program);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");

        final TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        final TextView tvDescription = (TextView) findViewById(R.id.tvPayment1);
        final Button btApply = (Button) findViewById(R.id.btApply);
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject rec = jsonObject.getJSONObject("data");
                    boolean success = jsonObject.getBoolean("success");
                    boolean applied = jsonObject.getBoolean("applied");

                    if(success){

                        String title = rec.getString("title");
                        tvTitle.setText(title);
                        String description = rec.getString("description");
                        tvDescription.setText(description);
                        btApply.setEnabled(applied);

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        UserDetails userDetails = new UserDetails();
        SpecificProgramRequest specificProgramRequest = new SpecificProgramRequest(userDetails.getPassword(),"id",responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(SpecificProgramActivity.this);
        requestQueue.add(specificProgramRequest);


        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btApply.setEnabled(FALSE);
                btApply.setClickable(FALSE);
            }
        });


    }
}
