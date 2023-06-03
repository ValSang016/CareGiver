package com.gachon.caregiver.userInform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.gachon.caregiver.R;

import com.gachon.caregiver.userInform.MainPage.Manager_btn_clicked;
import com.gachon.caregiver.userInform.MainPage.companion_certificate_btn_clicked;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Manager extends AppCompatActivity {

    private DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager);

        // Firebase Realtime Database의 데이터베이스 참조를 얻어옵니다.
        databaseReference = FirebaseDatabase.getInstance().getReference();

        createButtonsForUsers();

    }

    // 데이터베이스에서 저장된 user id 목록을 가져오는 메소드
    private List<String> getUserIdsFromDatabase() {
        List<String> userIds = new ArrayList<>();
        userIds.add("user1");
        userIds.add("user2");
        userIds.add("user3");
        return userIds;
    }

    // 버튼을 동적으로 생성하는 메소드
    private void createButtonsForUsers() {
        List<String> userIds = getUserIdsFromDatabase();

        // 버튼을 생성할 레이아웃
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);

        for (String userId : userIds) {
            Button button = new Button(this);
            button.setText("User: " + userId);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent send_apply = new Intent(getApplicationContext(), Manager_btn_clicked.class);
                    startActivity(send_apply);
                }
            });

            // 생성한 버튼을 레이아웃에 추가
            buttonLayout.addView(button);
        }
    }


}
