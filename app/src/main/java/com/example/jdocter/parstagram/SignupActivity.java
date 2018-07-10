package com.example.jdocter.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private Button btnSignup;
    private EditText etEmail;
    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        btnSignup = findViewById(R.id.btnSignup);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etNewUser);
        etPassword = findViewById(R.id.etNewPass);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                signup(email,name,username,password);
            }
        });
    }

    private void signup(String email,String name, String username, String password) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Set custom properties
        user.put("name", name);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.d("SignupActivity","Successful Signup");
                    final Intent intent = new Intent(SignupActivity.this, com.example.jdocter.parstagram.LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignupActivity.this,"Successful sign up!",Toast.LENGTH_LONG);
                    finish();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    // TODO tell user why signup didnt work
                    Log.e("SignupActivity","Failed Signup");
                    e.printStackTrace();
                }
            }
        });

    }
}
