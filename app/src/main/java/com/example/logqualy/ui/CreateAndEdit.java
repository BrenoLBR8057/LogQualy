package com.example.logqualy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.logqualy.R;
import com.example.logqualy.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.logqualy.ui.Constants.KEY_EDIT_PRODUCT;
import static com.example.logqualy.ui.Constants.KEY_NEW_PRODUCT;

public class CreateAndEdit extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button btnSave;
    private EditText title;
    private EditText description;
    private EditText date;
    private ImageView photo;
    private boolean isEditForm;
    private Products products;
    private String TAG = "Succes";
    private String PRODUCTS_COLLECTION = "COLLECTION";
    private List<Products> productsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_edit);

        loadFields();
        Intent intent = getIntent();
        if(intent.hasExtra(KEY_EDIT_PRODUCT)){
            isEditForm = true;
            products = (Products) intent.getSerializableExtra(KEY_EDIT_PRODUCT);
            title.setText(products.getTitle());
            description.setText(products.getDescription());
            date.setText(products.getDate());
        }

        buttonClick();
    }

    private void buttonClick() {
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditForm){
                    updateProduct();
                    Intent intent = new Intent(CreateAndEdit.this, ProductList.class);
                    intent.putExtra(KEY_EDIT_PRODUCT, products);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }else{
                    products = getProductFromForm();
                    Intent intent = new Intent(CreateAndEdit.this, ProductList.class);
                    intent.putExtra(KEY_NEW_PRODUCT, products);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void updateProduct() {
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        String date = this.date.getText().toString();

        products.setTitle(title);
        products.setDescription(description);
        products.setDate(date);
    }

    private void loadFields() {
        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextDescription);
        date = findViewById(R.id.editTextDate);
        photo = findViewById(R.id.imageViewPhoto);
    }

    private Products getProductFromForm(){
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        String date = this.date.getText().toString();

        Map<String, Object> product = new HashMap<>();
        product.put("Tittle", title);
        product.put("Description", description);
        product.put("Date", date);

        db.collection(PRODUCTS_COLLECTION)
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        return new Products(title, description, date);
    }
}