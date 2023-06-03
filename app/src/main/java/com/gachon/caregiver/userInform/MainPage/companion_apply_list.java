package com.gachon.caregiver.userInform.MainPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.Manager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class companion_apply_list extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companion_apply_list);

        mAuth = FirebaseAuth.getInstance(); //FirebaseAuth 인스턴스 초기화

        // Firebase 데이터베이스 참조

        // Firebase 데이터베이스에서 사용자 데이터 읽기
        readUserData();
    }

    private void readUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/certificationList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 사용자 데이터 목록 가져오기
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String databaseUid = snapshot.getKey(); // 데이터베이스의 UID 가져오기
                    if (databaseUid.equals(uid)) { // 현재 사용자의 UID와 일치하는 데이터인지 확인
                        User user = snapshot.getValue(User.class); // User 객체로 데이터 변환
                        List<User> userList = new ArrayList<>();
                        userList.add(user);
                        displayUserData(userList); // 데이터를 화면에 표시하는 메소드 호출
                        break; // 일치하는 UID를 찾았으므로 루프 종료
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 읽기 실패 시 처리
                Toast.makeText(companion_apply_list.this, "데이터를 읽어올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void displayUserData(List<User> userList) {
        for (User user : userList) {
            String approvalText;
            switch (user.getApproval()) {
                case "0":
                    approvalText = "대기중";
                    break;
                case "1":
                    approvalText = "승인됨";
                    break;
                case "2":
                    approvalText = "거부됨";
                    break;
                default:
                    approvalText = "알 수 없음";
            }

            TextView nameTextView = findViewById(R.id.nameTextView);
            TextView approvalTextView = findViewById(R.id.approvalTextView);

            nameTextView.setText(user.getEmail());
            approvalTextView.setText(approvalText);
        }
    }


    // User 클래스 예시 (사용자 데이터 구조에 맞게 수정해야 함)
        private static class User {
            private String email;
            private String approval;
            private String uid; // UID 정보 추가

            public String getEmail() {
                return email;
            }

            public String getApproval() {
                return approval;
            }

            public String getUid() {
                return uid;
            }
        }

    }

