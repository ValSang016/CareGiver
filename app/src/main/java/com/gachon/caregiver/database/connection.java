package com.gachon.caregiver.database;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class connection {

    String serverUrl = "http://www.exam[ple.com/api/get_data";
    // Request a string response from the provided URL.
    StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.
                    Log.d("Volley", "Response is: "+ response);
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // Display error message.
            Log.e("Volley", "That didn't work!");
        }
    });

}
