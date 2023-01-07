package com.example.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.Login.LoginActivity;
import com.example.myapplication.Login.RegisterActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserAccountSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String userID;

    private Context mContext;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public boolean checkIfUsernameAlreadyExists(String username, DataSnapshot dataSnapshot) {
        Log.d(TAG, "checkIfUsernameAlreadyExists: checking if " + username + " already exists.");

        User user = new User();

        for (DataSnapshot ds: dataSnapshot.getChildren()) {
            Log.d(TAG, "checkIfUsernameAlreadyExists: datasnapshot: " + ds);

            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameAlreadyExists: username: " + user.getUsername());

            if (StringManipulation.expandUsername(user.getUsername()).equals(username)) {
                Log.d(TAG, "checkIfUsernameAlreadyExists: Found a match: " + user.getUsername());
                return true;
            }
        }
        return false;
    }

    /**
     * Register a new email and password to firebase authentication
     * @param email
     * @param password
     * @param username
     */
    public void registerNewEmail(final String email,
                                 final String password,
                                 final String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(mContext,
                            "RegistrationError: " + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();

                } else if (task.isSuccessful()){
                    Toast.makeText(mContext,"User registered successfully", Toast.LENGTH_SHORT).show();
                    userID = mAuth.getCurrentUser().getUid();
                }
            }
        });
    }

    public void addNewUser(String email, String username, String description, String profile_photo) {
        User user = new User(userID, 1, email, StringManipulation.condenseUsername(username));

        myRef.child(mContext.getString(R.string.db_users))
                .child(userID)
                .setValue(user);

        UserAccountSettings settings = new UserAccountSettings(
                description,
                username,
                0,
                0,
                0,
                profile_photo,
                username);

        myRef.child(mContext.getString(R.string.db_user_account_settings))
                .child(userID)
                .setValue(settings);
    }




}
