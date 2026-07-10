package com.example.to_do_app.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Toast;
import com.example.to_do_app.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


import com.example.to_do_app.R;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseFirestore firestore ;
    private FirebaseAuth auth ;

    private EditText etFirstName ;
    private EditText etLastName ;
    private EditText etEmail ;
    private EditText etPassword ;
    private EditText etConfirmPassword ;
    private Button btnSignUp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance() ;
        firestore = FirebaseFirestore.getInstance() ;

        etFirstName = findViewById(R.id.edtFirstName) ;
        etLastName = findViewById(R.id.edtLastName) ;
        etEmail = findViewById(R.id.edtEmail) ;
        etConfirmPassword = findViewById(R.id.edtConfirmPassword) ;
        btnSignUp = findViewById(R.id.btnSignUp) ;


        setupClickListeners();


    }
    private void setupClickListeners() {
     btnSignUp.setOnClickListener(v -> signUp());
    }

    private void signUp () {
        String firstName = etFirstName.getText().toString().trim() ;
        String lastName = etLastName.getText().toString().trim() ;
        String email = etEmail.getText().toString().trim() ;
        String password = etPassword.getText().toString().trim() ;
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if(firstName.isEmpty()){
            etFirstName.setError(" Entrer your first name");
            return ;
        }
        if(lastName.isEmpty()){
            etLastName.setError("Entrer your last name");
            return;
        }
        if (email.isEmpty()) {
            etEmail.setError("Enter your email");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Enter your password");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must contain at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        auth.createUserWithEmailAndPassword(email , password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        String uid = firebaseUser.getUid();

                        User user = new User(
                                uid ,
                                firstName ,
                                lastName ,
                                email
                        );

                        firestore.collection("users")
                                .document(uid)
                                .set(user)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(
                                            SignUpActivity.this ,
                                            "Account created successfully" ,
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    startActivity(new Intent(
                                            SignUpActivity.this ,
                                            MainActivity.class
                                    ));
                                    finish();
                                })

                                .addOnFailureListener(e ->
                                        Toast.makeText(
                                             SignUpActivity.this ,
                                                e.getMessage(),
                                                Toast.LENGTH_SHORT
                                        ).show());
                    }else {
                        Toast.makeText(
                                SignUpActivity.this,
                                task.getException().getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}