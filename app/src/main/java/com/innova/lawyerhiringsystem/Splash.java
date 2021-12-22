package com.innova.lawyerhiringsystem;
/*
* Launcher Activity
* Initial Configuration is done in this activity
* Splash Screen
* Animation
* All required permissions
* Routing based on user role and logged in data
* */
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //FADE - IN animation
        logo = findViewById(R.id.logo);
        logo.setVisibility(View.GONE);
        final Animation fadein = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);

        //Adding some delay
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                logo.setVisibility(View.VISIBLE);
                logo.startAnimation(fadein);
                if (checkPermission()) {
                    openActivity();
                }
            }
        }, 500);

        // Check required permissions
        if (!checkPermission()) {
          // permissions are not granted
            AlertDialog.Builder dialog = new AlertDialog.Builder(Splash.this);
            dialog.setTitle("Permissions Required!")
                    .setMessage("App requires certain permissions for the proper functioning of the features. Please grant permissions in your application manager.")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setPositiveButton("Give Permissions", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE,Manifest.permission.CAMERA}, 1);
                            checkPermission();

                        }
                    }).setCancelable(false).show();
        }

    }

    //getting app permissions
    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(Splash.this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {

            return false;
        } else
            return true;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {

                    openActivity();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Splash.this);
                    dialog.setTitle("Permissions Required!")
                            .setMessage("App requires certain permissions for the proper functioning of the features. Please grant permissions for Legall in your application manager.")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .setPositiveButton("Give Permissions", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent settings = new Intent();
                                    settings.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    settings.setData(uri);
                                    startActivity(settings);
                                    finish();

                                }
                            }).setCancelable(false).show();
                }
        }
    }

    // routing to next activity based on user info
    public void openActivity() {
        // some delay to load animations properly
        final android.os.Handler intentHandler = new android.os.Handler();
        intentHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, WelcomeScreen.class));
                finish();

            }
        }, 1500);
    }
    }


