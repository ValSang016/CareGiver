package com.gachon.caregiver.userInform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.signUpPage.SignUpPage_companion;
import com.google.firebase.auth.FirebaseAuth;

public class find_pw extends AppCompatActivity {

    private FirebaseAuth mAuth;

    String f_id;
    String f_name;
    String f_tel;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password); //로그인 하는 창 등장

        //데이터 서버와의 연결
        mAuth = FirebaseAuth.getInstance();

        //비밀번호 찾기에서 받아올 문장
           EditText id = findViewById(R.id.input_id);
           EditText name = findViewById(R.id.input_name);
           EditText phone = findViewById(R.id.input_tel);

        //비밀번호 찾기를 눌렀을 때 실행될 버튼
        Button find = findViewById(R.id.Login_login_button);
         find.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  f_id = id.getText().toString();
                f_name = name.getText().toString();
                f_tel = phone.getText().toString();
            }
        });

    }
}
