package com.innova.lawyerhiringsystem;
/* Welcome Screen
*  It is the first screen after app is loaded (after splash screen)
*  Redirects the user to login or regirster
* */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.innova.lawyerhiringsystem.getstarted.GetStarted;

public class WelcomeScreen extends AppCompatActivity {

    TextView appMotto, getStarted;
    Button register, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        // font settings
        appMotto =(TextView) findViewById(R.id.legall_text);
        String fontPath="fonts/PoiretOne.ttf";
        Typeface tf=Typeface.createFromAsset(getAssets(),fontPath);
        appMotto.setTypeface(tf);

        register =(Button) findViewById(R.id.btn_signup);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeScreen.this,Register.class));
                finish();
            }
        });

        login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeScreen.this,Login.class));
                finish();
            }
        });

        getStarted = findViewById(R.id.getstarted);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeScreen.this, GetStarted.class));
                finish();
            }
        });

    }
}