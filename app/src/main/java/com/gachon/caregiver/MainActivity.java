package com.gachon.caregiver;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login_bt = (Button) findViewById(R.id.login_button);//로그인 버튼 연결
        Button find_pw = (Button) findViewById(R.id.find_password);//비밀번호 찾기 연결
        Button some_id = (Button) findViewById(R.id.some_id); //회원가입 버튼 연결

        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_login);
            }
        });

        find_pw.setOnClickListener(new View.OnClickListener(){ //비밀번호 찾기 하는 곳
            @Override
           public void onClick(View View){
               Intent intent_find = new Intent(getApplicationContext(), find_pw.class);
               startActivity(intent_find);
           }
        });

        some_id.setOnClickListener(new View.OnClickListener(){//회원가입하는 곳
            @Override
            public void onClick(View View){
                Intent intent_sign_up = new Intent(getApplicationContext(), sign_inActivity.class);
                startActivity(intent_sign_up);
            }
        });
    }
}