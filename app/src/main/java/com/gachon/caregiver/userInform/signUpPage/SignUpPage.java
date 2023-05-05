package com.gachon.caregiver.userInform.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.gachon.caregiver.MainActivity;
import com.gachon.caregiver.R;

public class SignUpPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main); //로그인 하는 창 등장

        Button companion = (Button) findViewById(R.id.caregiver_signUp);
        Button Patient = (Button) findViewById(R.id.parent_signUp);
        Button back_bt = (Button) findViewById(R.id.Back_button);

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

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_back);
            }
        });
    }
}