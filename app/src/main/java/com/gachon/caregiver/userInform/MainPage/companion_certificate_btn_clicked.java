package com.gachon.caregiver.userInform.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
public class companion_certificate_btn_clicked  extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companion_certificate_button_clicked);

        Button apply = findViewById(R.id.apply);
        Button apply_result = findViewById(R.id.apply_result);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_apply = new Intent(getApplicationContext(), companion_apply.class);
                startActivity(go_apply);
            }
        });

        apply_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_applyResult = new Intent(getApplicationContext(), companion_apply_list.class);
                startActivity(go_applyResult);
            }
        });

    }
}
