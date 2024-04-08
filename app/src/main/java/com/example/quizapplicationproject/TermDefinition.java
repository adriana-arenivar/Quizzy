package com.example.quizapplicationproject;

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

public class TermDefinition extends AppCompatActivity {
    EditText termInput, definitionInput;
    Button submitButton, doneButton;
    FirebaseFirestore db;
    String flashcardSetId; // Assuming this is passed to the activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);

        termInput = findViewById(R.id.flashcardTermInput);
        definitionInput = findViewById(R.id.flashcardDefinitionInput);
        submitButton = findViewById(R.id.flashSubmitButton);
        doneButton = findViewById(R.id.flashDoneButton);
        db = FirebaseFirestore.getInstance();

        flashcardSetId = getIntent().getStringExtra("FLASHCARD_SET_ID"); // Retrieve the set ID

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String term = termInput.getText().toString().trim();
                String definition = definitionInput.getText().toString().trim();
                if (!term.isEmpty() && !definition.isEmpty() && flashcardSetId != null && !flashcardSetId.isEmpty()) {
                    addCardToSet(term, definition);
                } else {
                    Toast.makeText(TermDefinition.this, "Please enter both term and definition and ensure set ID is valid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back or to MainActivity depending on your flow
                finish(); // Consider using this if you just want to return to the previous activity
                // startActivity(new Intent(getApplicationContext(), MainActivity.class)); // Use this if you specifically want to navigate to MainActivity
            }
        });
    }

    private void addCardToSet(String term, String definition) {
        FlashcardSet.Flashcard card = new FlashcardSet.Flashcard(term, definition); // Ensure Flashcard has a no-arg constructor and getters

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && flashcardSetId != null && !flashcardSetId.isEmpty()) {
            db.collection("users")
                    .document(currentUser.getUid())
                    .collection("flashcardSets")
                    .document(flashcardSetId)
                    .collection("cards")
                    .add(card)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(TermDefinition.this, "Card added successfully", Toast.LENGTH_SHORT).show();
                            termInput.setText("");
                            definitionInput.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TermDefinition.this, "Error adding card", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not authenticated or Set ID is invalid", Toast.LENGTH_SHORT).show();
        }
    }
}
