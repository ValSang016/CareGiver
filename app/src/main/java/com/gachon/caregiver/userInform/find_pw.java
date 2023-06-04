package com.gachon.caregiver.userInform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.MainActivity;
import com.gachon.caregiver.R;
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
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password);

        idEditText = findViewById(R.id.input_id);
        nameEditText = findViewById(R.id.input_name);
        phoneEditText = findViewById(R.id.input_tel);
        findPasswordButton = findViewById(R.id.Login_login_button);
        Button back_bt = findViewById(R.id.Login_back_button);

        // Firebase Realtime Database에 연결
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        findPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = idEditText.getText().toString();
                String inputName = nameEditText.getText().toString();
                String inputPhNum = phoneEditText.getText().toString();

                readUserEmails(inputEmail, inputName, inputPhNum);
            }
        });

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_login = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(go_login);
            }
        });

    }

    private void readUserEmails(String inputEmail, String inputName, String inputPhNum) {
        DatabaseReference usersRef = database.getReference("users/UID");
        Query query = usersRef.orderByChild("email").equalTo(inputEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String name = userSnapshot.child("username").getValue(String.class);
                        String phNum = userSnapshot.child("phoneNumber").getValue(String.class);
                        String password = userSnapshot.child("password").getValue(String.class);

                        if (name != null && name.equals(inputName) && phNum != null && phNum.equals(inputPhNum)) {
                            providePassword(password);
                            return;
                        }
                    }
                    Toast.makeText(find_pw.this, "이름 또는 전화번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(find_pw.this, "이메일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(find_pw.this, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 일치하는 이메일을 가진 사용자의 비밀번호 제공
    private void providePassword(String password) {
        if (password != null) {
            Toast.makeText(this, "Password: " + password, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "비밀번호가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
