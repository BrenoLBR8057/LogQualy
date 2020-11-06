package com.example.logqualy.ui.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logqualy.R;
import com.example.logqualy.model.Products;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
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