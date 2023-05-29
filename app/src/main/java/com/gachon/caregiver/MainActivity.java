package com.gachon.caregiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.gachon.caregiver.userInform.loginPage.LoginPage;
import com.gachon.caregiver.userInform.signUpPage.SignUpPage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this); //firebase 초기화

        Button login_bt = findViewById(R.id.Main_login_button);//로그인 버튼 연결
        Button find_pw = findViewById(R.id.Main_findPw_button);//비밀번호 찾기 연결
        Button signup_bt = findViewById(R.id.Main_signUp_button); //회원가입 버튼 연결

        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                try {
                    startActivity(intent);
                } catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"병신아 오류났다",Toast.LENGTH_SHORT).show();
                }
            }
        });

        find_pw.setOnClickListener(new View.OnClickListener(){ //비밀번호 찾기 하는 곳
            @Override
           public void onClick(View View){
//               Intent intent = new Intent(getApplicationContext(),);
//               startActivity(intent);
           }
        });

        signup_bt.setOnClickListener(new View.OnClickListener(){//비밀번호 찾기 하는 곳
            @Override
            public void onClick(View View){
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(intent);
            }
        });
    }
}