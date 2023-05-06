package com.gachon.caregiver.userInform.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.MainActivity;
import com.gachon.caregiver.R;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;


public class LoginPage extends AppCompatActivity {

    SignInButton Google_Login;
    private static final int RC_SIGN_IN = 1000;
//    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    Button loginBtn = findViewById(R.id.Login_login_button);
    Button backBtn = findViewById(R.id.Login_back_button);

    EditText editTextID = findViewById(R.id.Login_idData);
    EditText editTextPW = findViewById(R.id.Login_passwordData);

    String ID;
    String PW;
    //Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ID = editTextID.getText().toString();
//                PW = editTextPW.getText().toString();
//
//                JSONObject requsetJsonObject = new JSONObject();
//                try {
//                    requsetJsonObject.put("id", ID);
//                    requsetJsonObject.put("password", PW);
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
////                RequestQueue requestQueue = v.getInstance(this).getRequestQueue();
//                // 이위까지 보내는 것을 모두 완료한 상태이다. 즉 id pw를 이제 volley를 통해 nodejs로 보낸 것이다.
//                JsonObjectRequest R_Object = new JsonObjectRequest(Request.Method.POST,"http://172.19.83.10:3000/receiv", requsetJsonObject, new Response.Listener() {
//                    @Override
//                    public void onResponse(Object response) {
//
//                    }
//
//                    public void onResponse(JSONObject response) {
//                        JSONArray J_JsonArray = new JSONArray();
//                        try {
//                            J_JsonArray = response.getJSONArray("results");
//                            JSONObject dataObj = J_JsonArray.getJSONObject(0);
//
//                            String name = null; //여기에다가 이름을 받아온다 만약 비밀번호가 틀렸으면 1 아이디가 틀렸으면 2 등록이된것이 아니라면 3을 수신하게 한다
//
//
//
//
//                        } catch(JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                }, new Response.ErrorListener() { //수신에서 에러가 난 경우에만 작동하는 코드이다 즉 돌아가면 ㅈ대는 거다
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(this, "네트워크 연결 오류.", Toast.LENGTH_LONG).show();
//                        Log.i("VolleyError", "Volley Error in receiv");
//                    }
//                });
////                requestQueue.add(R_Object);
//            }
//        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_back);
            }
        });


    }
}