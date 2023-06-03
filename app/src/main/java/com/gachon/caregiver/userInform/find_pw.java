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
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    boolean foundName = false;
    boolean foundPhNum = false;
    boolean foundPW = false;
    String getFoundPW;

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
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DatabaseReference emailRef = userSnapshot.child("email").getRef();
                    emailRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String email = dataSnapshot.getValue(String.class);
                            // 읽어온 이메일 주소 사용
                            if (email != null) {
                                // 이메일 주소와 입력한 이메일이 일치하는지 확인
                                if (email.equals(inputEmail)) {
                                    DatabaseReference nameRef = userSnapshot.child("username").getRef();
                                    DatabaseReference phNumRef = userSnapshot.child("phoneNumber").getRef();
                                    DatabaseReference passwordRef = userSnapshot.child("password").getRef();
                                    nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String name = snapshot.getValue(String.class);
                                            if (name != null && name.equals(inputName)) {
                                                // name이 작성한 정보와 일치하는 경우
                                                foundName = true;
                                                checkPhNum(inputPhNum, passwordRef, phNumRef);
                                            } else {
                                                // 일치하지 않는 경우
                                                foundName = false;
                                                checkPhNum(inputPhNum, passwordRef, phNumRef);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(find_pw.this, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else {
                                    Toast.makeText(find_pw.this, "이메일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // 데이터 읽기가 취소되었거나 실패한 경우
                            // TODO: 오류 처리
                            Toast.makeText(find_pw.this, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 읽기가 취소되었거나 실패한 경우
                // TODO: 오류 처리
                Toast.makeText(find_pw.this, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPhNum(String inputPhNum, DatabaseReference passwordRef, DatabaseReference phNumRef){
        phNumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phNum = snapshot.getValue(String.class);
                if (phNum != null && phNum.equals(inputPhNum)){
                    foundPhNum = true;
                    checkPassword(passwordRef);
                } else {
                    foundPhNum = false;
                    checkPassword(passwordRef);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(find_pw.this, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkPassword(DatabaseReference passwordRef) {
        passwordRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String password = dataSnapshot.getValue(String.class);
                if (password != null) {
                    // 비밀번호가 유효한 경우
                    foundPW = true;
                    getFoundPW = password;
                } else {
                    // 비밀번호가 없는 경우
                    foundPW = false;
                }
                providePassword(getFoundPW);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 읽기가 취소되었거나 실패한 경우
                Toast.makeText(find_pw.this, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                providePassword(null);
            }
        });
    }

    // 일치하는 이메일을 가진 사용자의 비밀번호 제공
    private void providePassword(String getFoundPW) {
        // TODO: 비밀번호 제공
        if (foundName && foundPhNum && foundPW) {
            Toast.makeText(this, "Password: " + getFoundPW, Toast.LENGTH_SHORT).show();
        } else if (!foundName | !foundPhNum) {
            Toast.makeText(this, "이름 또는 전화번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        } else if (!foundPW) {
            Toast.makeText(this, "비밀번호가 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "이메일이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }



}
