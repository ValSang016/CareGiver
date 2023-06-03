package com.gachon.caregiver.userInform.MainPage;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.caregiver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CalendarCompanionActivity extends AppCompatActivity {
    public String readDay = null;
    public String str = null;
    public CalendarView calendarView;
    public Button cha_Btn, del_Btn, save_Btn;
    public TextView diaryTextView, textView2, textView3;
    public EditText contextEditText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private int store_year;
    private int store_month;
    private int store_day;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_companion);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        calendarView = findViewById(R.id.calendarView);
        diaryTextView = findViewById(R.id.diaryTextView);
        save_Btn = findViewById(R.id.save_Btn);
        del_Btn = findViewById(R.id.del_Btn);
        cha_Btn = findViewById(R.id.cha_Btn);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        contextEditText = findViewById(R.id.contextEditText);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                store_day = dayOfMonth;
                store_month = month;
                store_year = year;
                contextEditText.setText("");
                checkDay(year, month, dayOfMonth);
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str = contextEditText.getText().toString();
                textView2.setText(str);
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);

                String context = str;

                Map<String, Object> data = new HashMap<>();
                data.put("context", context);
                data.put("day", store_day);
                data.put("month", store_month+1);
                data.put("year", store_year);


                final FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    databaseReference.child("calenders").child(user.getUid()).setValue(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CalendarCompanionActivity.this, "달력이 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CalendarCompanionActivity.this, "달력 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

        cha_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                contextEditText.setText(str);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                textView2.setText(contextEditText.getText());
            }
        });

        del_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setVisibility(View.INVISIBLE);
                contextEditText.setText("");
                contextEditText.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);

                String context = "";

                Map<String, Object> data = new HashMap<>();
                data.put("context", context);
                data.put("day", "");
                data.put("month", "");
                data.put("year", "");

                final FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    databaseReference.child("calenders").child(user.getUid()).setValue(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CalendarCompanionActivity.this, "달력이 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CalendarCompanionActivity.this, "달력 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    public void checkDay(int cYear, int cMonth, int cDay) {
        readDay = "" + cYear + "-" + (cMonth + 1) + "-" + cDay + ".txt";

        store_day = cDay;
        store_month = cMonth;
        store_year = cYear;

        str = "";
        textView2.setVisibility(View.INVISIBLE);
        final FirebaseUser user = mAuth.getCurrentUser();
        databaseReference.child("calenders").child(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            if (dataSnapshot.exists()) {
                                String diary = dataSnapshot.child("context").getValue(String.class);
                                str = diary;
                                textView2.setText(diary);

                                // 추가된 부분: 선택한 날짜에만 데이터를 띄우기
                                String selectedDate = String.format("%d-%d-%d", store_year, store_month + 1, store_day);
                                if (readDay.equals(selectedDate)) {
                                    textView2.setVisibility(View.VISIBLE);
                                    diaryTextView.setVisibility(View.INVISIBLE);
                                    save_Btn.setVisibility(View.INVISIBLE);
                                    cha_Btn.setVisibility(View.VISIBLE);
                                    del_Btn.setVisibility(View.VISIBLE);
                                    contextEditText.setVisibility(View.INVISIBLE);
                                } else {
                                    textView2.setVisibility(View.INVISIBLE);
                                    diaryTextView.setVisibility(View.VISIBLE);
                                    save_Btn.setVisibility(View.VISIBLE);
                                    cha_Btn.setVisibility(View.INVISIBLE);
                                    del_Btn.setVisibility(View.INVISIBLE);
                                    contextEditText.setVisibility(View.VISIBLE);
                                }
                            } else {
                                str = "";
                                textView2.setText("");

                                // 추가된 부분: 선택한 날짜에만 데이터를 띄우지 않음
                                textView2.setVisibility(View.INVISIBLE);
                                diaryTextView.setVisibility(View.VISIBLE);
                                save_Btn.setVisibility(View.VISIBLE);
                                cha_Btn.setVisibility(View.INVISIBLE);
                                del_Btn.setVisibility(View.INVISIBLE);
                                contextEditText.setVisibility(View.VISIBLE);
                            }
                        } else {
                            str = "";
                            textView2.setText("");

                            // 추가된 부분: 선택한 날짜에만 데이터를 띄우지 않음
                            textView2.setVisibility(View.INVISIBLE);
                            diaryTextView.setVisibility(View.VISIBLE);
                            save_Btn.setVisibility(View.VISIBLE);
                            cha_Btn.setVisibility(View.INVISIBLE);
                            del_Btn.setVisibility(View.INVISIBLE);
                            contextEditText.setVisibility(View.VISIBLE);
                        }

                        // 추가된 부분: 저장한 기록 계속 유지하기
                        contextEditText.setText(str);
                    }
                });
    }


}
