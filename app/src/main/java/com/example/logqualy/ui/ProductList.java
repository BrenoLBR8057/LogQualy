package com.example.logqualy.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.logqualy.R;
import com.example.logqualy.model.Products;
import com.example.logqualy.ui.recyclerview.ProductAdapter;
import com.example.logqualy.ui.recyclerview.ProductOnItemClick;
import com.example.logqualy.ui.recyclerview.helper.ProductTouthHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.logqualy.ui.Constants.KEY_EDIT_PRODUCT;
import static com.example.logqualy.ui.Constants.KEY_NEW_PRODUCT;
import static com.example.logqualy.ui.Constants.REQUEST_CODE_EDIT_PRODUCT;
import static com.example.logqualy.ui.Constants.REQUEST_CODE_NEW_PRODUCT;

public class ProductList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fabProduct;
    private String PRODUCTS_COLLECTION = "COLLECTION";
    private FirebaseAuth mAuth;
    private ProductAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "Succes";
    private List<Products> productsList;
    private int positionItemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        loadFields();
        productsList = new ArrayList<>();

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        loadData();
        configureRecycler();
        onClickBtnAdd();
    }

    private void configureRecycler() {
        recyclerView = findViewById(R.id.recyclerProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(productsList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ProductOnItemClick() {
            @Override
            public void itemClick(Products products, int position) {
                positionItemClick = position;
                Intent intent = new Intent(ProductList.this, CreateAndEdit.class);
                intent.putExtra(KEY_EDIT_PRODUCT, products);
                startActivityForResult(intent, REQUEST_CODE_EDIT_PRODUCT);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ProductTouthHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public void onClickBtnAdd() {
        fabProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductList.this, CreateAndEdit.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_PRODUCT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_NEW_PRODUCT && resultCode == RESULT_OK && data.hasExtra(KEY_NEW_PRODUCT)){
            Products products = (Products) data.getSerializableExtra(KEY_NEW_PRODUCT);
            db.collection(PRODUCTS_COLLECTION).add(products);
            loadData();
        }else if(requestCode == REQUEST_CODE_EDIT_PRODUCT && resultCode == RESULT_OK && data.hasExtra(KEY_EDIT_PRODUCT)){
            Products products = (Products)data.getSerializableExtra(KEY_EDIT_PRODUCT);
            productsList.set(positionItemClick, products);
            db.collection(PRODUCTS_COLLECTION).document(products.getId()).set(products);
            loadData();
        }
    }

    private void loadFields() {
        recyclerView = findViewById(R.id.recyclerProducts);
        fabProduct = findViewById(R.id.fabAddProduct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuLogOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProductList.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void loadData(){
        db.collection(PRODUCTS_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete (@NonNull Task < QuerySnapshot > task) {
                    if (task.isSuccessful()) {
                        productsList.clear();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            Products products = documentSnapshot.toObject(Products.class);
                            products.setId(documentSnapshot.getId());
                            productsList.add(products);
                            configureRecycler();
                        }
                    } else {
                        Log.d(TAG, "Erro ao pegar documentos", task.getException());
                    }
                }
        });

    }
}