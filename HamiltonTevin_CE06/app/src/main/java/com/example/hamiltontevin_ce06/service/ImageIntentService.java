package com.example.hamiltontevin_ce06.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hamiltontevin_ce06.MainActivity;
import com.example.hamiltontevin_ce06.file.FileStorageHelper;
import com.example.hamiltontevin_ce06.fragments.ImageGalleryFragment;
import com.example.hamiltontevin_ce06.network.NetworkUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageIntentService extends IntentService {

    public ImageIntentService() {
        super("service.ImageIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null && intent.hasExtra(MainActivity.EXTRA_IMAGE_ARRAY)){
            getImageFile(intent.getStringArrayExtra(MainActivity.EXTRA_IMAGE_ARRAY));
        }

    }

    private void getImageFile(String[] imageArray){
        if(imageArray != null) {
            File folderPath = getExternalFilesDir(FileStorageHelper.IMAGE_FOLDER);
            if (folderPath != null) {
                for (String currentImageString : imageArray) {
                    File imageFile = new File(folderPath, currentImageString);
                    if (!imageFile.exists()) {
                        try {
                            final boolean newFile = imageFile.createNewFile();
                            Log.i(getClass().getName(),"file was created: " + newFile);

                            FileOutputStream out = new FileOutputStream(imageFile);
                             byte[] ba  = NetworkUtils.getNetworkData(currentImageString);
                             if(ba != null) {
                                 out.write(ba);
                             }
                            out.close();
                        } catch (IOException | NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                    sendBroadCast();
                }
            }
        }
    }

    private void sendBroadCast(){
        Intent intent = new Intent(ImageGalleryFragment.BROADCAST_ACTION);
        sendBroadcast(intent);
    }
}
