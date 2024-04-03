package com.example.quizapplicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private EditText mName, mEmail, mPassword, mRePassword, mAvatar;
    Button mSignUpButton;
    RadioGroup mAvatarGroup;
    RadioButton mSloth, mElephant, mPig, mFox;
    FirebaseAuth fAuth;
    TextView mLogin;
    FirebaseFirestore fStore;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mName = findViewById(R.id.setupNameInput);
        mEmail = findViewById(R.id.setupEmailInput);
        mPassword = findViewById(R.id.setupPasswordInput);
        mRePassword = findViewById(R.id.setupReenterInput);
        mAvatarGroup = findViewById(R.id.radioGroup);
        mSignUpButton = findViewById(R.id.setupSignUpButton);
        mSloth = findViewById(R.id.slothAv);
        mElephant = findViewById(R.id.elephantAv);
        mPig = findViewById(R.id.pigAv);
        mFox = findViewById(R.id.foxAv);
        mLogin = findViewById(R.id.setupLogin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//        if (fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }

        mAvatarGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Directly call avatarSelection method here with the checkedId
                avatarSelection(checkedId);
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String rPassword = mRePassword.getText().toString().trim();
                String name = mName.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if (TextUtils.isEmpty(rPassword)) {
                    mRePassword.setError("Must reenter password");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must be greater than 6 characters");
                    return;
                }

                if (!password.equals(rPassword)) {
                    mRePassword.setError("Passwords do not match");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "User profile created", Toast.LENGTH_SHORT).show();
                        userid = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userid);
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("email", email);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: user profile created for " + userid);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.toString());
                            }
                        });
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(SignupActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    // Save user selection of avatar picked
    private void avatarSelection(int checkedId) {
        int avSelected = 0;
        if (checkedId == R.id.slothAv) {
            avSelected = R.drawable.sloth;
        } else if (checkedId == R.id.pigAv) {
            avSelected = R.drawable.pig;
        } else if (checkedId == R.id.elephantAv) {
            avSelected = R.drawable.elephant;
        } else if (checkedId == R.id.foxAv) {
            avSelected = R.drawable.fox;
        }

    };
}

