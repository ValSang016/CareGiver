package com.gachon.caregiver.userInform.MainPage;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class companion_apply extends AppCompatActivity {

    Uri uri;
    String imageUri;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companion_apply);

        Button selectImageBtn = findViewById(R.id.btn_UploadPicture);

        Button certificate_send = findViewById(R.id.certificate_send);

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
                Intent send_apply = new Intent(getApplicationContext(), companion_certificate_btn_clicked.class);
                startActivity(send_apply);
                Toast.makeText(getApplicationContext(), "신청이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
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

}


