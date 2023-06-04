package com.gachon.caregiver.userInform.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.Manager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class matching_accept_companion extends AppCompatActivity {
    private DatabaseReference usersRef;
    private List<String> userIds;
    private FirebaseAuth mAuth;
    String curUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_accept_companion);
        mAuth = FirebaseAuth.getInstance(); //FirebaseAuth 인스턴스 초기화
        curUID = mAuth.getUid();

        userIds = new ArrayList<>();
        getUsersFromDatabase();
    }

    private void getUsersFromDatabase() {
        usersRef = FirebaseDatabase.getInstance().getReference("users/matching");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String companionUID = userSnapshot.child("companionUID").getValue(String.class);
                    if (companionUID != null && companionUID.equals(curUID)) {
                        String userId = userSnapshot.getKey();
                        userIds.add(userId);
                    }
                }
                // 가져온 user id 목록을 사용하여 동적으로 버튼을 생성합니다.
                createLayoutForUsers(userIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 로딩이 실패한 경우에 대한 처리를 여기에 작성합니다.
                Toast.makeText(matching_accept_companion.this, "데이터 로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createLayoutForUsers(List<String> userIds) {
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);

        for (String userId : userIds) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/matching/" + userId + "/name");
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.getValue(String.class);

                        Button button = new Button(matching_accept_companion.this);
                        button.setText("User: " + userName);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent send = new Intent(matching_accept_companion.this, mappar_info.class);
                                send.putExtra("id", userId);
                                startActivity(send);
                            }
                        });

                        // 생성한 버튼을 레이아웃에 추가합니다.
                        buttonLayout.addView(button);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(matching_accept_companion.this, "데이터 로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private void setUserName(String userId, TextView textView) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/matching/" + userId + "/name");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userName = dataSnapshot.getValue(String.class);
                    textView.setText("User ID: " + userName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(matching_accept_companion.this, "데이터 로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
