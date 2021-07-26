package com.example.hamiltontevin_ce06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hamiltontevin_ce06.file.FileStorageHelper;
import com.example.hamiltontevin_ce06.fragments.ImageGalleryFragment;
import com.example.hamiltontevin_ce06.service.ImageIntentService;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_ARRAY = "EXTRA_IMAGE_ARRAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_download){
            TextView tv = findViewById(R.id.tv_userPromt);
            tv.setVisibility(View.GONE);

             FileStorageHelper imageFileStorage = new FileStorageHelper();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout_gallerDisplay , ImageGalleryFragment
                            .newInstance()).commitNow();

            Intent intent = new Intent(this, ImageIntentService.class);
            intent.putExtra(EXTRA_IMAGE_ARRAY, imageFileStorage.IMAGES);
            startService(intent);
        }
        return true;
    }
}
