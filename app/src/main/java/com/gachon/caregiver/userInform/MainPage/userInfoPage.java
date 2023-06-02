package com.gachon.caregiver.userInform.MainPage;

import static android.content.ContentValues.TAG;

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
            }
        });

        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    count--;
                    tvCount.setText(count + "");
            }
        });


        // 이제 보호자가 특정 동행인에 대해 추천 or 비추천 버튼 눌렀으면
        // 버튼을 비활성화 하거나 안보이게 하는 코드 추가해야되는데
        // 내가 원하는 걸 찾기 어려워서 찾은게 시간에 따라 버튼 중복 클릭이 안되게 하는 게 있는데
        // 그거 야매로 쓰는 방법도 염두에 두는 중
    }
}
