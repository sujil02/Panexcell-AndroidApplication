package com.panexcell;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujil on 07-01-2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String URL = "http://panexcell.binarywebworks.com:3000/signup";
    private Map<String, String> params;

    public RegisterRequest(String name, String middlename, String surname, String dob, String idtype, String idnumber,String phoneNo, String gender, Response.Listener<String> Listener){
        super(Method.POST, URL, Listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("fathersname", middlename);
        params.put("surname", surname);
        params.put("dob", dob);
        params.put("idtype", idtype);
        params.put("idnumber", idnumber);
        params.put("phonenumber", phoneNo);
        params.put("gender", gender);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
