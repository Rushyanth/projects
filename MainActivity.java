package com.example.rushyanthreddy.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button b_signup,b_login;
    EditText userEmail,userPassword;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private final String TAG = "Login Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_signup=(Button)findViewById(R.id.button_signup);
        b_login=(Button)findViewById(R.id.button_login);
        userEmail=(EditText)findViewById(R.id.edittext_email);
        userPassword=(EditText)findViewById(R.id.edittext_password);

        mAuth=FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    Intent i = new Intent(MainActivity.this,MainHome.class);
                    startActivity(i);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        b_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin(userEmail.getText().toString(),userPassword.getText().toString());
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener!=null){
            mAuth.addAuthStateListener(mAuthListener);
        }
    }
    private void signin(String email,String password){

        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                if(task.isSuccessful()){
                    Intent i = new Intent(MainActivity.this,MainHome.class);
                    startActivity(i);
                }


                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(MainActivity.this,"Login Failed",
                            Toast.LENGTH_SHORT).show();
                    userPassword.setError("Wrong Password");
                    userEmail.setError("Wrong Mail ID");
                }
            }
        });



    }
    private boolean validateForm() {
        boolean valid = true;

        String email = userEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Required.");
            valid = false;
        } else {
            userEmail.setError(null);
        }

        String password = userPassword.getText().toString();

            if (TextUtils.isEmpty(password)) {
                userPassword.setError("Required.");
                valid = false;
            } else {
                userPassword.setError(null);
            }

        return valid;
    }


}
