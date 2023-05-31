package com.gachon.caregiver.userInform.MainPage;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;

public class userInfoPage extends AppCompatActivity {

    private EditText name;
    private EditText old;
    private EditText address;
    private EditText special;

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.myinformation_companion);

        name = findViewById(R.id.name);
        old = findViewById(R.id.age);
        address = findViewById(R.id.address);
        special = findViewById(R.id.significant);


    }
}
