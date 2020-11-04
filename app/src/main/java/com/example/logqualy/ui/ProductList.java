package com.example.logqualy.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.logqualy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.logqualy.ui.Constants.KEY_NEW_PRODUCT;
import static com.example.logqualy.ui.Constants.REQUEST_CODE_NEW_PRODUCT;
import static com.example.logqualy.ui.Constants.RETURN_CODE_NEW_PRODUCT;

public class ProductList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fabProduct;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        //FirebaseAuth.getInstance().signOut();

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

            losdFields();
            onClick();
        }
    }

    private void onClick() {
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
        if(requestCode == REQUEST_CODE_NEW_PRODUCT && resultCode == RETURN_CODE_NEW_PRODUCT && data.hasExtra(KEY_NEW_PRODUCT)){

        }
    }

    private void losdFields() {
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
}