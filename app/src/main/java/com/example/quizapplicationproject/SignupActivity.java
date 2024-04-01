package com.example.quizapplicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupActivity extends AppCompatActivity {
    private EditText mName, mEmail, mPassword, mRePassword, mAvatar;
    private Button mSignUpButton;
    private RadioGroup mAvatarGroup;
    private RadioButton mSloth, mElephant, mPig, mFox;
    private FirebaseAuth fAuth;
    private int avSelected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mName = findViewById(R.id.setupNameInput);
        mEmail = findViewById(R.id.setupEmailInput);
        mPassword = findViewById(R.id.setupPasswordInput);
        mRePassword = findViewById(R.id.setupReenterInput);
        mSignUpButton = findViewById(R.id.setupSignUpButton);
        mSloth = findViewById(R.id.slothAv);
        mElephant = findViewById(R.id.elephantAv);
        mPig = findViewById(R.id.pigAv);
        mFox = findViewById(R.id.foxAv);
        mAvatarGroup = findViewById(R.id.radioGroup);

        fAuth = FirebaseAuth.getInstance();

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

                if (rPassword.length() < 6) {
                    mRePassword.setError("Password must be greater than 6 characters");
                    return;
                }

                if(!password .equals(rPassword)){
                    mRePassword.setError("Passwords do not match");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "User profile created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(SignupActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
                // Save user selection of avatar picked
                private void avatarSelection (int checkedId){

                    if (checkedId == R.id.slothAv) {
                        avSelected = R.drawable.sloth;
                    } else if (checkedId == R.id.pigAv) {
                        avSelected = R.drawable.pig;
                    } else if (checkedId == R.id.elephantAv) {
                        avSelected = R.drawable.elephant;
                    } else if (checkedId == R.id.foxAv) {
                        avSelected = R.drawable.fox;
                    }
                }
        }

