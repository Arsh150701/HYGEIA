package com.example.hygeia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DocHome extends Fragment {

    private static final String TAG = "DocHome";

    private TextView someText, patientName, time;
    private LinearLayout recent;
    private LinearProgressIndicator progressIndicator;

    public DocHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doc_home, container, false);

        someText = v.findViewById(R.id.someText);
        recent = v.findViewById(R.id.docrecent);
        patientName = v.findViewById(R.id.recentPatient);
        time = v.findViewById(R.id.recentTime);
        progressIndicator = v.findViewById(R.id.progressbardoc);


        someText.setSelected(true);
        getRecent();

        return v;
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

                                            for(int i=0;i<snapshotsList.size();++i){
                                                Log.d(TAG, "onSuccess: "+snapshotsList.get(i).get("Time"));
                                            }

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