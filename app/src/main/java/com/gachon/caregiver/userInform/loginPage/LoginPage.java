package com.gachon.caregiver.userInform.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gachon.caregiver.MainActivity;
import com.gachon.caregiver.R;
import com.gachon.caregiver.companion_main;
import com.gachon.caregiver.parents_main;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    SignInButton Google_Login;
    private static final int RC_SIGN_IN = 1000;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    String ID;
    String PW;
    //Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_class);

        Button loginBtn = findViewById(R.id.Login_login_button);
        Button backBtn = findViewById(R.id.Login_back_button);

        EditText editTextID = findViewById(R.id.Login_idData);
        EditText editTextPW = findViewById(R.id.Login_passwordData);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = editTextID.getText().toString();
                PW = editTextPW.getText().toString();

                JSONObject requestJsonObject = new JSONObject();
                try {
                    requestJsonObject.put("id", ID);
                    requestJsonObject.put("password", PW);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                // 이위까지 보내는 것을 모두 완료한 상태이다. 즉 id pw를 이제 volley를 통해 nodejs로 보낸 것이다.
                JsonObjectRequest R_Object = new JsonObjectRequest(Request.Method.POST,"http://172.19.83.10:3000/receiv", requestJsonObject, new Response.Listener<JSONObject>() {


                    public void onResponse(JSONObject response) {
                        JSONArray J_JsonArray = new JSONArray();
                        try {
                            J_JsonArray = response.getJSONArray("results");
                            JSONObject dataObj = J_JsonArray.getJSONObject(0);

                            // if 동행자면 (서버에서 0인거 받아온다는 조건) 어떻게 서버에서 받아오는 지 모르겠음
                            // main screen 으로 가기
                            navigateToMainScreen_companion();

                            // if 보호자면
                            navigateToMainScreen_parents();

                        } catch(JSONException e) {
                            e.printStackTrace();
                            // 아디비번 틀리면 틀렸다고 toast 띄우기
                            Toast.makeText(LoginPage.this, "\"아이디 혹은 비밀번호 오류\"", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() { //수신에서 에러가 난 경우에만 작동하는 코드이다 즉 돌아가면 ㅈ대는 거다

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결 오류.", Toast.LENGTH_LONG).show();
                        Log.i("VolleyError", "Volley Error in receiv");
                    }
                });
                requestQueue.add(R_Object);
            }
        });

        // 뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_back);
            }
        });


    }

    // main screen 가는 함수
    private void navigateToMainScreen_companion() {
        // 아이디를 받아서 동행자인지 보호자인지 판별해서 동행자면 동행자 메인 화면으로
        Intent intent = new Intent(LoginPage.this, companion_main.class);
        startActivity(intent);
        finish();
    }

    private void navigateToMainScreen_parents() {
        Intent intent = new Intent(LoginPage.this, parents_main.class);
        startActivity(intent);
        finish();
    }
}


