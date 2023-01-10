package com.example.myapplication.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Login.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.utils.BottomNavigationViewHelper;
import com.example.myapplication.utils.SectionsPagerAdapter;
import com.example.myapplication.utils.UniversalImageLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;

/*
    Fragments have a different stack than activities
    Fragments don't keep track of their stack, you have to track it manually

 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;

    private final Context mContext = MainActivity.this;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starting.");

        setupFirebaseAuth();

        initImageLoader();
        setupBottomNavigationView();
        setupViewPager();

//        mAuth.signOut();
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    /**
     * responsible for adding the 3 tabs: Camera, Home, Messages
     */
    private void setupViewPager() {
        SectionsPagerAdapter adapter =
                new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new CameraFragment()); // index 0
        adapter.addFragment(new MainFragment()); // index 1
        adapter.addFragment(new MessagesFragment()); // index 2

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_name);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_arrow);
    }
    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d(TAG, "settingUpBottomNavigationView");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
        mAuth = FirebaseAuth.getInstance();

    }

    /*
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Firebase ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private void checkCurrentUser(FirebaseUser user) {
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if (user == null) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                // checks if the user user logged in
                checkCurrentUser(user);

                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged: signed in" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged: signed out");

                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}