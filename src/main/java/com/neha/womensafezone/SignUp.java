package com.neha.womensafezone;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    DatabaseReference RootReference;
    Button register;
    EditText editTextTextEmailAddress2, editTextPhone,editTextTextPassword2;
    private  DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        try{
            this.getSupportActionBar().hide();
        }catch(NullPointerException e){

        }
        editTextTextEmailAddress2=findViewById(R.id.r_email);
        editTextPhone =findViewById(R.id.r_contact);
        editTextTextPassword2=findViewById(R.id.r_password);
        register=findViewById(R.id.r_button);
        mAuth = FirebaseAuth.getInstance();
        RootReference= FirebaseDatabase.getInstance().getReference();
        findViewById(R.id.r_button).setOnClickListener((View.OnClickListener) this);
        ref = FirebaseDatabase.getInstance().getReference();
    }

    private void registerUser(){

        String email= editTextTextEmailAddress2.getText().toString().trim();

        String phone_number= editTextPhone.getText().toString().trim();


        String password= editTextTextPassword2.getText().toString().trim();

        if(email.isEmpty()){
            editTextTextEmailAddress2.setError("Email_id is required");
            editTextTextEmailAddress2.requestFocus();
            return;

        }

        else if(phone_number.isEmpty())
        {
            editTextPhone.setError("phone number required");
            editTextPhone.requestFocus();
        }

        else if(password.isEmpty()){
            editTextTextPassword2.setError("Password is required");
            editTextTextPassword2.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextTextEmailAddress2.setError("enter valid email id");
            editTextTextEmailAddress2.requestFocus();
            return;
        }
        if(password.length()<8){
            editTextTextPassword2.setError("minimum length of password must be eight");
            editTextTextPassword2.requestFocus();
            return;

        }
        if(phone_number.length()<10 || phone_number.length()>10){
            editTextPhone.setError("count the digits of typed number");
            editTextPhone.requestFocus();
            return;

        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String currentUserId =mAuth.getCurrentUser().getUid();
                            ref.child("AppUsers").child(currentUserId).setValue(" ");

                            Toast.makeText(getApplicationContext(),"Register Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(SignUp.this,ChoiceActivity.class);
                            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"You are already Register",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Error in Registration",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.r_button:
                registerUser();

        }

    }
}
