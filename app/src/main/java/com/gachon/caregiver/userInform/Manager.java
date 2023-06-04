package com.gachon.caregiver.userInform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.gachon.caregiver.R;

import com.gachon.caregiver.userInform.MainPage.Manager_btn_clicked;
import com.gachon.caregiver.userInform.MainPage.companion_apply_list;
import com.gachon.caregiver.userInform.MainPage.companion_certificate_btn_clicked;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Manager extends AppCompatActivity {

    private DatabaseReference usersRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager);


        getUserIdsFromDatabase();
    }
    List<String> userIds = new ArrayList<>();
    // 데이터베이스에서 저장된 user id 목록을 가져오는 메소드
    private void getUserIdsFromDatabase() {
        usersRef = FirebaseDatabase.getInstance().getReference("users/certificationList");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UID = dataSnapshot.getKey();
                if(UID != null) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        userIds.add(userId);
                    }
                    // 가져온 user id 목록을 사용하여 동적으로 버튼을 생성합니다.
                    createButtonsForUsers(userIds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 로딩이 실패한 경우에 대한 처리를 여기에 작성합니다.
                Toast.makeText(Manager.this, "데이터 로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 버튼을 동적으로 생성하는 메소드
    private void createButtonsForUsers(List<String> userIds) {
        // 버튼을 생성할 레이아웃
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);

        for (String userId : userIds) {
            Button button = new Button(this);
            button.setText("User: " + userId);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent send_apply = new Intent(Manager.this, Manager_btn_clicked.class);
                    send_apply.putExtra("id", userId);
                    startActivity(send_apply);
                }
            });

            // 생성한 버튼을 레이아웃에 추가합니다.
            buttonLayout.addView(button);
        }
    }
}
