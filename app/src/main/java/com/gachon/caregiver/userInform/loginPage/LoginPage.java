package com.gachon.caregiver.userInform.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.MainActivity;
import com.gachon.caregiver.R;
import com.gachon.caregiver.companion_main;
import com.gachon.caregiver.connect_server_data;
import com.gachon.caregiver.parents_main;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    SignInButton Google_Login;
    private static final int RC_SIGN_IN = 1000;
    private connect_server_data connectServerData;
    String ID;
    String PW;
    //Context mContext;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_class);
        mAuth = FirebaseAuth.getInstance(); //FirebaseAuth 인스턴스 초기화

        Button loginBtn = findViewById(R.id.Login_login_button);
        Button backBtn = findViewById(R.id.Login_back_button);

        EditText editTextID = findViewById(R.id.Login_idData);
        EditText editTextPW = findViewById(R.id.Login_passwordData);

        connectServerData = new connect_server_data();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = editTextID.getText().toString();
                PW = editTextPW.getText().toString();

                connectServerData.login_connect(ID,PW);
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


