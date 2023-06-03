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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class companion_apply_list extends AppCompatActivity {

    private DatabaseReference databaseReference;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companion_apply_list);

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
                    Intent send_apply = new Intent(companion_apply_list.this, Manager_btn_clicked.class);
                    startActivity(send_apply);
                }
            });

            // 생성한 버튼을 레이아웃에 추가
            buttonLayout.addView(button);
        }
    }

}
