
package com.neha.womensafezone;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText editTextTextEmailAddress, editTextTextPassword ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }

        mAuth = FirebaseAuth.getInstance();
        Button button = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        editTextTextEmailAddress=findViewById(R.id.email);
        editTextTextPassword=findViewById(R.id.password);
        findViewById(R.id.signup_text).setOnClickListener((View.OnClickListener) this);

        findViewById(R.id.button).setOnClickListener((View.OnClickListener) this);
    }
    private void UserLoginFunction() {
        String email=editTextTextEmailAddress.getText().toString().trim();
        String password= editTextTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextTextEmailAddress.setError("Email_id is required");
            editTextTextEmailAddress.requestFocus();
            return;

        }


        else if(password.isEmpty()){
            editTextTextPassword.setError("Password is required");
            editTextTextPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextTextEmailAddress.setError("enter valid email id");
            editTextTextEmailAddress.requestFocus();
            return;
        }
        if(password.length()<8){
            editTextTextPassword.setError("minimum length of password must be eight");
            editTextTextPassword.requestFocus();
            return;

        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Intent intent =new Intent(LoginActivity.this,ChoiceActivity.class);
                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"LoggedIn Successfully",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Wrong email or password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                UserLoginFunction();
                break;

            case R.id.signup_text:
                startActivity(new Intent(this,SignUp.class));
                break;
        }

    }

}
