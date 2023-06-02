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

import com.gachon.caregiver.MainActivity;
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

import java.util.ArrayList;
import java.util.List;

public class find_pw extends AppCompatActivity {

    public EditText idEditText;
    public EditText nameEditText;
    public EditText phoneEditText;
    private Button findPasswordButton;

    private DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password);

        idEditText = findViewById(R.id.input_id);
        nameEditText = findViewById(R.id.input_name);
        phoneEditText = findViewById(R.id.input_tel);
        findPasswordButton = findViewById(R.id.Login_login_button);
        Button back_bt = findViewById(R.id.Login_back_button);

        // Firebase Realtime Database에 연결
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        findPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = idEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String name = nameEditText.getText().toString();
                readUserEmails();
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
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    boolean userFound = false;

    // 사용자 이메일 주소를 저장할 List
    private List<String> userEmailList = new ArrayList<>();

    // 입력한 이메일
    private String inputEmail = "작성한 이메일";

    // 사용자 이메일 주소를 읽는 메서드
    public void readUserEmails() {
        DatabaseReference usersRef = database.getReference("users/UID");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    DatabaseReference emailRef = userSnapshot.child("email").getRef();
                    emailRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String email = dataSnapshot.getValue(String.class);
                            // 읽어온 이메일 주소 사용
                            if (email != null) {
                                // 이메일 주소가 유효한 경우
                                userEmailList.add(email);
                                // 이메일 주소와 입력한 이메일이 일치하는지 확인
                                if (email.equals(inputEmail)) {
                                    // 일치하는 이메일을 가진 사용자의 비밀번호 제공
                                    DatabaseReference passwordRef = userSnapshot.child("password").getRef();
                                    passwordRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String password = dataSnapshot.getValue(String.class);
                                            if (password != null) {
                                                // 비밀번호가 유효한 경우
                                                providePassword(userId, password);
                                            } else {
                                                // 비밀번호가 없는 경우
                                                handleNoPassword();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // 데이터 읽기가 취소되었거나 실패한 경우
                                            // TODO: 오류 처리
                                        }
                                    });
                                }
                            }
                            // 모든 사용자의 이메일 주소를 읽었는지 확인
                            if (userEmailList.size() == dataSnapshot.getChildrenCount()) {
                                // 모든 사용자의 이메일 주소를 읽었으므로 작업 수행
                                handleEmailComparison();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // 데이터 읽기가 취소되었거나 실패한 경우
                            // TODO: 오류 처리
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 읽기가 취소되었거나 실패한 경우
                // TODO: 오류 처리
            }
        });
    }

    // 이메일 주소 비교 후 작업 수행
    private void handleEmailComparison() {
        // userEmailList에서 각 사용자의 이메일 주소를 가져와 사용
        // TODO: 원하는 동작 수행
    }

    // 일치하는 이메일을 가진 사용자의 비밀번호 제공
    private void providePassword(String userId, String password) {
        // TODO: 비밀번호 제공
        Toast.makeText(this, "User ID: " + userId + ", Password: " + password, Toast.LENGTH_SHORT).show();
    }

    // 비밀번호가 없는 경우 처리
    private void handleNoPassword() {
        // TODO: 비밀번호 없음 처리
        Toast.makeText(this, "No password found for the user with the input email.", Toast.LENGTH_SHORT).show();
    }



}
