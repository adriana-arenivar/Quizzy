package com.example.quizapplicationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<FlashcardSet> list;

    public interface OnItemClickListener {
        void onItemClick(FlashcardSet flashcardSet);
    }

    private OnItemClickListener listener;

    public MyAdapter(Context context, ArrayList<FlashcardSet> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_display_sets, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FlashcardSet flashcardSet = list.get(position);
        holder.setName.setText(flashcardSet.getSetName());

        // Set the click listener
        holder.itemView.setOnClickListener(view -> listener.onItemClick(flashcardSet));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView setName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            setName = itemView.findViewById(R.id.displaySetName);
        }
    }
}
