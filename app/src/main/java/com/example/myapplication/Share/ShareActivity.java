package com.example.myapplication.Share;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Profile.ProfileFragment;
import com.example.myapplication.R;
import com.example.myapplication.utils.BottomNavigationViewHelper;
import com.example.myapplication.utils.SectionsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class ShareActivity extends AppCompatActivity {

    private static final String TAG = "ShareActivity";
    private static final int ACTIVITY_NUM = 2;
    private final Context mContext = ShareActivity.this;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Log.d(TAG, "onCreate: started.");
        setupViewPager();

    }

    public int getCurrentTabNumber(){
        return mViewPager.getCurrentItem();
    }

    private void setupViewPager(){
        SectionsPagerAdapter adapter =  new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GalleryFragment());
        adapter.addFragment(new PhotoFragment());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabsBottom);
        tabLayout.setupWithViewPager(mViewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setText(getString(R.string.gallery));
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText(getString(R.string.photo));
    }

    public int getTask(){
        Log.d(TAG, "getTask: TASK: " + getIntent().getFlags());
        return getIntent().getFlags();
    }

    // BottomNavigationView setup
    private void setupBottomNavigationView() {
        Log.d(TAG, "settingUpBottomNavigationView");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
