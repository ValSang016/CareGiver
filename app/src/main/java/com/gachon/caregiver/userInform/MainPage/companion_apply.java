package com.gachon.caregiver.userInform.MainPage;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.gachon.caregiver.userInform.signUpPage.SignUpPage_companion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class companion_apply extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;

    Uri uri;
    String imageUri;
    ImageView selectedImage;

    String curUserEmail;
    String curUID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companion_apply);

        mAuth = FirebaseAuth.getInstance(); // Firebase 인증 객체 초기화
        mStorage = FirebaseStorage.getInstance().getReference(); // Firebase 스토리지 객체 초기화

        FirebaseUser curUser = mAuth.getCurrentUser();
        curUserEmail = curUser.getEmail();
        curUID = curUser.getUid();


        Button selectImageBtn = findViewById(R.id.btn_UploadPicture);
        Button certificate_send = findViewById(R.id.certificate_send);
        selectedImage = findViewById(R.id.user_image);
        TextView information = findViewById(R.id.significant);


        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bringImg = new Intent(Intent.ACTION_PICK);
                bringImg.setType("image/*");
                startActivityResult.launch(bringImg);
            }
        });

        certificate_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inform = information.getText().toString();
                uploadImageNuploadRecord(inform, curUserEmail);
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
                            selectedImage.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
    private void uploadImageNuploadRecord(String inform, String email){
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        if(uri != null){
            StorageReference imageRef = mStorage.child("certification_images").child(email + ".jpg");
            imageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUri = uri.toString();
                            Map<String, Object> data = new HashMap<>();
                            data.put("email", email);
                            data.put("information", inform);
                            data.put("imageUri", imageUri);
                            data.put("approval", "0");
                            mDatabase.child("certificationList").child(curUID).setValue(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "신청이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

                                            Intent send_apply = new Intent(companion_apply.this, companion_certificate_btn_clicked.class);
                                            startActivity(send_apply);
                                       }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(companion_apply.this, "데이터베이스 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(companion_apply.this, "이미지 url 다운로드를 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(companion_apply.this, "이미지 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(companion_apply.this, "이미지를 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}


