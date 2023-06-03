package com.gachon.caregiver.userInform.MainPage;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class mapcomp_info extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private String name;
    private String age;
    private String significant;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapcompanion_info);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        EditText text_name = findViewById(R.id.name);
        EditText text_age = findViewById(R.id.age);
        EditText text_significant = findViewById(R.id.significant);

        Intent getUID = getIntent();
        String companionUID = getUID.getStringExtra("id");

        getUserInfo(companionUID);

        //데베에서 가져온 유저의 정보를 저장하는 부분 창에는 데베에서 가져온 정보가 뜰 것이다.

        Button application = findViewById(R.id.sinchung);

        application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_go = new Intent(getApplicationContext(), sinchung.class);
                intent_go.putExtra("id", companionUID);
                startActivity(intent_go);
            }
        });
    }

    //리얼타임 데베가서 동행자의 userinfo를 가져와서 저장시키는 부분
    public void getUserInfo(String companionUID){
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = databaseReference.child("UID");
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Iterate over each child node under "locations"
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        if (!userId.equals(companionUID)) {
                            name = userSnapshot.child("name").getValue(String.class);
                            age = userSnapshot.child("age").getValue(String.class);
                            significant =  userSnapshot.child("significant").getValue(String.class);

                            EditText text_name = findViewById(R.id.name);
                            EditText text_age = findViewById(R.id.age);
                            EditText text_significant = findViewById(R.id.significant);

                            text_name.setText(name);
                            text_age.setText(age);
                            text_significant.setText(significant);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(mapcomp_info.this, "정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(mapcomp_info.this, "정보를 가져오는 것을 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
