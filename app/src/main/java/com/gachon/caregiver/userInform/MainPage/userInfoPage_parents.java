package com.gachon.caregiver.userInform.MainPage;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.MainActivity;
import com.gachon.caregiver.R;

public class userInfoPage_parents extends AppCompatActivity {

    private EditText name;
    private EditText old;
    private EditText address;
    private EditText special;


    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.myinformation_parents);

        name = findViewById(R.id.name);
        old = findViewById(R.id.age);
        special = findViewById(R.id.significant);
        Button back_bt = findViewById(R.id.Login_back_button);

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_main = new Intent(getApplicationContext(), mainscreen_parents.class);
                startActivity(go_main);
            }
        });

    }
}
