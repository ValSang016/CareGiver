package com.gachon.caregiver.userInform.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import org.w3c.dom.Text;

public class mappar_info extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private String name;
    private String age;
    private String significant;
    String parentsUID;
    TextView text_name;
    TextView text_age;
    TextView text_significant;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapparents_info);

        Button allow = findViewById(R.id.allow);
        Button deny = findViewById(R.id.deny);

        text_name = findViewById(R.id.name);
        text_age = findViewById(R.id.age);
        text_significant = findViewById(R.id.significant);

        mAuth = FirebaseAuth.getInstance(); //FirebaseAuth 인스턴스 초기화

        Intent getter = getIntent();
        parentsUID = getter.getStringExtra("id");

        getUserInfo();


        //수락 버튼을 눌렀을 경우의 버튼
        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("users/matching");
                    databaseReference.child(parentsUID).child("mapping").setValue(1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(mappar_info.this, "매칭되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(mappar_info.this, "전송이 실패되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
            }
        });

        //거절 버튼을 눌렀을 경우의 버튼
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("users/matching");
                databaseReference.child(parentsUID).child("mapping").setValue(2)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(mappar_info.this, "거부 되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(mappar_info.this, "전송이 실패되셨습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
        });
    }

    //리얼타임 데베가서 노인의 userinfo를 가져와서 저장시키는 부분
    public void getUserInfo(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users/matching");
        DatabaseReference userRef = database.child(parentsUID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    name = dataSnapshot.child("name").getValue(String.class);
                    age = dataSnapshot.child("age").getValue(String.class);
                    significant =  dataSnapshot.child("significant").getValue(String.class);

                    //데베에서 가져온 유저의 정보를 저장하는 부분 창에는 데베에서 가져온 정보가 뜰 것이다.
                    text_name.setText(name);
                    text_age.setText(age);
                    text_significant.setText(significant);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mappar_info.this, "정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
