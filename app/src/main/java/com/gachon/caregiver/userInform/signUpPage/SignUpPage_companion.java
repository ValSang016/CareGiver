package com.gachon.caregiver.userInform.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.gachon.caregiver.connect_server_data;
import com.gachon.caregiver.userInform.loginPage.LoginPage;

public class SignUpPage_companion extends AppCompatActivity {

    private connect_server_data connectServerData;
    String name;
    String birth;
    String gender;
    String sign_up_id;
    String sign_up_pw;
    String phone_number;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_companion);


        Button next_bt = findViewById(R.id.go_to_login); //누름과 동시에 회원가입이 승인 되고 이후 다시 로그인 창으로 넘어가서 로그인을 할 수 있게 해준다.
        Button check_id_bt = findViewById(R.id.check_id);//아이디 중복 체크 버튼

        EditText sign_up_name = findViewById(R.id.name);
        EditText sign_up_birth = findViewById(R.id.birth);
        RadioGroup check_gender_group = findViewById(R.id.ckkmale);
        RadioButton sign_up_gender_f = findViewById(R.id.female);
        RadioButton sign_up_gender_m = findViewById(R.id.male);
        EditText make_up_id = findViewById(R.id.id_write);
        EditText make_up_pw = findViewById(R.id.password_write);
        EditText sign_up_phone_number = findViewById(R.id.phonenumber_write);

        connectServerData = new connect_server_data();

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

                connectServerData.sign_up_companion_connect(name,birth,gender,sign_up_id,sign_up_pw,phone_number);

                Intent login = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(login);

            }
        });
    }
}

