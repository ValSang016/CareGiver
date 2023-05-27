package com.gachon.caregiver.userInform.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.loginPage.LoginPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPage_companion extends AppCompatActivity {

    private FirebaseAuth mAuth;

    String name;
    String birth;
    String gender;
    String sign_up_id;
    String sign_up_pw;
    String phone_number;

    isOkSignUP allGood = new isOkSignUP();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_companion);

        mAuth = FirebaseAuth.getInstance();


        Button next_bt = findViewById(R.id.go_to_login); //누름과 동시에 회원가입이 승인 되고 이후 다시 로그인 창으로 넘어가서 로그인을 할 수 있게 해준다.
        Button check_id_bt = findViewById(R.id.check_id);//아이디 중복 체크 버튼

        EditText sign_up_name = findViewById(R.id.name);
        EditText sign_up_birth = findViewById(R.id.birth);
        RadioGroup check_gender_group = findViewById(R.id.ckkmale);
        RadioButton sign_up_gender_f = findViewById(R.id.female);
        RadioButton sign_up_gender_m = findViewById(R.id.male);
        EditText make_up_id = findViewById(R.id.id_write);
        EditText make_up_pw = findViewById(R.id.password_write);
        EditText make_up_pw_r = findViewById(R.id.password_ok);
        EditText sign_up_phone_number = findViewById(R.id.phonenumber_write);

//      TextView valid = findViewById(R.id.validEmail);

        //이메일이 양식에 맞게 작성되는지
        make_up_id.addTextChangedListener(new TextWatcher() {
            SignUpChecking checking = new SignUpChecking();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String emailNeedToCheck = make_up_id.getText().toString();
                //아직은 안됨
                if(checking.isEmailValid(emailNeedToCheck)){
                    Toast.makeText(SignUpPage_companion.this, "이메일 양식이 적절합니다.",
                            Toast.LENGTH_SHORT).show();
                    allGood.setEmailGreat(true);
                } else {
//                    validEmail.setText("이메일 양식이 부적적합니다");
//                    validEmail.setTextColor(Color.RED);
                    Toast.makeText(SignUpPage_companion.this, "이메일 양식이 부적절합니다.",
                            Toast.LENGTH_SHORT).show();
                    allGood.setEmailGreat(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        make_up_pw_r.addTextChangedListener(new TextWatcher() {
            SignUpChecking checking = new SignUpChecking();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ps1 = make_up_pw.getText().toString();
                String ps2 = make_up_pw_r.getText().toString();
                if(checking.isPasswordConfirmed(ps1, ps2)){
                    Toast.makeText(SignUpPage_companion.this, "비밀번호가 일치합니다.",
                            Toast.LENGTH_SHORT).show();
                    allGood.setPwGreat(true);
                } else {
                    Toast.makeText(SignUpPage_companion.this, "비밀번호가 일치하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                    allGood.setPwGreat(false);
                }
            }
        });


        //성별 체크후 성별 보내주는 곳
        check_gender_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.female){
                      gender = "female";
                } else if (i==R.id.male) {
                      gender = "male";
                }
            }
        });

        //유흔이가 만들 아이디 중복 체크에 id값만 미리 보내서 체크를 해오는 것이다. 그에 대한 결과 값에 따라 중복이면 중복이다라는 toast 아니면 사용가능하다는 토스트를 사용한다.
        check_id_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //다음 버튼을 눌렀을 경우 다시 로그인 창으로 넘어갈 수 있게 하는 코드이다
        next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = sign_up_name.getText().toString();
                birth = sign_up_birth.getText().toString();
                sign_up_id = make_up_id.getText().toString();
                sign_up_pw = make_up_pw.getText().toString();
                phone_number = sign_up_phone_number.getText().toString();

                try {
                    if(allGood.okSignUp()) {
                        signUp(sign_up_id, sign_up_pw, name, birth, gender, phone_number, 0);  //파이어베이스 회원가입 메서드
                    } else{
                        Toast.makeText(SignUpPage_companion.this, "다시 입력해주세요.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(SignUpPage_companion.this, "다시 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private DatabaseReference mDatabase;
    // 회원가입 버튼 클릭 시 호출되는 메서드
    private void signUp(String email, String password, String username, String birth, String gender, String phoneNumber, Integer userTP) {
        Intent login = new Intent(SignUpPage_companion.this, LoginPage.class);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();
                            // 추가적인 사용자 정보 저장하거나 초기화 작업
                            UserInformation userInfo = new UserInformation(email, password, username, birth, gender, phoneNumber, userTP);
                            mDatabase.child("Users").child("UID").child(userId).setValue(userInfo);

                            // 회원가입 후에 다음 화면으로 이동하거나 액션이 필요한 경우에 대한 코드를 작성합니다.
                            startActivity(login);

                        } else {
                            // 회원가입 실패
                            Toast.makeText(SignUpPage_companion.this, "회원가입에 실패하였습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

