package com.panexcell;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujil on 07-01-2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String URL = "http://panexcell1.binarywebworks.com:3001/signup";
    private Map<String, String> params;

    public RegisterRequest(String name, String middlename, String surname, String dob, String idtype, String idnumber,String phoneNo,String phoneNo2, String gender, String city, Response.Listener<String> Listener){
        super(Method.POST, URL, Listener, null);
        params = new HashMap<>();
        params.put("username", phoneNo);
        params.put("name", name);
        params.put("middlename", middlename);
        params.put("surname", surname);
        params.put("dob", dob);
        params.put("idtype", idtype);
        params.put("idnumber", idnumber);
        params.put("phonenumber", phoneNo);
        params.put("alternatephonenumber", phoneNo2);
        params.put("location",city);
        params.put("gender", gender);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
