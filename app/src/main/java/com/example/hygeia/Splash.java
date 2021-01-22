package com.example.hygeia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Splash extends AppCompatActivity {
    private static final String TAG = "Splash";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
            startActivity(new Intent(this, Intro_Signin.class));
        else {
            /*FirebaseFirestore db = FirebaseFirestore.getInstance();
            Log.d(TAG, "onCreate: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
            Log.d(TAG, "onCreate: " + user);

            db.collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful())
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            else
                                startActivity(new Intent(getApplicationContext(), DoctorClinicsInitActivity.class));
                        }
                    });*/
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
