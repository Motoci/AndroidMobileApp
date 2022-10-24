package com.example.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.myapplication.Likes.LikesActivity;
import com.example.myapplication.Main.MainActivity;
import com.example.myapplication.Profile.ProfileActivity;
import com.example.myapplication.R;
import com.example.myapplication.Search.SearchActivity;
import com.example.myapplication.Share.ShareActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationView bottomNavigationView) {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
//        bottomNavigationViewEx.enableAnimation(false);
//        bottomNavigationViewEx.enableItemShiftingMode(false);
//        bottomNavigationViewEx.enableShiftingMode(false);
//        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationView view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_house: // ACTIVITY_NUM = 0
                        Intent intent1 = new Intent(context, MainActivity.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_search: // ACTIVITY_NUM = 1
                        Intent intent2 = new Intent(context, SearchActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_circle: // ACTIVITY_NUM = 2
                        Intent intent3 = new Intent(context, ShareActivity.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_alert: // ACTIVITY_NUM = 3
                        Intent intent4 = new Intent(context, LikesActivity.class);
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_android: // ACTIVITY_NUM = 4
                        Intent intent5 = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent5);
                        break;
                }
                return false;
            }
        });
    }
}
