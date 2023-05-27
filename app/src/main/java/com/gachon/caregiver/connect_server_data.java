package com.gachon.caregiver;

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

    //여기에 상헌이가 준 주소로 변경하여 앞으로 전송할 url로 사용할 예정이다 주소 변경해야한다. 지금은 임시로 대충 한거다.
    String url ="https://sanghyun_data_server";

    public void login_connect(String id,String pw){

        String login_id = id;
        String login_pw = pw;
        //volley 요청 초기화
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //이제 내용을 보내기 시작 할 예정이다.
        //바로 밑의 코드 부분은 데이터를 포장하는 부분이다.
        JSONObject post_login_data = new JSONObject();
        try{
            post_login_data.put("id",login_id);
            post_login_data.put("pw",login_pw);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //포장한 데이터를 서버로 전송하는 부분의 코드이다.
        JsonObjectRequest login_request = new JsonObjectRequest(Request.Method.POST, url, post_login_data, new Response.Listener<JSONObject>() {

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


    public void sign_up_parents_connect(String name,String birth,String gender,String sign_up_id,String sign_up_pw,String phone_number){
        String sign_name = name;
        String sign_birht = birth;
        String sign_gender = gender;
        String sign_id = sign_up_id;
        String sign_pw = sign_up_pw;
        String phone = phone_number;

        //다시 연결 초기화해서 연결상태 비워두고 보내기 위함
//        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //바로 밑의 코드 부분은 데이터를 포장하는 부분이다.
        JSONObject post_sign_up_data = new JSONObject();

        try{
            post_sign_up_data.put("name",sign_name);
            post_sign_up_data.put("birth",sign_birht);
            post_sign_up_data.put("gender",sign_gender);
            post_sign_up_data.put("id",sign_id);
            post_sign_up_data.put("pw",sign_pw);
            post_sign_up_data.put("phone_number",phone);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //포장한 데이터를 서버로 전송하는 부분의 코드이다.
        JsonObjectRequest sign_up_requset = new JsonObjectRequest(Request.Method.POST, url, post_sign_up_data, new Response.Listener<JSONObject>() {

            //이 이하의 부분은 답을 받았을 때 어떻게 할 것인지에 대한 코드를 작성하는 곳이다.
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
        requestQueue.add(sign_up_requset);
    }

    public void sign_up_companion_connect(String name,String birth,String gender,String sign_up_id,String sign_up_pw,String phone_number){
        String sign_name = name;
        String sign_birht = birth;
        String sign_gender = gender;
        String sign_id = sign_up_id;
        String sign_pw = sign_up_pw;
        String phone = phone_number;

        //다시 연결 초기화해서 연결상태 비워두고 보내기 위함
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //바로 밑의 코드 부분은 데이터를 포장하는 부분이다.
        JSONObject post_sign_up_data = new JSONObject();

        try{
            post_sign_up_data.put("name",sign_name);
            post_sign_up_data.put("birth",sign_birht);
            post_sign_up_data.put("gender",sign_gender);
            post_sign_up_data.put("id",sign_id);
            post_sign_up_data.put("pw",sign_pw);
            post_sign_up_data.put("phone_number",phone);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //포장한 데이터를 서버로 전송하는 부분의 코드이다.
        JsonObjectRequest sign_up_requset = new JsonObjectRequest(Request.Method.POST, url, post_sign_up_data, new Response.Listener<JSONObject>() {

            //이 이하의 부분은 답을 받았을 때 어떻게 할 것인지에 대한 코드를 작성하는 곳이다.
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
        requestQueue.add(sign_up_requset);
    }
}