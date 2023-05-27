package com.gachon.caregiver.userInform.MainPage;

import android.os.Bundle;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_companion);

        firestore = FirebaseFirestore.getInstance();

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
                contextEditText.setText("");
                checkDay(year, month, dayOfMonth);
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(readDay);
                str = contextEditText.getText().toString();
                textView2.setText(str);
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);

                Map<String, Object> data = new HashMap<>();
                data.put("diary", str);

                firestore.collection("diaries").document(readDay)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CalendarCompanionActivity.this, "일정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
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
                removeDiary(readDay);
            }
        });
    }

    public void checkDay(int cYear, int cMonth, int cDay) {
        readDay = "" + cYear + "-" + (cMonth + 1) + "-" + cDay + ".txt";

        str = "";
        textView2.setVisibility(View.INVISIBLE);

        firestore.collection("diaries").document(readDay)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String diary = document.getString("diary");
                                str = diary;
                                textView2.setText(diary);
                            } else {
                                str = "";
                                textView2.setText("");
                            }
                        } else {
                            str = "";
                            textView2.setText("");
                        }

                        if (str.isEmpty()) {
                            textView2.setVisibility(View.INVISIBLE);
                            diaryTextView.setVisibility(View.VISIBLE);
                            save_Btn.setVisibility(View.VISIBLE);
                            cha_Btn.setVisibility(View.INVISIBLE);
                            del_Btn.setVisibility(View.INVISIBLE);
                            contextEditText.setVisibility(View.VISIBLE);
                        } else {
                            textView2.setVisibility(View.VISIBLE);
                            diaryTextView.setVisibility(View.INVISIBLE);
                            save_Btn.setVisibility(View.INVISIBLE);
                            cha_Btn.setVisibility(View.VISIBLE);
                            del_Btn.setVisibility(View.VISIBLE);
                            contextEditText.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    public void removeDiary(String readDay) {
        Map<String, Object> data = new HashMap<>();
        data.put("diary", "");

        firestore.collection("diaries").document(readDay)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CalendarCompanionActivity.this, "일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void saveDiary(String readDay) {
        String content = contextEditText.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("diary", content);

        firestore.collection("diaries").document(readDay)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CalendarCompanionActivity.this, "일정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
