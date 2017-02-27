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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {

    int year_x,month_x,day_x;
    static final int DIALOG_ID = 0;
    private RadioButton radioSexButton;
    EditText etName;
    String PhoneNumber2 = null;
    private Calendar calendar;
    private TextView etDOB;

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
            month_x = month +1;
            day_x = dayOfMonth ;
            Date date = new Date(year_x,month_x,day_x);
            final TextView etDOB1 = (TextView) findViewById(R.id.etDOB);

            Calendar userAge = new GregorianCalendar(year,month,dayOfMonth);
            Calendar minAdultAge = new GregorianCalendar();
            minAdultAge.add(Calendar.YEAR, -18);
            if (minAdultAge.before(userAge)) {
                Toast.makeText(getApplicationContext(), "Age should be more than 18 years", Toast.LENGTH_LONG).show();
                etDOB.requestFocus();
            }
            else {
                etDOB1.setText(day_x + "/" + month_x + "/" + year_x);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = (EditText) findViewById(R.id.etName);
        final EditText etMiddleName = (EditText) findViewById(R.id.etMiddleName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        etDOB = (TextView) findViewById(R.id.etDOB);
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
        final EditText etAlternateNumber = (EditText) findViewById(R.id.etPhoneNumber2);
        final EditText etcity = (EditText) findViewById(R.id.etCity);
        final RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        final Button btRegister = (Button) findViewById(R.id.btRegister);

        calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);
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
                else if(etIDNumber.getText().toString().length()==0){
                    etIDNumber.setError("Please enter valid ID");
                    etIDNumber.requestFocus();
                }
                else if(etPhone.getText().toString().length()!=10){
                    etPhone.setError("Please input right number");
                    etPhone.requestFocus();
                }
                else if(radioSexGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Please select gender", Toast.LENGTH_LONG).show();
                    radioSexGroup.requestFocus();
                }
                else {

                    btRegister.setEnabled(false);
                    btRegister.setClickable(false);
                    final String Name = etName.getText().toString();
                    final String MiddleName = etMiddleName.getText().toString();
                    final String Surname = etSurname.getText().toString();
                    final String DOB = etDOB.getText().toString();
                    final String IdType = spinner.getSelectedItem().toString();
                    final String IDNumber = etIDNumber.getText().toString();
                    final String PhoneNumber = etPhone.getText().toString();
                    PhoneNumber2 = etAlternateNumber.toString();
                    String city = etcity.getText().toString();
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) findViewById(selectedId);
                    final String Gender = (String) radioSexButton.getText();
                    //String str = "02/02/1995";
                    SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                    Date date = null;
                    try {
                        date = df.parse(DOB);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long epoch = date.getTime();
                    //System.out.println(epoch); // 1055545912454


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
                                            finish();
                                        }
                                    }, timeout);

                                    Intent descriptionActivity = new Intent(RegisterActivity.this, DescriptionActivity.class);
                                    RegisterActivity.this.startActivity(descriptionActivity);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("fail").setNegativeButton("retry", null).create().show();
                                    btRegister.setEnabled(true);
                                    btRegister.setClickable(true);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    if(etAlternateNumber.getText().toString().length() == 0 ){
                        PhoneNumber2 = "0";
                    }
                    if(etcity.getText().toString().length() == 0 ){
                        city = "0";
                    }
                    RegisterRequest registerRequest = new RegisterRequest(Name, MiddleName, Surname, String.valueOf(epoch) , IdType, IDNumber, PhoneNumber,PhoneNumber2, Gender,city, responseListner);
                    RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                    requestQueue.add(registerRequest);
                }
            }
        });


    }
}
