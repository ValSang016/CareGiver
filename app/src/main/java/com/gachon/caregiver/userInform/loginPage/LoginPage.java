package com.gachon.caregiver.userInform.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gachon.caregiver.MainActivity;
import com.gachon.caregiver.R;

public class LoginPage extends AppCompatActivity {

    RequestQueue queue = Volley.newRequestQueue(this);
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); //로그인 하는 창 등장

        Button loginBtn = findViewById(R.id.Login_login_button);
        Button backBtn = findViewById(R.id.Login_back_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_back);
            }
        });
    }
}
