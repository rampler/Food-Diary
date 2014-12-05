package com.example.medicineApp.helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.medicineApp.activities.Logging;
import com.example.medicineApp.activities.UserProfile;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Sabina on 2014-12-01.
 */
public class Session {

    private static Globals g;

    public static boolean LogOut(UserProfile userProfile) throws IOException {
        g = Globals.getInstance();
        String sessionId = g.getSessionId();
        Context context = userProfile.getApplicationContext();
        //MakeLogoutRequest(sessionId, context);
        return LogOutWithHttpRequest(sessionId, context);
    }

    private static boolean MakeLogoutRequest(String sessionId, final Context context) {
        String uri = String.format("http://foodiary.ddns.net:8080/logout?sessionId=%1$s", sessionId);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("LogOut", response.toString());
                Toast.makeText(context, "User no longer logged in", Toast.LENGTH_SHORT);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("LogOut", "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
        return false;
    }

    private static boolean LogOutWithHttpRequest(String sessionId, Context context) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(String.format(g.getServerURL() + "/logout?sessionId=%1$s", sessionId));
        HttpResponse response = httpclient.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == 200) {
            return true;
        }
        return false;
    }

}
