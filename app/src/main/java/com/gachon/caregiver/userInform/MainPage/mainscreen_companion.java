package com.gachon.caregiver.userInform.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;

public class mainscreen_companion extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen_companion);

        Button callendar = findViewById(R.id.callendar);

        //달력으로 이동하는 함수
        callendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_cal = new Intent(getApplicationContext(),calendar_companion.class);
                startActivity(go_cal);
            }
        });
    }
}
