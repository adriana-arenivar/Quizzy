package com.example.quizapplicationproject;

import static com.example.quizapplicationproject.Signup.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class SetDisplayForStudy extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore db;
    MyAdapter myAdapter;
    ArrayList<FlashcardSet> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        recyclerView = findViewById(R.id.recycle_view);
        db = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        // Instantiate MyAdapter with the click listener implemented
        myAdapter = new MyAdapter(this, list, flashcardSet -> {
            Intent intent = new Intent(SetDisplayForStudy.this, Edit.class);
            intent.putExtra("setName", flashcardSet.getSetName());
            startActivity(intent);
        });

        recyclerView.setAdapter(myAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid()).collection("flashcardSets")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String setName = document.getString("setName");
                                if (setName != null) {
                                    FlashcardSet flashcardSet = new FlashcardSet(setName);
                                    list.add(flashcardSet);
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            }
                            myAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });
        }
    }
}
