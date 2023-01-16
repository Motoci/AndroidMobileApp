package com.example.myapplication.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserAccountSettings;
import com.example.myapplication.model.UserSettings;
import com.example.myapplication.utils.BottomNavigationViewHelper;
import com.example.myapplication.utils.FirebaseMethods;
import com.example.myapplication.utils.UniversalImageLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private static final int ACTIVITY_NUM = 4;

    private TextView mPosts, mFollowers, mFollowing, mDisplayName, mUserName, mDescription;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;
    private Toolbar toolbar;
    private GridView gridView;
    private BottomNavigationView bottomNavigationView;
    private ImageView profileMenu;

    private Context mContext;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();
        mDisplayName = view.findViewById(R.id.display_name);
        mUserName = view.findViewById(R.id.username);
        mDescription = view.findViewById(R.id.description);
        mProfilePhoto = view.findViewById(R.id.profile_photo);
        mPosts = view.findViewById(R.id.tvPosts);
        mFollowers = view.findViewById(R.id.tvFollowers);
        mFollowing = view.findViewById(R.id.tvFollowing);
        mProgressBar = view.findViewById(R.id.profileProgressBar);
        gridView = view.findViewById(R.id.gridView);
        toolbar = view.findViewById(R.id.profileToolBar);
        profileMenu = view.findViewById(R.id.profileMenu);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
        mFirebaseMethods = new FirebaseMethods(getActivity());
        Log.d(TAG, "onCreateView: started");

        setupBottomNavigationView();
        setupToolBar();
        setupFirebaseAuth();

        TextView editProfile = view.findViewById(R.id.EditProfileMenu);
        editProfile.setOnClickListener(v -> {
            Log.d(TAG, "onClick: navigating to " + mContext.getString(R.string.edit_profile_fragment));
            Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
            intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_activity));
            startActivity(intent);
        });

        return view;
    }

    private void setProfileWidgets(UserSettings userSettings) {
        Log.d(TAG, "setProfileWidgets: setting widgets with data from DB");
//        User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(settings.getDisplay_name());
        mUserName.setText(settings.getUsername());
        mDescription.setText(settings.getDescription());
        mPosts.setText(String.valueOf(settings.getPosts()));
        mFollowers.setText(String.valueOf(settings.getFollowers()));
        mFollowing.setText(String.valueOf(settings.getFollowing()));
        mProgressBar.setVisibility(View.GONE);
    }

    // BottomNavigationView setup
    private void setupBottomNavigationView() {
        Log.d(TAG, "settingUpBottomNavigationView");
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void setupToolBar() {

        ((ProfileActivity)getActivity()).setSupportActionBar(toolbar);

        profileMenu.setOnClickListener(v -> {
            Log.d(TAG, "onMenuItemClick: clicked signOut Button");
            startActivity(new Intent(mContext, AccountSettingsActivity.class));
        });
    }

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                Log.d(TAG, "onAuthStateChanged: signed in" + user.getUid());
            } else {
                Log.d(TAG, "onAuthStateChanged: signed out");

            }
        };

        // get dataSnapShot & read or write to DB
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // retrieve user information from the database
                setProfileWidgets(mFirebaseMethods.getUserSettings(snapshot));

                // retrieve images for the user

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
