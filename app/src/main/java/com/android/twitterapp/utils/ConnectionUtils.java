package com.android.twitterapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Mohamed Elgendy.
 */

public class ConnectionUtils {

    private static int TYPE_WIFI = 1;
    private static int TYPE_MOBILE = 2;
    private static int TYPE_NOT_CONNECTED = 0;

    private static int getConnectivityStatus(Context context) {
        Log.i("Function called:", "getConnectivityStatus");

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    private static String getConnectivityStatusString(Context context) {
        Log.i("Function called:", "getConnectivityStatusString");

        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    public static boolean isConnected(Context context){

        boolean result = false;

        String res= getConnectivityStatusString(context);
        if (res.equals("Wifi enabled") || res.equals("Mobile data enabled")) {
            result = true;
        }
        else if(res.equals("Not connected to Internet")){
            result = false;
        }
        return  result;
    }
}