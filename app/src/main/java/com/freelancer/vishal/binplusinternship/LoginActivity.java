package com.freelancer.vishal.binplusinternship;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailText, loginPasswordText;
    private Button loginBtn, loginRegBtn;
    private FirebaseAuth mAuth;
    private ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        //finding objects for the defined widgets
        loginEmailText = findViewById(R.id.login_email_edit);
        loginPasswordText = findViewById(R.id.login_password_edit);
        loginBtn = findViewById(R.id.login_button);
        loginRegBtn = findViewById(R.id.login_register_button);
        loginProgressBar = findViewById(R.id.login_progressBar);

        // Register button click
        loginRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(regIntent);
                finish();
            }
        });

        // Login buton click listner
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String loginEmail = loginEmailText.getText().toString();
                String loginPassword = loginPasswordText.getText().toString();

                //check the email and password string is not empty

                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword)){
                    loginProgressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if (task.isSuccessful()){

                                sendToMain();

                            }else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this,"Error : " + errorMessage, Toast.LENGTH_LONG).show();
                            }
                            loginProgressBar.setVisibility(View.INVISIBLE);

                        }

                    });

                }

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            sendToMain();

        }
    }
    private void sendToMain() {
        Intent mainActivityIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }
}
