package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String SHARED_USER = "local_user";

    ImageView imageView;
    TextView textView;
    LottieAnimationView lottieAnimationView;
    String user;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = (ImageView) findViewById(R.id.appBG);
        textView = (TextView) findViewById(R.id.appName);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.splashGIF);

        imageView.animate().translationY(-2500).setDuration(1000).setStartDelay(4000);
        textView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        sharedpreferences =  getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(SHARED_USER, null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user!= null){
                    Intent intent = new Intent(getApplicationContext(), BottomTabActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },4930);

    }
}