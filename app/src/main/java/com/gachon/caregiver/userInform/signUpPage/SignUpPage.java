package com.gachon.caregiver.userInform.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;

public class SignUpPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); //로그인 하는 창 등장

        Button companion = (Button) findViewById();
        Button Patient = (Button) findViewById();

        companion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_login = new Intent(getApplicationContext(), SignUpPage_companion.class);
                startActivity(intent_login);
            }
        });

        Patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_login = new Intent(getApplicationContext(), SignUpPage_patient.class);
                startActivity(intent_login);
            }
        });
    }
}