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

        Button calendar = findViewById(R.id.callendar);
        Button matchingList = findViewById(R.id.matchingList);

        //달력으로 이동하는 함수
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_cal = new Intent(mainscreen_companion.this,CalendarCompanionActivity.class);
                startActivity(go_cal);
            }
        });

        Button map = findViewById(R.id.map);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_map = new Intent(mainscreen_companion.this, kakaomap.class);
                startActivity(go_map);
            }
        });

        Button info = findViewById(R.id.information);

        //차후에 여기 무조건 user info 창 만들어서 연결해야된다 지금은 그냥 맴에 연결해둔 상태
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_info = new Intent(mainscreen_companion.this, userInfoPage.class);
                startActivity(go_info);
            }
        });

        Button certificate_apply = findViewById(R.id.add_certificate);
        certificate_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_cert = new Intent(mainscreen_companion.this, companion_certificate_btn_clicked.class);
                startActivity(go_cert);
            }
        });
        matchingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_cert = new Intent(mainscreen_companion.this, matching_accept_companion.class);
                startActivity(go_cert);
            }
        });
    }
}
