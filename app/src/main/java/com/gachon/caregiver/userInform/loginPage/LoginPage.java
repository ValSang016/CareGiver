package com.gachon.caregiver.userInform.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gachon.caregiver.MainActivity;
import com.gachon.caregiver.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginPage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

        SignInButton Google_Login;
        private static final int RC_SIGN_IN = 1000;
        private FirebaseAuth mAuth;
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

//            init();

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ID = editTextID.getText().toString();
                    PW = editTextPW.getText().toString();
                    requestLogin(ID, PW);

                }
            });

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                    .build();

            mAuth = FirebaseAuth.getInstance();

            Google_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent,RC_SIGN_IN);
                }
            });
        }

//        public void init(){
//            editTextID = (EditText)findViewById(R.id.);
//            editTextPW = (EditText)findViewById(R.id.editTextPW);
//            loginBtn = (Button)findViewById(R.id.activity_login_login_button);
//            Google_Login = (SignInButton)findViewById(R.id.Google_Login);
//            //mContext = getApplicationContext();
//        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    //구글 로그인 성공해서 파베에 인증
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                }
                else{
                    //구글 로그인 실패
                }
            }
        }
        private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(LoginActivity.this, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), EnrollActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }

        public void requestLogin(String ID, String PW){
            String url = "http://172.19.83.10:3000";

            //JSON형식으로 데이터 통신을 진행합니다!
            JSONObject testjson = new JSONObject();
            try {
                //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
                testjson.put("id", ID);
                testjson.put("password", PW);
                String jsonString = testjson.toString(); //완성된 json 포맷

                //이제 전송해볼까요?
                final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                    //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //받은 json형식의 응답을 받아
                            JSONObject jsonObject = new JSONObject(response.toString());

                            //key값에 따라 value값을 쪼개 받아옵니다.
                            String resultId = jsonObject.getString("approve_id");
                            String resultPassword = jsonObject.getString("approve_pw");

                            if(resultId.equals("OK") & resultPassword.equals("OK")){
                                Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                easyToast("로그인 실패");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonObjectRequest);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        void easyToast(String str){
            Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
        }
    }