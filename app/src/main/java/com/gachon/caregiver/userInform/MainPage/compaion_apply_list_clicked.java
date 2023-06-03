package com.gachon.caregiver.userInform.MainPage;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gachon.caregiver.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class compaion_apply_list_clicked extends AppCompatActivity  {
    String userId;
    String photoUrl;
    String text;

    ImageView imageView;
    TextView textView;
    private DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companion_apply_list_clicked);

        // Firebase Realtime Database의 데이터베이스 참조를 얻어옵니다.
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // 사용자 정보와 사진 URL 불러옴
        loadUserData();


    }

    private void loadUserData() {

        databaseReference.child("users").orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 해당 사용자의 데이터를 가져옴
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // 각 사용자의 정보와 사진 URL, 텍스트를 가져옴
                    userId = userSnapshot.child("userId").getValue(String.class);
                    photoUrl = userSnapshot.child("photoUrl").getValue(String.class);
                    text =  userSnapshot.child("userText").getValue(String.class);
                }

                ImageView imageView = findViewById(R.id.imageView);
                TextView textView = findViewById(R.id.textView);

                // 가져온 데이터를 UI에 반영하거나 원하는 동작을 수행합니다.
                // 예를 들어, ImageView에 이미지를 로드하고 TextView에 텍스트를 설정하는 등의 동작을 수행할 수 있습니다.
                Glide.with(compaion_apply_list_clicked.this)
                        .load(photoUrl)
                        .into(imageView);
                textView.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 로딩이 실패한 경우에 대한 처리를 여기에 작성합니다.
                Toast.makeText(compaion_apply_list_clicked.this, "데이터 로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
