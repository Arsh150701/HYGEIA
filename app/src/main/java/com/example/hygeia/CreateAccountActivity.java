package com.example.hygeia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccount";
    private FirebaseAuth mAuth;
    EditText fieldName;
    EditText fieldEmail;
    EditText fieldEmailconfirm;
    Button verify, emailRegister;
    Button go;
    TextInputLayout namebox, emailbox, confemailbox;

    FirebaseUser user;
    String Number;
    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
        fieldName = findViewById(R.id.fieldName);
        fieldEmail = findViewById(R.id.fieldEmail);
        fieldEmailconfirm = findViewById(R.id.fieldEmailconfirm);
        verify = findViewById(R.id.button_verify);
        emailRegister = findViewById(R.id.button_register_email);
        go = findViewById(R.id.button_signin);
        Number = getIntent().getStringExtra("Number");
        namebox = findViewById(R.id.namebox);
        emailbox = findViewById(R.id.emailbox);
        confemailbox = findViewById(R.id.confirmemailbox);

        setWatcher(namebox, fieldName);
        setWatcher(confemailbox, fieldEmailconfirm);
        setWatcher(emailbox, fieldEmail);

        go.setText(getIntent().getStringExtra("Title"));

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClickVerify: ");
                if (fieldEmail.getText().toString().length() == 0 || fieldEmail.getText() == null)
                    emailbox.setError("Enter an email to verify");
                if (!(fieldEmail.getText().toString().equals(fieldEmailconfirm.getText().toString()))) {
                    confemailbox.setError("Email Mismatch");
                    fieldEmailconfirm.requestFocus();
                } else {
                    if (!(fieldEmail.getText().toString().isEmpty())) {
                        Log.d(TAG, "sendEmailVerify() ");
                        sendemailverify();
                    }
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "signIn");

                if (fieldName.getText().toString().isEmpty()) {
                    namebox.setError("Provide some name");
                    fieldName.requestFocus();
                } else {
                    Log.d(TAG, "signIn inside else ");
                    saveDetails();
                    Toast.makeText(CreateAccountActivity.this, "Account created sucessfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                }
            }
        });

        emailRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "emailRegister ");

                FirebaseAuth.getInstance().signInWithEmailAndPassword(fieldEmail.getText().toString(), "12345_admin")
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                                        Log.d(TAG, "Email verified sucess");
                                        Toast.makeText(CreateAccountActivity.this, "Email verified", Toast.LENGTH_SHORT).show();
                                        saveEmail();
                                    } else {
                                        Log.d(TAG, "Email verified failed");
                                        Toast.makeText(CreateAccountActivity.this, "Please verify email", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });
            }
        });
    }

    public void setWatcher(TextInputLayout layout, EditText editText) {
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

    private void saveDetails() {
        Log.d(TAG, "saveDeatils");

        HashMap<String, Object> map = new HashMap<>();

        map.put("Name", fieldName.getText().toString());
        map.put("number", Number);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(map, SetOptions.merge());

    }

    private void saveEmail() {
        Log.d(TAG, "saveEmail ");

        HashMap<String, Object> map = new HashMap<>();
        map.put("Email", fieldEmail.getText().toString());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(map, SetOptions.merge());
        popupwindow();

    }

    private void sendemailverify() {
        Log.d(TAG, "inside method sendemailverify");

        AuthCredential credential = EmailAuthProvider.getCredential(fieldEmail.getText().toString(), "12345_admin");

        if (getIntent().getStringExtra("Title").equals("Edit and save Email")) {
            String provider = FirebaseAuth.getInstance().getCurrentUser().getProviderId();
            FirebaseAuth.getInstance().getCurrentUser().unlink(provider)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                setEmail(credential);
                            }
                        }
                    });
        } else
            setEmail(credential);
    }

    private void popupwindow() {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(CreateAccountActivity.this);
        builder.setTitle("Email Verification");
        builder.setMessage("Your Email address has been verified");
        builder.setIcon(R.drawable.email);
        builder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg, null));
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public void setEmail(AuthCredential credential) {
        FirebaseAuth.getInstance().getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task1) {
                        if (task1.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                            task1.getResult().getUser().sendEmailVerification();
                            emailRegister.setVisibility(View.VISIBLE);
                            verify.setVisibility(View.INVISIBLE);
                            setUser(task1.getResult().getUser());
                        } else {
                            Log.w(TAG, "linkWithCredential:failure", task1.getException());
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}