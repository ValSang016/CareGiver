package com.gachon.caregiver.userInform.MainPage;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_btn_clicked);

        // Firebase Realtime Database의 데이터베이스 참조를 얻어옵니다.
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // 사용자 정보와 사진 URL 불러옴
        loadUserData();

        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);

        Glide.with(Manager_btn_clicked.this)
                .load(photoUrl)
                .into(imageView);

        textView.setText(text);

    }

    private void loadUserData() {
        // 여기에서는 예시로 'users'라는 키에 저장된 사용자 정보를 다 가져옴
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 'users' 노드의 모든 데이터 봄
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // 각 사용자의 정보와 사진 URL을 가져옴
                    userId = userSnapshot.getKey();
                    photoUrl = userSnapshot.child("photoUrl").getValue(String.class);
                    text =  userSnapshot.child("userText").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 로딩이 실패한 경우
                Toast.makeText(Manager_btn_clicked.this, "데이터 로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
