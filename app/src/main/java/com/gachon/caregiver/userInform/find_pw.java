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

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_class); //로그인 하는 창 등장

        //데이터 서버와의 연결
        mAuth = FirebaseAuth.getInstance();

        //비밀번호 찾기에서 받아올 문장
        //   EditText name = findViewById(R.id.name);
        //   EditText id = findViewById(R.id.id);
        //   EditText phone = findViewById(R.id.phone);

        //비밀번호 찾기를 눌렀을 때 실행될 버튼
     //  Button find = findViewById(R.id.button);
       // find.setOnClickListener(new View.OnClickListener() {
            //@Override
         //   public void onClick(View view) {

          //  }
        //});

    }
}
