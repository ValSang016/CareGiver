package com.gachon.caregiver.userInform.signUpPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.loginPage.LoginPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;


public class SignUpPage_companion extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String name;
    String birth;
    String gender;
    String sign_up_id;
    String sign_up_pw;
    String phone_number;

    boolean checkEmailDuplicate = false;

    Uri uri;
    ImageView imageView;

    isOkSignUP allGood = new isOkSignUP();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_companion);

        mAuth = FirebaseAuth.getInstance();


        Button next_bt = findViewById(R.id.go_to_login); //누름과 동시에 회원가입이 승인 되고 이후 다시 로그인 창으로 넘어가서 로그인을 할 수 있게 해준다.
        Button check_id_bt = findViewById(R.id.check_id);//아이디 중복 체크 버튼
        Button selectImageBtn = findViewById(R.id.btn_UploadPicture);//사진 가져오기
        imageView = findViewById(R.id.user_image); //가져온 사진 보여주는 이미지뷰

        EditText sign_up_name = findViewById(R.id.name);
        EditText sign_up_birth = findViewById(R.id.birth);
        RadioGroup check_gender_group = findViewById(R.id.ckkmale);
        RadioButton sign_up_gender_f = findViewById(R.id.female);
        RadioButton sign_up_gender_m = findViewById(R.id.male);
        EditText make_up_id = findViewById(R.id.id_write);
        EditText make_up_pw = findViewById(R.id.password_write);
        EditText make_up_pw_r = findViewById(R.id.password_ok);
        EditText sign_up_phone_number = findViewById(R.id.phonenumber_write);

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
                    make_up_id.setTextColor(Color.GREEN);

//                    Toast.makeText(SignUpPage_companion.this, "이메일 양식이 적절합니다.",
//                            Toast.LENGTH_SHORT).show();
                    allGood.setEmailGreat(true);
                } else {
                    make_up_id.setTextColor(Color.RED);
//                    Toast.makeText(SignUpPage_companion.this, "이메일 양식이 부적절합니다.",
//                            Toast.LENGTH_SHORT).show();
                    allGood.setEmailGreat(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        check_id_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailNeedToCheck = make_up_id.getText().toString();
                checkDuplicateEmail(emailNeedToCheck); // 이메일 중복 확인 메서드
            }
        });


        make_up_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SignUpChecking checking = new SignUpChecking();

                String ps1 = make_up_pw.getText().toString();
                String ps2 = make_up_pw_r.getText().toString();
                if(checking.isPasswordConfirmed(ps1, ps2)){
                    make_up_pw.setTextColor(Color.GREEN);
                    make_up_pw_r.setTextColor(Color.GREEN);
                    allGood.setPwGreat(true);
                } else {
                    make_up_pw.setTextColor(Color.RED);
                    make_up_pw_r.setTextColor(Color.RED);
                    allGood.setPwGreat(false);
                }
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
                    make_up_pw.setTextColor(Color.GREEN);
                    make_up_pw_r.setTextColor(Color.GREEN);
                    allGood.setPwGreat(true);
                } else {
                    make_up_pw.setTextColor(Color.RED);
                    make_up_pw_r.setTextColor(Color.RED);
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
        // 사진 가져오는 버튼
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bringImg = new Intent(Intent.ACTION_PICK);
                bringImg.setType("image/*");

                //이거다
                startActivityResult.launch(bringImg);
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
                    if(allGood.okSignUp()&&checkEmailDuplicate) {
                        signUp(sign_up_id, sign_up_pw, name, birth, gender, phone_number, "0");  //파이어베이스 회원가입 메서드
                    } else if(!allGood.okSignUp()) {
                        Toast.makeText(SignUpPage_companion.this, "이메일 또는 비밀번호를 다시 입력해주세요.",
                                Toast.LENGTH_SHORT).show();
                    }else if(!checkEmailDuplicate){
                        Toast.makeText(SignUpPage_companion.this, "이메일 중복 확인을 해주세요.",
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
    private void signUp(String email, String password, String username, String birth, String gender, String phoneNumber, String userTP) {
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

                    // 회원가입 후에 다음 화면
                    startActivity(login);
                } else {
                    // 회원가입 실패
                    Toast.makeText(SignUpPage_companion.this, "회원가입에 실패하였습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        uri = result.getData().getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
//                            uploadImageToFirebase(uri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

//    private void uploadImageToFirebase(){
//        StorageReference
//    }

    //이메일 중복 확인 메서드
    private void checkDuplicateEmail(String email) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                                // 이메일이 이미 사용 중인 경우
                                Toast.makeText(SignUpPage_companion.this, "사용중인 이메일입니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                // 이메일이 사용 가능한 경우
                                Toast.makeText(SignUpPage_companion.this, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                                checkEmailDuplicate = true;
                            }
                        } else {
                            // 오류 발생
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                // Firebase 사용자 충돌 오류 (이메일이 이미 사용 중인 경우)
                                Toast.makeText(SignUpPage_companion.this, "사용중인 이메일입니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                // 기타 오류
                                Toast.makeText(SignUpPage_companion.this, "이메일 중복 확인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
