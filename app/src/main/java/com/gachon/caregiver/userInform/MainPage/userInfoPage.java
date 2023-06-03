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

import com.gachon.caregiver.R;

public class userInfoPage extends AppCompatActivity {

    private EditText name;
    private EditText old;
    private EditText address;
    private EditText special;

    private TextView tvCount;
    private Button upBtn;
    private Button downBtn;
    private int count = 0;

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.myinformation_companion);

        name = findViewById(R.id.name);
        old = findViewById(R.id.age);
        address = findViewById(R.id.address);
        special = findViewById(R.id.significant);

        upBtn = findViewById(R.id.btn_add);
        downBtn = findViewById(R.id.btn_minus);
        tvCount = findViewById(R.id.tv_count);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: upBtn : " + view.getClass().getName());
                count++;
                tvCount.setText(count + "");

                // 버튼 비활성화
                upBtn.setEnabled(false);
                downBtn.setEnabled(false);
            }
        });

        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    count--;
                    tvCount.setText(count + "");

                // 버튼 비활성화
                upBtn.setEnabled(false);
                downBtn.setEnabled(false);
            }
        });

        Button back_bt = findViewById(R.id.Login_back_button);

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_main = new Intent(getApplicationContext(), mainscreen_companion.class);
                startActivity(go_main);
            }
        });
    }
}
