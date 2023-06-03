package com.gachon.caregiver.userInform.MainPage;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.runtime.snapshots.Snapshot;

import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.Manager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Manager_btn_clicked extends AppCompatActivity  {
    String userId;
    String photoUrl;
    String text;
    String UID;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    Button noBtn;
    Button yesBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_btn_clicked);

        Intent getIn = getIntent();
        UID = getIn.getStringExtra("id");
        Log.d("UID", "onCreate: " + UID);

        // Firebase Realtime Database의 데이터베이스 참조를 얻어옵니다.
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // 사용자 정보와 사진 URL 불러옴

        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);
        noBtn = findViewById(R.id.no);
        yesBtn = findViewById(R.id.yes);

        loadUserData(imageView, textView);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeApprovalStateYes();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeApprovalStateNo();

            }
        });

    }

    private void loadUserData(ImageView imageView, TextView textView) {
        DatabaseReference certificationListRef = database.getReference("users/certificationList");
        DatabaseReference userRef = certificationListRef.child(UID);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // 사용자의 정보와 사진 URL, 텍스트를 가져옴
                    userId = snapshot.child("id").getValue(String.class);
                    photoUrl = snapshot.child("imageUri").getValue(String.class);
                    text = snapshot.child("information").getValue(String.class);

                    // 가져온 데이터를 UI에 반영하거나 원하는 동작을 수행합니다.
                    // 예를 들어, ImageView에 이미지를 로드하고 TextView에 텍스트를 설정하는 등의 동작을 수행합니다.
                    Glide.with(Manager_btn_clicked.this)
                            .load(photoUrl)
                            .into(imageView);
                    textView.setText(text);
                    textView.setTextSize(30);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리를 여기에 작성하세요
            }
        });
    }

    private void changeApprovalStateYes(){
        DatabaseReference certificationListRef = database.getReference("users/certificationList");
        DatabaseReference userRef = certificationListRef.child(UID).child("approval");
        userRef.setValue("1")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Manager_btn_clicked.this, "승인 되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 데이터 업데이트에 실패한 경우
                        Toast.makeText(Manager_btn_clicked.this, "실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void changeApprovalStateNo(){
        DatabaseReference certificationListRef = database.getReference("users/certificationList");
        DatabaseReference userRef = certificationListRef.child(UID).child("approval");
        userRef.setValue("2")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Manager_btn_clicked.this, "거부 되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 데이터 업데이트에 실패한 경우
                        Toast.makeText(Manager_btn_clicked.this, "실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
