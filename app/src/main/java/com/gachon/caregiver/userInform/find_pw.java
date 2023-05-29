package com.gachon.caregiver.userInform;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.loginPage.LoginPage;
import com.gachon.caregiver.userInform.signUpPage.SignUpPage_companion;
import com.gachon.caregiver.userInform.signUpPage.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class find_pw extends AppCompatActivity {

    private EditText idEditText;
    private EditText nameEditText;
    private EditText phoneEditText;
    private Button findPasswordButton;

    private DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idEditText = findViewById(R.id.input_id);
        nameEditText = findViewById(R.id.input_name);
        phoneEditText = findViewById(R.id.input_tel);
        findPasswordButton = findViewById(R.id.Login_login_button);
        Button back_bt = findViewById(R.id.Login_back_button);

        // Firebase Realtime Database에 연결
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        findPasswordButton.setOnClickListener(view -> findPassword());

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_login = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(go_login);
            }
        });

    }


    private void findPassword() {
        String id = idEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        Query query = databaseReference.orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            //비밀번호 관련 데이터 처리하는 곳인듯
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean found = false;
                String password = "";

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInformation userInfo = snapshot.getValue(UserInformation.class);
                    if (userInfo != null && userInfo.getUsername().equals(name) && userInfo.getPhoneNumber().equals(phone)) {
                        found = true;
                        password = userInfo.getPassword();
                        break;
                    }
                }

                if (found) {
                    Toast.makeText(find_pw.this,"비밀번호: " + password,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(find_pw.this,"일치하는 정보를 찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase", "Failed to read value: " + databaseError.getMessage());
            }
        });
    }

}
