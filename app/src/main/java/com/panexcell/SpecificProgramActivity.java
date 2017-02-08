package com.panexcell;

import android.content.SharedPreferences;
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
    SharedPreferences prefs;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_program);
        Bundle bundle = getIntent().getExtras();
        String id = (String)bundle.getString("id");

        tv1 = (TextView) findViewById(R.id.tvStudyNo1);
        tv2 = (TextView) findViewById(R.id.tvBloodLoss1);
        tv3 = (TextView) findViewById(R.id.tvPayment1);
        tv4 = (TextView) findViewById(R.id.tvPeriods1);
        tv5 = (TextView) findViewById(R.id.tvStay1);
        tv6 = (TextView) findViewById(R.id.tvAS1);
        tv7 = (TextView) findViewById(R.id.tvStudy1);
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

                        tv1.setText(rec.getString("title"));
                        tv2.setText(rec.getString("bloodloss"));
                        tv3.setText(rec.getString("payment"));
                        tv4.setText(rec.getString("noofperiods"));
                        tv5.setText(rec.getString("stay"));
                        tv6.setText(rec.getString("ambulatorysamples"));
                        tv7.setText(rec.getString("study"));
                        if(applied)
                        {
                            btApply.setText("Already Applied");
                            btApply.setEnabled(false);
                        }
                        else
                            btApply.setEnabled(true);

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        prefs = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        SpecificProgramRequest specificProgramRequest = new SpecificProgramRequest( prefs.getString(Constants.SESSIONUSER,null),id,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(SpecificProgramActivity.this);
        requestQueue.add(specificProgramRequest);


        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btApply.setEnabled(FALSE);
                btApply.setClickable(FALSE);
                btApply.setText("Already Applied");
            }
        });


    }
}
