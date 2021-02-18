package com.example.hygeia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DoctorMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DoctorMainActivity";

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView someText, patientName, time;
    private LinearProgressIndicator progressIndicator;
    private LinearLayout recent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);

        toolbar = findViewById(R.id.doctoolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.docdrawerlayout);
        navigationView = findViewById(R.id.docnavigation);

        progressIndicator = findViewById(R.id.progressbardoc);
        someText = findViewById(R.id.someText);
        recent = findViewById(R.id.docrecent);
        patientName = findViewById(R.id.recentPatient);
        time = findViewById(R.id.recentTime);

        someText.setSelected(true);

        getDrawerStarted();
        getRecent();
    }

    private void getDrawerStarted() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        );

        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private void getRecent() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("doctors")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            String name = (document.getString("Name"));

                            db.collection("Appointment")
                                    .whereEqualTo("Doctor Name", name)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            Log.d(TAG, "onSuccessListner inside appt");
                                            List<DocumentSnapshot> snapshotsList = queryDocumentSnapshots.getDocuments();
                                            time.setText(snapshotsList.get(0).get("Time").toString());
                                            patientName.setText(snapshotsList.get(0).get("Patient Name").toString());
                                            someText.setVisibility(View.GONE);
                                            recent.setVisibility(View.VISIBLE);
                                            progressIndicator.setVisibility(View.GONE);
                                        }
                                    });

                        }
                    }
                });
    }
}