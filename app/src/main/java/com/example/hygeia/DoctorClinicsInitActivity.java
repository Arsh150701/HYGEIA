package com.example.hygeia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

//isko fragment banana hai
public class DoctorClinicsInitActivity extends AppCompatActivity {

    private static final String TAG = "DoctorClinicsInitActivi";

    private CheckBox mon, tue, wed, thur, fri, sat, sun;
    private Button addclinic, skip;
    private Button btnstmon, btnsttue, btnstwed, btnstthur, btnstfri, btnstsat, btnstsun;
    private Button btnetmon, btnettue, btnetwed, btnetthur, btnetfri, btnetsat, btnetsun;
    private TextInputLayout doclinicbox;
    private TextInputEditText doclinic;

    private List<String> array_title = new ArrayList<>();
    int t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_clinics_init);

        skip = findViewById(R.id.skipclinic);
        doclinicbox = findViewById(R.id.docclinicbox);
        doclinic = findViewById(R.id.docclinic);
        addclinic = findViewById(R.id.addclinic);


        mon = findViewById(R.id.checkmon);
        btnstmon = findViewById(R.id.sttimemon);
        btnetmon = findViewById(R.id.ettimemon);

        tue = findViewById(R.id.checktue);
        btnsttue = findViewById(R.id.sttimetue);
        btnettue = findViewById(R.id.ettimetue);

        wed = findViewById(R.id.checkwed);
        btnstwed = findViewById(R.id.sttimewed);
        btnetwed = findViewById(R.id.ettimewed);

        thur = findViewById(R.id.checkthur);
        btnstthur = findViewById(R.id.sttimethur);
        btnetthur = findViewById(R.id.ettimethur);

        fri = findViewById(R.id.checkfri);
        btnstfri = findViewById(R.id.sttimefri);
        btnetfri = findViewById(R.id.ettimefri);

        sat = findViewById(R.id.checksat);
        btnstsat = findViewById(R.id.sttimesat);
        btnetsat = findViewById(R.id.ettimesat);

        sun = findViewById(R.id.checksun);
        btnstsun = findViewById(R.id.sttimesun);
        btnetsun = findViewById(R.id.ettimesun);

        setWatcher(doclinicbox, doclinic);
        checkchange(mon, btnstmon, btnetmon);
        checkchange(tue, btnsttue, btnettue);
        checkchange(wed, btnstwed, btnetwed);
        checkchange(thur, btnstthur, btnetthur);
        checkchange(fri, btnstfri, btnetfri);
        checkchange(sat, btnstsat, btnetsat);
        checkchange(sun, btnstsun, btnetsun);

        timepicker(btnstmon);
        timepicker(btnetmon);
        timepicker(btnsttue);
        timepicker(btnettue);
        timepicker(btnstwed);
        timepicker(btnetwed);
        timepicker(btnstthur);
        timepicker(btnetthur);
        timepicker(btnstfri);
        timepicker(btnetfri);
        timepicker(btnstsat);
        timepicker(btnetsat);
        timepicker(btnstsun);
        timepicker(btnetsun);


        addclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mon.isChecked())
                    saveDetails(btnstmon, btnetmon, mon);
                if (tue.isChecked())
                    saveDetails(btnsttue, btnettue, tue);
                if (wed.isChecked())
                    saveDetails(btnstwed, btnetwed, wed);
                if (thur.isChecked())
                    saveDetails(btnstthur, btnetthur, thur);
                if (fri.isChecked())
                    saveDetails(btnstfri, btnetfri, fri);
                if (sat.isChecked())
                    saveDetails(btnstsat, btnetsat, sat);
                if (sun.isChecked())
                    saveDetails(btnstsun, btnetsun, sun);
                Log.d(TAG, "onClick: buttonclick");
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoctorMainActivity.class));
            }
        });
    }

    public void checkchange(CheckBox checkBox, Button btn1, Button btn2) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                } else {
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setWatcher(TextInputLayout layout, TextInputEditText editText) {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        editText.addTextChangedListener(watcher);
    }

    private void saveDetails(Button btn1, Button btn2, CheckBox checkBox) {
        Log.d(TAG, "saveDetails1: save");
        HashMap<String, Object> map = new HashMap<>();
        map.put("startTime", btn1.getText().toString());
        map.put("endTime", btn2.getText().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("doctors")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection(doclinic.getText().toString())
                .document(checkBox.getText().toString())
                .set(map, SetOptions.merge());

        map.clear();
        db.collection("doctors")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            assert doc != null;
                            String clinics = doc.getString("Visits");
                            try {
                                array_title = Arrays.asList(clinics.split(","));
                                int exist = 0;

                                for (int i = 0; i < array_title.size(); ++i) {
                                    String clinicName = array_title.get(i);
                                    if (clinicName.equals(doclinic.getText().toString())) {
                                        Log.d(TAG, "onComplete: name exists");
                                        Toast.makeText(DoctorClinicsInitActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
                                        exist = 1;
                                    }
                                }
                                if (exist == 0) {
                                    map.clear();
                                    Log.d(TAG, "onComplete: name doest not exist");
                                    map.put("Visits", clinics + "," + doclinic.getText().toString());
                                    db.collection("doctors")
                                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .set(map, SetOptions.merge());
                                }
                            } catch (Exception e) {
                                Log.d(TAG, "onComplete: " + e.getMessage());
                                Log.d(TAG, "onComplete: field does not exist");
                                map.clear();
                                map.put("Visits", doclinic.getText().toString());
                                db.collection("doctors")
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .set(map, SetOptions.merge());
                            }
                        }
                    }
                });
    }

    public void timepicker(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        DoctorClinicsInitActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                t1 = i;
                                t2 = i1;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, i, i1);
                                if (i1 == 0)
                                    btn.setText(new StringBuilder().append(i).append(":").append("00"));
                                else
                                    btn.setText(new StringBuilder().append(i).append(":").append(i1));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(t1, t2);
                timePickerDialog.show();
            }
        });
    }
}