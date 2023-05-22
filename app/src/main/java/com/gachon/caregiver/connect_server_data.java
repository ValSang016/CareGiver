package com.gachon.caregiver;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class connect_server_data extends AppCompatActivity {

    private RequestQueue requestQueue;

    //여기에 상헌이가 준 코드
    String url ="https://sanghyun_data_server";

    protected void login_connect(String id,String pw){

        String login_id = id;
        String login_pw = pw;
        //volley 요청 초기화
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //이제 내용을 보내기 시작 할 예정이다.
        //바로 밑의 코드 부분은 데이터를 포장하는 부분이다.
        JSONObject post_login_data = new JSONObject();
        try{
            post_login_data.put("id","login_id");
            post_login_data.put("pw","login_pw");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //포장한 데이터를 서버로 전송하는 부분의 코드이다.
        JsonObjectRequest login_requset = new JsonObjectRequest(Request.Method.POST, url, post_login_data, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // 서버 응답을 처리하는 코드
                try {
                    // 응답 데이터 처리
                    String result = response.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 에러 처리
                        error.printStackTrace();
                    }

        });
    }

}
