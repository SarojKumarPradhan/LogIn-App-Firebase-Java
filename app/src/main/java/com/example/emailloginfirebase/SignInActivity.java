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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener
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
        setContentView(R.layout.activity_sign_in);
        t1 = findViewById(R.id.email);
        t2 = findViewById(R.id.pass);
        b1 = findViewById(R.id.login);
        b1.setOnClickListener(this);
        progressBar = findViewById(R.id.progress);
    }

    @Override
    public void onClick(final View view)
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
            t2.setError("Pass Can't Blank");
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
        }
        progressBar.setVisibility(View.VISIBLE);
        //*************Sign in code ***************************************
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        Log.d("MSG","signInWithEmail:success");
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SignInActivity.this, "User Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this,Welcome.class));
                            finish();
                        }
                        if (!task.isSuccessful())
                        {
                            Log.w("MSG","signInWithEmail:failure",task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void newUser(View view)
    {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }
}