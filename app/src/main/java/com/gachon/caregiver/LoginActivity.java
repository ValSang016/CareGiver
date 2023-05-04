package com.gachon.caregiver;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    RequestQueue queue = Volley.newRequestQueue(this);
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); //로그인 하는 창 등장

        Button lgbutton = findViewById(R.id.login_button);

        lgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the request to the RequestQueue.
//                queue.add(stringRequest);
            }
        });
    }

}
