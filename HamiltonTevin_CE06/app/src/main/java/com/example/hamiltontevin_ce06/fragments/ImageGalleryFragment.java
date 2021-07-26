package com.example.hamiltontevin_ce06.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.hamiltontevin_ce06.R;
import com.example.hamiltontevin_ce06.adapter.ImageAdapter;
import com.example.hamiltontevin_ce06.file.FileStorageHelper;

import java.io.File;

public class ImageGalleryFragment extends Fragment {
    public static final String BROADCAST_ACTION =
            "com.fullsail.android.broadcasterapp.Fragments.BROADCAST_ACTION";

    private  static final String AUTHORITY = "com.example.hamiltontevin_ce06.fileprovider";

    private final ImageReceiver mReceiver = new ImageReceiver();

    private final FileStorageHelper mImageFileStorage = new FileStorageHelper();

    public ImageGalleryFragment() {
    }

    public static ImageGalleryFragment newInstance() {
        Bundle args = new Bundle();

        ImageGalleryFragment fragment = new ImageGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery_display,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);
        if(getActivity() != null) {
            getActivity().registerReceiver(mReceiver, filter);
        }
    }

    @Override
    public void onPause() {
        Log.i(getClass().getName(),"onPause()");
        super.onPause();
        if(getActivity() != null) {
            getActivity().unregisterReceiver(mReceiver);
        }
    }

    private void updateGridView(){
        if (getView() != null){
            GridView galleryGridView = getView().findViewById(R.id.gridView_imageDisplay);

            if(getActivity() != null) {
                File folderPath = getActivity().getExternalFilesDir(FileStorageHelper.IMAGE_FOLDER);
                if (folderPath != null) {
                    File[] fileList = folderPath.listFiles();
                    if(fileList != null){
                        mImageFileStorage.setFileArray(fileList);
                        ImageAdapter ia = new ImageAdapter(getActivity(), fileList);
                        galleryGridView.setAdapter(ia);
                    }
                }
            }
            galleryGridView.setOnItemClickListener(mImageClickListener);
        }
    }

    private final AdapterView.OnItemClickListener mImageClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(getActivity() != null) {
                Uri passingUri = Uri.parse(String.valueOf(FileProvider.getUriForFile(getActivity(), AUTHORITY, mImageFileStorage.getFileArray()[position])));
                Intent imageView = new Intent(android.content.Intent.ACTION_VIEW);
                imageView.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imageView.setDataAndType(passingUri, "image/*");
                startActivity(imageView);
            }
        }
    };

    class ImageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("this", "reached on recieved");
            updateGridView();
        }
    }
}
