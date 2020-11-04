package com.example.logqualy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logqualy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateNewUser extends AppCompatActivity {
    private static final String TAG = "CreateNewUser";
    private EditText createLogin;
    private EditText createPassword;
    private Button btnCreate;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_user);

        mAuth = FirebaseAuth.getInstance();
        loadFields();
        onClick();
    }

    private void onClick() {
        btnCreate = findViewById(R.id.btnCreateAccount);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createLogin.getText().toString().isEmpty()){
                    createLogin.setError("Campo vazio");
                    return;
                }else if(createPassword.getText().toString().isEmpty()){
                    createPassword.setError("Campo vazio");
                    return;
                }
                String email = createLogin.getText().toString();
                String password = createPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(CreateNewUser.this, "Falha ao autenticar",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent intent = new Intent(this, ProductList.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT);
        }
    }

    private void loadFields() {
        createLogin = findViewById(R.id.editTextCreateLogin);
        createPassword = findViewById(R.id.editTextCreatePassword);
    }


}