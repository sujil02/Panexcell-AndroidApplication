package com.panexcell;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujil on 18-01-2017.
 */

public class UserAreaRequest extends StringRequest {

    private static final String URL = "http://panexcell.binarywebworks.com:3000/experiments";
    private Map<String, String> params;


    public UserAreaRequest(String username,  Response.Listener<String> Listener){
        super(Method.POST, URL, Listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
