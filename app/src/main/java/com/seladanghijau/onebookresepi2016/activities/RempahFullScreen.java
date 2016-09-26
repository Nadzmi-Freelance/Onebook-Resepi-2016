package com.seladanghijau.onebookresepi2016.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.seladanghijau.onebookresepi2016.R;
import com.seladanghijau.onebookresepi2016.adapters.RempahListAdapter;

import java.lang.ref.WeakReference;

public class RempahFullScreen extends AppCompatActivity {
    // views
    ImageView ivRempahImg;

    // vars
    WeakReference<ImageView> rempahWeakRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rempah_full_screen);

        initViews();
        initVars();
        process();
    }

    // initialize ----------------------------------------------------------------------------------
    private void initViews() {
        ivRempahImg = (ImageView) findViewById(R.id.ivRempahImg);
    }

    private void initVars() {
        rempahWeakRef = new WeakReference<>(ivRempahImg);
    }

    private void process() {
        ImageView tempImageView;
        int imgRes;

        tempImageView = rempahWeakRef.get();
        imgRes = getIntent().getExtras().getInt("imgRes-id");
        tempImageView.setImageResource(imgRes);
    }
    // ---------------------------------------------------------------------------------------------
}
