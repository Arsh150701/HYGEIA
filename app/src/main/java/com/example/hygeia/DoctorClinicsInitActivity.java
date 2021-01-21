package com.example.hygeia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class DoctorClinicsInitActivity extends AppCompatActivity {

    private static final String TAG = "DoctorClinicsInitActivi";

    private CheckBox mon, tue, wed, thur, fri, sat, sun;
    private TextInputLayout doclinicbox, monbox, tuebox, wedbox, thurbox, fribox, satbox, sunbox,monbox1, tuebox1, wedbox1, thurbox1, fribox1, satbox1, sunbox1;
    private TextInputEditText doclinic, montext, tuetext, wedtext, thurtext, fritext, sattext, suntext,montext1, tuetext1, wedtext1, thurtext1, fritext1, sattext1, suntext1;
    private Button addclinic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_clinics_init);

        doclinicbox=findViewById(R.id.docclinicbox);
        doclinic=findViewById(R.id.docclinic);
        addclinic=findViewById(R.id.addclinic);


        mon=findViewById(R.id.checkmon);
        montext=findViewById(R.id.sttimemon);
        monbox=findViewById(R.id.sttimeboxmon);
        montext1=findViewById(R.id.ettimemon);
        monbox1=findViewById(R.id.ettimeboxmon);

        tue=findViewById(R.id.checktue);
        tuetext=findViewById(R.id.sttimetue);
        tuebox=findViewById(R.id.sttimeboxtue);
        tuetext1=findViewById(R.id.ettimetue);
        tuebox1=findViewById(R.id.ettimeboxtue);

        wed=findViewById(R.id.checkwed);
        wedtext=findViewById(R.id.sttimewed);
        wedbox=findViewById(R.id.sttimeboxwed);
        wedtext1=findViewById(R.id.ettimewed);
        wedbox1=findViewById(R.id.ettimeboxwed);

        thur=findViewById(R.id.checkthur);
        thurtext=findViewById(R.id.sttimethur);
        thurbox=findViewById(R.id.sttimeboxthur);
        thurtext1=findViewById(R.id.ettimethur);
        thurbox1=findViewById(R.id.ettimeboxthur);

        fri=findViewById(R.id.checkfri);
        fritext=findViewById(R.id.sttimefri);
        fribox=findViewById(R.id.sttimeboxfri);
        fritext1=findViewById(R.id.ettimefri);
        fribox1=findViewById(R.id.ettimeboxfri);

        sat=findViewById(R.id.checksat);
        sattext=findViewById(R.id.sttimesat);
        satbox=findViewById(R.id.sttimeboxsat);
        sattext1=findViewById(R.id.ettimesat);
        satbox1=findViewById(R.id.ettimeboxsat);

        sun=findViewById(R.id.checksun);
        suntext=findViewById(R.id.sttimesun);
        sunbox=findViewById(R.id.sttimeboxsun);
        suntext1=findViewById(R.id.ettimesun);
        sunbox1=findViewById(R.id.ettimeboxsun);


        setWatcher(doclinicbox, doclinic);

        mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    monbox.setVisibility(View.VISIBLE);
                    monbox1.setVisibility(View.VISIBLE);
                }else {
                    monbox.setVisibility(View.GONE);
                    monbox1.setVisibility(View.GONE);
                }
            }
        });
        tue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tuebox.setVisibility(View.VISIBLE);
                    tuebox1.setVisibility(View.VISIBLE);
                }else {
                    tuebox.setVisibility(View.GONE);
                    tuebox1.setVisibility(View.GONE);
                }
            }
        });
        wed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    wedbox.setVisibility(View.VISIBLE);
                    wedbox1.setVisibility(View.VISIBLE);
                }else {
                    wedbox.setVisibility(View.GONE);
                    wedbox1.setVisibility(View.GONE);
                }
            }
        });
        thur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    thurbox.setVisibility(View.VISIBLE);
                    thurbox1.setVisibility(View.VISIBLE);
                }else {
                    thurbox.setVisibility(View.GONE);
                    thurbox1.setVisibility(View.GONE);
                }
            }
        });
        fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fribox.setVisibility(View.VISIBLE);
                    fribox1.setVisibility(View.VISIBLE);
                }else {
                    fribox.setVisibility(View.GONE);
                    fribox1.setVisibility(View.GONE);
                }
            }
        });
        sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    satbox.setVisibility(View.VISIBLE);
                    satbox1.setVisibility(View.VISIBLE);
                }else {
                    satbox.setVisibility(View.GONE);
                    satbox1.setVisibility(View.GONE);
                }
            }
        });
        sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sunbox.setVisibility(View.VISIBLE);
                    sunbox1.setVisibility(View.VISIBLE);
                }else {
                    sunbox.setVisibility(View.GONE);
                    sunbox1.setVisibility(View.GONE);
                }
            }
        });

        addclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mon.isChecked())
                    saveDetails(montext, montext1, mon);
                if(tue.isChecked())
                    saveDetails(tuetext, tuetext1, tue);
                if(wed.isChecked())
                    saveDetails(wedtext, wedtext1, wed);
                if(thur.isChecked())
                    saveDetails(thurtext, thurtext1, thur);
                if(fri.isChecked())
                    saveDetails(fritext, fritext1, fri);
                if(sat.isChecked())
                    saveDetails(sattext, sattext1, sat);
                if(sun.isChecked())
                    saveDetails(suntext, suntext1, sun);
                Log.d(TAG, "onClick: buttonclick");
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

    private void saveDetails(TextInputEditText textInputEditText, TextInputEditText textInputEditText1, CheckBox checkBox){

        Log.d(TAG, "saveDetails: inside save details");
        HashMap<String, Object> map = new HashMap<>();

        map.put("startTime", textInputEditText.getText().toString());
        map.put("endTime", textInputEditText1.getText().toString());

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("doctors")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection(doclinic.getText().toString())
                .document(checkBox.getText().toString())
                .set(map, SetOptions.merge());
    }
}