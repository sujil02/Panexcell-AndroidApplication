package com.panexcell;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujil on 19-01-2017.
 */

public class SpecificProgramRequest extends StringRequest {


        private static final String URL = "http://panexcell1.binarywebworks.com:3001/experiments/0";
        private Map<String, String> params;

        public SpecificProgramRequest(String username, String id, Response.Listener<String> Listener){


            super(Method.POST, URL, Listener, null);
            //String URL = "http://panexcell.binarywebworks.com:3000/experiments/1";
            params = new HashMap<>();
            params.put("username", username);
            params.put("id", id);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }

    }

