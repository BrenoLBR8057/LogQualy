package com.example.logqualy.ui.recyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logqualy.R;
import com.example.logqualy.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Products> productsList;
    private Context context;
    private ProductOnItemClick onItemClickListener;

    public ProductAdapter(List<Products> productsList, Context context){
        this.context = context;
        this.productsList = productsList;
    }

    public void setOnItemClickListener(ProductOnItemClick onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Products products = productsList.get(position);

        holder.link(products);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void removeItemCarro(int position) {
        Products products = productsList.get(position);
        db.collection("COLLECTION").document(products.getId()).delete();
        notifyItemRemoved(position);
    }

    public void changeLocate(int posicaoInicial, int posicaoFinal) {
        Collections.swap(productsList, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textViewProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProduct = itemView.findViewById(R.id.textViewproduct);
            imageView = itemView.findViewById(R.id.imageView3);
        }

        public void link(Products products){
            textViewProduct.setText(products.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicao = getAdapterPosition();
                    onItemClickListener.itemClick(productsList.get(getAdapterPosition()), posicao);
                }
            });
        }
    }
}