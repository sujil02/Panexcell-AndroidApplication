package com.panexcell;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {

    int year_x,month_x,day_x;
    static final int DIALOG_ID = 0;
    private RadioButton radioSexButton;
    EditText etName;

    public void showDialogOnButtonClick(){
        final  Button btDatePicker = (Button) findViewById(R.id.btDatePicker);

        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_ID)
            return new DatePickerDialog(this,dpickerListner, year_x,month_x,day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListner
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year_x = year;
            month_x = month;
            day_x = dayOfMonth;
            final TextView etDOB1 = (TextView) findViewById(R.id.etDOB);
            etDOB1.setText(day_x + "/" + month_x + "/" + year_x);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = (EditText) findViewById(R.id.etName);
        final EditText etMiddleName = (EditText) findViewById(R.id.etMiddleName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        final TextView etDOB = (TextView) findViewById(R.id.etDOB);
       // final Spinner spIDType = (Spinner) findViewById(R.id.spIDType);
        final Spinner spinner = (Spinner) findViewById(R.id.spIDType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        final  EditText etIDNumber = (EditText) findViewById(R.id.etIDNumber);
        final  EditText etPhone = (EditText) findViewById(R.id.etPhoneNumber);
        final RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        final Button btRegister = (Button) findViewById(R.id.btRegister);


        showDialogOnButtonClick();
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etName.getText().toString().length()==0){
                    etName.setError("username not entered");
                    etName.requestFocus();

                }
                else if(etMiddleName.getText().toString().length()==0){
                    etMiddleName.setError("middle name not entered");
                    etMiddleName.requestFocus();
                }
                else if(etSurname.getText().toString().length()==0){
                    etSurname.setError("Please enter surname");
                    etSurname.requestFocus();
                }
                else if(etDOB.getText().toString().length()==0){
                        etDOB.setError("Please select DOB");
                    etDOB.requestFocus();
                }
                else if(radioSexGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Please select gender", Toast.LENGTH_LONG).show();
                    radioSexGroup.requestFocus();
                }
                else {


                    final String Name = etName.getText().toString();
                    final String MiddleName = etMiddleName.getText().toString();
                    final String Surname = etSurname.getText().toString();
                    final String DOB = etDOB.getText().toString();
                    final String IdType = spinner.getSelectedItem().toString();
                    ;
                    final String IDNumber = etIDNumber.toString();
                    final String PhoneNumber = etPhone.toString();
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) findViewById(selectedId);
                    final String Gender = (String) radioSexButton.getText();


                    Response.Listener<String> responseListner = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                //JSONArray data = jsonObject.getJSONArray("data");

                                if (success) {

                                    int timeout = 8000; // make the activity visible for 10 seconds

                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {

                                        @Override
                                        public void run() {
                                            finish();
                                            Intent homepage = new Intent(RegisterActivity.this, LoginActivity.class);
                                            RegisterActivity.this.startActivity(homepage);
                                        }
                                    }, timeout);

                                    Intent descriptionActivity = new Intent(RegisterActivity.this, DescriptionActivity.class);
                                    RegisterActivity.this.startActivity(descriptionActivity);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("fail").setNegativeButton("retry", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    RegisterRequest registerRequest = new RegisterRequest(Name, MiddleName, Surname, DOB, IdType, IDNumber, PhoneNumber, Gender, responseListner);
                    RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                    requestQueue.add(registerRequest);
                }
            }
        });


    }
}
