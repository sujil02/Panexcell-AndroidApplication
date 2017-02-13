package com.panexcell;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujil on 07-01-2017.
 */

public class LoginRequest extends StringRequest {

    private static final String URL = "http://panexcell1.binarywebworks.com:3001/login";
    private Map<String, String> params;

    public LoginRequest(String username, String password, String token, Response.Listener<String> Listener){
        super(Method.POST, URL, Listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("token",token);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
