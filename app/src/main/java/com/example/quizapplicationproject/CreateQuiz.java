package com.example.quizapplicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
public class CreateQuiz extends AppCompatActivity {
    Button cNext;
    EditText cTitle, cSubject;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        cNext = findViewById(R.id.createNextButton);
        cTitle = findViewById(R.id.createTitleInput);
        cSubject = findViewById(R.id.createSubjectInput);
        db = FirebaseFirestore.getInstance();

        cNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setName = cTitle.getText().toString().trim();
                if (!setName.isEmpty()) {
                    createFlashcardSet(setName);
                } else {
                    Toast.makeText(CreateQuiz.this, "Please enter set name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void createFlashcardSet(String setName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            FlashcardSet set = new FlashcardSet(setName, new ArrayList<>());

            db.collection("users")
                    .document(currentUser.getUid())
                    .collection("flashcardSets")
                    .add(set)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(CreateQuiz.this, "Set created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), TermDefinition.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateQuiz.this, "Error creating set", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
