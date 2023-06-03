package com.gachon.caregiver.userInform.MainPage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sinchung extends AppCompatActivity {

    private String name;
    private String age;
    private String significant;

    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sinchung);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Button sinchung_bt = findViewById(R.id.click);
        EditText getname = findViewById(R.id.name);
        EditText getage = findViewById(R.id.age);
        EditText getsignificant = findViewById(R.id.significant);

        sinchung_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = getname.getText().toString();
                age = getage.getText().toString();
                significant = getsignificant.getText().toString();

                Map<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("age", age);
                data.put("significant", significant);
                data.put("mapping",0);

                final FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    databaseReference.child("sinchung").child(user.getUid()).setValue(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(sinchung.this, "신청 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(sinchung.this, "신청 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
