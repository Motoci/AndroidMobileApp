package com.example.myapplication.Share;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class PhotoFragment extends Fragment {
    private static final String TAG = "PhotoFragment";
    private static final int PHOTO_FRAGMENT_NUM = 1;
    private static final int GALLERY_FRAGMENT_NUM = 2;
    private static final int  CAMERA_REQUEST_CODE = 5;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        Log.d(TAG, "onCreateView: started.");

        Button btnLaunchCamera = view.findViewById(R.id.btnLaunchCamera);
        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: launching camera.");

                if(((ShareActivity) requireActivity()).getCurrentTabNumber() == PHOTO_FRAGMENT_NUM){

                        Log.d(TAG, "onClick: starting camera");
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(cameraIntent);
//                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
            }
        });

        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == CAMERA_REQUEST_CODE){
//            Log.d(TAG, "onActivityResult: done taking a photo.");
//            Log.d(TAG, "onActivityResult: attempting to navigate to final share screen.");
//            //navigate to the final share screen to publish photo
//        }
//    }
}
