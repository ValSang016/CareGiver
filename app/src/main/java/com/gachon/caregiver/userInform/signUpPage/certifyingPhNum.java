package com.gachon.caregiver.userInform.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class certifyingPhNum extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private boolean mVerificationInProgress = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3000) {
            if (resultCode == RESULT_OK) {
                // 인증번호 확인이 성공한 경우
                mVerificationInProgress = true;
            } else {
                // 인증번호 확인이 실패한 경우
                Toast.makeText(this, "인증번호 확인에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_certify_phnum);

        mAuth = FirebaseAuth.getInstance();
        EditText certificationCode = findViewById(R.id.certificationcode);

        Button certify = findViewById(R.id.certifying_button);
        certify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = certificationCode.getText().toString().trim();
                PhoneAuthCredential credential = getIntent().getParcelableExtra("credential");
                if (credential == null) {
                    String verificationId = getIntent().getStringExtra("verificationId");
                    credential = PhoneAuthProvider.getCredential(verificationId, code);
                }
                verifyCode(credential);
            }
        });
    }
    private void verifyCode(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 인증에 성공한 경우
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            // 인증에 실패한 경우
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    }
                });
    }

}
