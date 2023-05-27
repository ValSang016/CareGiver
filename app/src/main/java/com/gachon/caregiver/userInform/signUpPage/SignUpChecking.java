package com.gachon.caregiver.userInform.signUpPage;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUpChecking {
    public boolean isEmailValid(String email) {
        // 이메일 주소의 유효성을 검사하기 위한 정규 표현식
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // 입력된 이메일 주소와 정규 표현식을 비교하여 일치 여부 확인
        return email.matches(emailPattern);
    }
    public boolean isPasswordConfirmed(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
    public boolean isUsernameAvailable(String username) {
        final boolean[] isAvailable = {true};
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query usernameQuery = usersRef.orderByChild("username").equalTo(username);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 아이디가 이미 존재하는 경우
                    isAvailable[0] = false;
                } else {
                    // 아이디가 존재하지 않는 경우
                    isAvailable[0] = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터베이스 쿼리 취소 시 발생하는 에러 처리
                Log.d("Firebase", "isUsernameAvailable:onCancelled", databaseError.toException());
            }
        };

        // 아이디 중복 확인을 위해 데이터베이스에서 쿼리 실행
        usernameQuery.addListenerForSingleValueEvent(valueEventListener);

        // 중복 확인 결과를 반환
        return isAvailable[0];
    }

}
