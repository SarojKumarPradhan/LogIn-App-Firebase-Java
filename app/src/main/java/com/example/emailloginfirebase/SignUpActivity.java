package com.example.emailloginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText t1,t2;
    Button b1;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_up);
        t1 = findViewById(R.id.email);
        t2 = findViewById(R.id.pass);
        b1 = findViewById(R.id.register);
        b1.setOnClickListener(this);
        progressBar = findViewById(R.id.progress);
    }

    @Override
    public void onClick(View view)
    {
        String email = t1.getText().toString().trim();
        String pass = t2.getText().toString().trim();

        if (email.isEmpty())
        {
            t1.setError("Email Can't Blank");
            t1.requestFocus();
            return;
        }
        if (pass.isEmpty())
        {
            t2.setError("Password Can't Blank");
            t2.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            t1.setError("Plz Enter Valid Format Email");
            t1.requestFocus();
        }
        if (pass.length()<6)
        {
            t2.setError("Plz Enter Minimum Length Password Is 6");
            t2.requestFocus();
        }
        progressBar.setVisibility(View.VISIBLE);

        //************************SignUp Code***************************************
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        Log.d("MSG","createUserWithEmail:success");
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "User Register Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                            finish();
                            progressBar.setVisibility(View.GONE);
                        }
                        if (task.getException() instanceof FirebaseAuthUserCollisionException)
                        {
                            Toast.makeText(SignUpActivity.this, "User Already Registered", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        if (!task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}