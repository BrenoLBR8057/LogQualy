package com.example.logqualy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.logqualy.R;

public class CreateAndEdit extends AppCompatActivity {
    private Button btnSave;
    private EditText title;
    private EditText description;
    private EditText date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_edit);

        loadFields();
    }

    private void loadFields() {
        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextDescription);
        date = findViewById(R.id.editTextDate);
    }
}