package com.gachon.caregiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.gachon.caregiver.userInform.loginPage.LoginPage;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login_bt = findViewById(R.id.main_login_button);//로그인 버튼 연결
        Button find_pw = findViewById(R.id.Main_findPw_button);//비밀번호 찾기 연결
        Button some_id = findViewById(R.id.Main_signUp_button); //회원가입 버튼 연결


        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });

        find_pw.setOnClickListener(new View.OnClickListener(){ //비밀번호 찾기 하는 곳
            @Override
           public void onClick(View View){
//               Intent intent = new Intent(getApplicationContext(),);
//               startActivity(intent);
           }
        });

        some_id.setOnClickListener(new View.OnClickListener(){//비밀번호 찾기 하는 곳
            @Override
            public void onClick(View View){
//                Intent intent = new Intent(getApplicationContext(),);
//                startActivity(intent);
            }
        });
    }
}