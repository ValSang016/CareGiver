package com.gachon.caregiver.userInform.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.loginPage.LoginPage;

public class SignUpPage_companion extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_companion);

        Button next_bt = findViewById(R.id.go_to_login);

        //다음 버튼을 눌렀을 경우 다시 로그인 창으로 넘어갈 수 있게 하는 코드이다
        next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(login);
            }
        });

    }
}
