package com.example.logqualy.ui.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logqualy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btnLogin;
    private EditText login;
    private EditText accountPassword;
    private TextView forgotPassword;
    private TextView createLogin;
    private static final String TAG = "LoginUser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFields();
        mAuth = FirebaseAuth.getInstance();
        onClick();
    }

    private void loadFields() {
        login = findViewById(R.id.editTextLogin);
        accountPassword = findViewById(R.id.editTextPassword);
        forgotPassword = findViewById(R.id.textViewForgotPassword);
        createLogin = findViewById(R.id.textViewCreateNewLogin);
    }

    private void onClick() {
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().toString().isEmpty()){
                    login.setError("Campo vazio");
                    return;
                }else if(accountPassword.getText().toString().isEmpty()){
                    accountPassword.setError("Campo vazio");
                    return;
                }
                String email = login.getText().toString();
                String password = accountPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Falha ao autenticar",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });

        createLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewUser.class);
                startActivity(intent);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        login.setText("");
        accountPassword.setText("");
        if(user != null){
            Intent intent = new Intent(this, ProductList.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT);
        }
    }
}