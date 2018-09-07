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

public class RegisterActivity extends AppCompatActivity {

    private EditText regEmailEdit, regPassEdit, regConfirmPssEdit;
    private Button regBtn, regLoginBtn;
    private ProgressBar regProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        regEmailEdit = findViewById(R.id.reg_email_edit);
        regPassEdit = findViewById(R.id.reg_password_edit);
        regConfirmPssEdit = findViewById(R.id.reg_conf_password_edit);

        regBtn = findViewById(R.id.register_button);
        regLoginBtn = findViewById(R.id.reg_login_button);

        regProgressBar = findViewById(R.id.reg_progress_bar);

        regProgressBar.setVisibility(View.INVISIBLE);

        // after clicking on alredy have an accoun button

        regLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        // after clicking on Register button
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = regEmailEdit.getText().toString();
                String pass = regPassEdit.getText().toString();
                String confirmPass = regConfirmPssEdit.getText().toString();


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirmPass)){
                    if (pass.equals(confirmPass)){
                        regProgressBar.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){
                                if (task.isSuccessful()){
//                                    Intent setupIntent = new Intent(RegisterActivity.this,MainActivity.class);
//                                    startActivity(setupIntent);
//                                    finish();
                                    sendToMain();
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this,"Error : " + errorMessage,Toast.LENGTH_LONG).show();
                                }

                                regProgressBar.setVisibility(View.INVISIBLE);
                            }

                        });

                    }else {
                        Toast.makeText(RegisterActivity.this,"Password and Confirm Password does not match",Toast.LENGTH_LONG).show();
                    }

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
        Intent mainActivityIntent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }
}
