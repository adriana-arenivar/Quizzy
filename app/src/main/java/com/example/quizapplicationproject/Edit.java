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

public class Edit extends AppCompatActivity {
    EditText editTerm, editDefinition;
    Button editSaveButton, editDeleteButton;
    String setId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcards);

        editTerm = findViewById(R.id.editTerm);
        editDefinition = findViewById(R.id.editDefinition);
        editSaveButton = findViewById(R.id.editSaveButton);
        editDeleteButton = findViewById(R.id.editDelete);

        // Assume setId is passed as an extra in the intent
        setId = getIntent().getStringExtra("SET_ID");

        // Assuming fetchSetData populates the edit fields with the set's current data
        fetchSetData(setId);

        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSetChanges();
            }
        });

        editDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSet();
            }
        });
    }
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private void fetchSetData(String setId) {
        db.collection("users").document(currentUser.getUid()).collection("flashcardSets").document(setId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        editTerm.setText(documentSnapshot.getString("term"));
                        editDefinition.setText(documentSnapshot.getString("definition"));
                    } else {
                        Toast.makeText(Edit.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(Edit.this, "Error fetching data", Toast.LENGTH_SHORT).show());
    }


    private void saveSetChanges() {
        String newTerm = editTerm.getText().toString().trim();
        String newDefinition = editDefinition.getText().toString().trim();

        // Perform validation if necessary

        // Update the set in your database
        DocumentReference setRef = db.collection("your_collection").document(setId);
        setRef.update("term", newTerm, "definition", newDefinition)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Edit.this, "Set updated successfully.", Toast.LENGTH_SHORT).show();
                        // Optionally, redirect back to the previous Activity
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Edit.this, "Error updating set.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteSet() {
        // Delete the set from your database
        DocumentReference setRef = db.collection("your_collection").document(setId);
        setRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Edit.this, "Set deleted successfully.", Toast.LENGTH_SHORT).show();
                        // Optionally, redirect back to the previous Activity
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Edit.this, "Error deleting set.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
