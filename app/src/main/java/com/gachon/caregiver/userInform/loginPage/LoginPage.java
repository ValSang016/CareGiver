package com.gachon.caregiver.userInform.loginPage;

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
import com.gachon.caregiver.userInform.MainPage.mainscreen_companion;
import com.gachon.caregiver.userInform.MainPage.mainscreen_parents;
import com.gachon.caregiver.userInform.Manager;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {

    String ID;
    String PW;
    //Context mContext;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_class);
        mAuth = FirebaseAuth.getInstance(); //FirebaseAuth 인스턴스 초기화

        Button loginBtn = findViewById(R.id.Login_login_button);
        Button backBtn = findViewById(R.id.Login_back_button);

        EditText editTextID = findViewById(R.id.Login_idData);
        EditText editTextPW = findViewById(R.id.Login_passwordData);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = editTextID.getText().toString();
                PW = editTextPW.getText().toString();

                logIn(ID, PW);

            }
        });

        // 뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_back);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        // 이미 로그인된 사용자인 경우 자동으로 홈 화면으로 이동합니다.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            callbackUserData();
        }
    }
    private void logIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            try {
                                Log.d("loginAble", "is alright?");
                                callbackUserData();
                            }catch (Exception e){
                                Toast.makeText(LoginPage.this, "로그인에 실패했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // 로그인 실패
                            Toast.makeText(LoginPage.this, "로그인에 실패했습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void callbackUserData(){
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        Log.d("userID",userId + " is alright?");
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users/UID");
        databaseRef.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userTP = dataSnapshot.child("userTP").getValue(String.class);
                    Log.d("userTP",userTP + "is alright?");
                    navigateToMainScreen(userTP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginPage.this, "로그인에 실패했습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void navigateToMainScreen(String userTP){
        if (userTP.equals("0")) {
            // userTP가 0인 경우 Companion 화면으로 전환
             navigateToMainScreen_companion();
        } else if(userTP.equals("1")){
            // userTP가 1인 경우 다른 화면으로 전환
            navigateToMainScreen_parents();
        } else if(userTP.equals("3")){
            navigateToMainScreen_admin();
        }
    }

    // main screen 가는 함수
    private void navigateToMainScreen_companion() {
        // 아이디를 받아서 동행자인지 보호자인지 판별해서 동행자면 동행자 메인 화면으로
        Intent intent = new Intent(LoginPage.this, mainscreen_companion.class);
        startActivity(intent);
        finish();
    }

    private void navigateToMainScreen_parents() {
        Intent intent = new Intent(LoginPage.this, mainscreen_parents.class);
        startActivity(intent);
        finish();
    }
    private void navigateToMainScreen_admin(){
        Intent intent = new Intent(LoginPage.this, Manager.class);
        startActivity(intent);
        finish();
    }
}


