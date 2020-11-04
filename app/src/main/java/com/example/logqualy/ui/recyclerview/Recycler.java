package com.example.logqualy.ui.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.logqualy.model.Products;

import java.util.List;

public class Recycler extends RecyclerView.Adapter<Recycler.ViewHolder> {
    private List<Products> productsList;

    @NonNull
    @Override
    public Recycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Recycler.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}