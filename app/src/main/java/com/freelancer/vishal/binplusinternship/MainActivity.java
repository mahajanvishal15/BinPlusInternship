package com.freelancer.vishal.binplusinternship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar mainToolBar;
    private FirebaseAuth mAuth;
    private TextView mainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mainToolBar = findViewById(R.id.main_tool_bar);
        mainTextView = findViewById(R.id.main_textView);
        setSupportActionBar(mainToolBar);
        getSupportActionBar().setTitle("Internship Programm Selection");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){sendToLogin();}
        else{mainTextView.setText("Welcome to Main Page");}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.main_logout_setting:
                logOut();
                return true;

            default:
                return false;

        }
    }

    private void logOut(){
        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin(){
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
