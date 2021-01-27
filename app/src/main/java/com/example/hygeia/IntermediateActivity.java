package com.example.hygeia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class IntermediateActivity extends AppCompatActivity {

    private static final String TAG = "IntermediateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Log.d(TAG, "onCreate: " + user.getPhoneNumber());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d(TAG, "onCreate: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.d(TAG, "onCreate: " + user);
        db.collection("doctors")
                .whereEqualTo("number", user.getPhoneNumber())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotsList = queryDocumentSnapshots.getDocuments();
                        if (!snapshotsList.isEmpty()) {
                            startActivity(new Intent(getApplicationContext(), DoctorMainActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                });
    }
}