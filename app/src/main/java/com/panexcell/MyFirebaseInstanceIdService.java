package com.panexcell;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.panexcell.Constants.MY_PREFS_NAME;
import static com.panexcell.Constants.TOKEN;

/**
 * Created by sujil on 12-02-2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private  SharedPreferences.Editor editor;
    SharedPreferences prefs;

    @Override
    public void onTokenRefresh() {

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(TOKEN,recent_token);
        editor.commit();
        Log.d(TOKEN, recent_token);
        super.onTokenRefresh();
    }
}
