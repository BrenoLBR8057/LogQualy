package com.example.logqualy.ui.recyclerview.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logqualy.ui.recyclerview.ProductAdapter;

public class ProductTouthHelper extends ItemTouchHelper.Callback {
    private ProductAdapter adapter;
    public ProductTouthHelper(ProductAdapter adapter){ this.adapter = adapter; }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int movimentoSwipe = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int movimentoDrag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(movimentoDrag, movimentoSwipe);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int inicialPosition = viewHolder.getAdapterPosition();
        int finalPosition = viewHolder.getAdapterPosition();
        adapter.changeLocate(inicialPosition, finalPosition);
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.removeItemCarro(viewHolder.getAdapterPosition());
    }
}
