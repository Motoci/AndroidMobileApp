package com.example.myapplication.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.utils.BottomNavigationViewHelper;
import com.example.myapplication.utils.GridImageAdapter;
import com.example.myapplication.utils.UniversalImageLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;

    private final Context mContext = ProfileActivity.this;
    private ProgressBar progressBar;
    private ImageView profilePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started.");

        init();


//        setupBottomNavigationView();
//        setupToolBar();
//        setupActivityWidgets();
//        setProfileImage();
//        tempGridSetup();
    }

    private void init() {
        Log.d(TAG, "init: inflating " + getString(R.string.profile_fragment));

        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction transaction =
                ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();

    }
//    private void tempGridSetup() {
//        ArrayList<String> imgURLs = new ArrayList<>();
//        imgURLs.add("https://ghantee.com/wp-content/uploads/2022/10/trishul-image-black-576x1024.jpg");
//        imgURLs.add("https://m.atcdn.co.uk/vms/media/%7Bresize%7D/a68575af72a34d108949947dd57d1a2a.jpg");
//        imgURLs.add("https://lumiere-a.akamaihd.net/v1/images/open-uri20150422-20810-1fndzcd_41017374.jpeg");
//        imgURLs.add("https://c4.wallpaperflare.com/wallpaper/572/103/624/cars-mater-tow-truck-wallpaper-preview.jpg");
//        imgURLs.add("https://i.ytimg.com/vi/R1jpnPZ8lrk/maxresdefault.jpg");
//        imgURLs.add("https://www.carscoops.com/wp-content/uploads/2020/07/Dodge-Viper.jpg");
//        imgURLs.add("https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/Salon_de_l%27auto_de_Gen%C3%A8ve_2014_-_20140305_-_Chevrolet_Corvette_Stingray_Z06.jpg/1200px-Salon_de_l%27auto_de_Gen%C3%A8ve_2014_-_20140305_-_Chevrolet_Corvette_Stingray_Z06.jpg");
//        imgURLs.add("https://tb.ziareromania.ro/Prinsi-in-timp-ce-furau-o-masina-cu----caruta/322a3e322a09a0f3/400/225/2/100/Prinsi-in-timp-ce-furau-o-masina-cu----caruta.jpg");
//        imgURLs.add("https://i0.wp.com/opiniedecarei.com/wp-content/uploads/2016/08/caruta1.jpg?fit=700%2C492&ssl=1");
//
//        setupImageGrid(imgURLs);
//    }
//
//    private void setupImageGrid(ArrayList<String> imageURLs) {
//        GridView gridView = findViewById(R.id.gridView);
//
//        int gridWidth = getResources().getDisplayMetrics().widthPixels;
//        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
//        gridView.setColumnWidth(imageWidth);
//
//        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imageURLs);
//        gridView.setAdapter(adapter);
//    }
//
//    private void setProfileImage() {
//        Log.d(TAG, "setProfileImage: setting profile photo");
//        String imageURL = "i.redd.it/peibne3f05a81.png";
//        UniversalImageLoader.setImage(imageURL, profilePhoto, progressBar, "https://");
//    }
//
//    private void setupActivityWidgets() {
//        progressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
//        progressBar.setVisibility(View.GONE);
//        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
//    }
//
//
}
