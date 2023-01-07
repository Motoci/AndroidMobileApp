package com.example.myapplication.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.utils.FirebaseMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private Button btnRegister;
    private TextView loadingPleaseWait;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mUsername;
    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append = "";
    private String email, username, password;

    /**
     * Initialize activity widgets
     */
    private void initWidgets() {
        Log.d(TAG, "initWidgets: Initializing widgets");
        mProgressBar = findViewById(R.id.progressBar);
        loadingPleaseWait = findViewById(R.id.loadingPleaseWait);
        loadingPleaseWait.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        btnRegister = findViewById(R.id.btn_register);
        mContext = RegisterActivity.this;

        mEmail = findViewById(R.id.input_email_register);
        mPassword = findViewById(R.id.input_password_register);
        mUsername = findViewById(R.id.input_username_register);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = RegisterActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        Log.d(TAG, "onCreate: started");

        initWidgets();
        setupFirebaseAuth();

        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(view -> {
            createUser();
        });

        mTextView.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private boolean checkInputs(String email, String password, String username) {
        Log.d(TAG, "checkInputs: checking inputs for null values");
        if (email.equals("") || password.equals("") || username.equals("")) {
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createUser() {
        email = mEmail.getText().toString();
        username = mUsername.getText().toString();
        password = mPassword.getText().toString();

        if (checkInputs(email, password, username)) {
            mProgressBar.setVisibility(View.VISIBLE);
            loadingPleaseWait.setVisibility(View.VISIBLE);

            firebaseMethods.registerNewEmail(email, password, username);
        }
    }

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    Log.d(TAG, "onAuthStateChanged: signed in: " + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check to make sure the username is not already in use;
                            if (firebaseMethods.checkIfUsernameAlreadyExists(username, snapshot)) {
                                append = myRef.push().getKey().substring(3,10);
                                Log.d(TAG, "onDataChange: username already exists. Appending random string to it.");
                            }
                            username = username + append;

                            // add new user to the db
                            // add new user_account_settings to the db
                            firebaseMethods.addNewUser(email, username, "", "");

                            Toast.makeText(mContext, "Signup successful. Sending verification email.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    // user is signed out
                    Log.d(TAG, "onAuthStateChanged: signed out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
