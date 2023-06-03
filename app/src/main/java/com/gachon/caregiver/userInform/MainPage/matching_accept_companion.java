package com.gachon.caregiver.userInform.MainPage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.Manager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class matching_accept_companion extends AppCompatActivity {
    private DatabaseReference usersRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_accept_companion);
        getUserIdsFromDatabase();
    }
    List<String> userIds = new ArrayList<>();
    // 데이터베이스에서 저장된 user id 목록을 가져오는 메소드
    private void getUserIdsFromDatabase() {
        usersRef = FirebaseDatabase.getInstance().getReference("users/matching");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UID = dataSnapshot.getKey();
                DatabaseReference emailRef = dataSnapshot.child("approval").getRef();
                if(UID != null) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        userIds.add(userId);
                    }
                    // 가져온 user id 목록을 사용하여 동적으로 버튼을 생성합니다.
                    createLayoutForUsers(userIds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 로딩이 실패한 경우에 대한 처리를 여기에 작성합니다.
                Toast.makeText(matching_accept_companion.this, "데이터 로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createLayoutForUsers(List<String> userIds) {

        for (String userId : userIds) {
            // RelativeLayout 생성
            RelativeLayout layout = new RelativeLayout(this);
            layout.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));

            String userName = getUserNameFromDatabase(userId);

            // 첫 번째 TextView 생성
            TextView textView1 = new TextView(this);
            textView1.setText("User ID: " + userName);
            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params1.addRule(RelativeLayout.ALIGN_PARENT_START);
            layout.addView(textView1, params1);

            // Button 1 생성
            Button button1 = new Button(this);
            button1.setText(" 수락 ");
            RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params3.addRule(RelativeLayout.BELOW, textView1.getId());
            params3.addRule(RelativeLayout.ALIGN_PARENT_START);
            layout.addView(button1, params3);

            // Button 2 생성
            Button button2 = new Button(this);
            button2.setText(" 거절 ");
            RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params4.addRule(RelativeLayout.BELOW, textView1.getId());
            params4.addRule(RelativeLayout.RIGHT_OF, button1.getId());
            layout.addView(button2, params4);

            // Button 3 생성
            Button button3 = new Button(this);
            button3.setText(" 정보 보기 ");
            RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params5.addRule(RelativeLayout.BELOW, textView1.getId());
            params5.addRule(RelativeLayout.RIGHT_OF, button2.getId());
            layout.addView(button3, params5);

        }
    }
    private String getUserNameFromDatabase(String userId) {
        final String[] userName = {""};

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/matching" + userId + "/name");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userName[0] = dataSnapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(matching_accept_companion.this, "데이터 로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return userName[0];
    }

}
