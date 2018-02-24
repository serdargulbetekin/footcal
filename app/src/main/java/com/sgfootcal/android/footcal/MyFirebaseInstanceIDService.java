package com.sgfootcal.android.footcal;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by serdar on 18.2.2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String  REG_TOKEN="REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        String recent_toekn = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recent_toekn);

    }
}
