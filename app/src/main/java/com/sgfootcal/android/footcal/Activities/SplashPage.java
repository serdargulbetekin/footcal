package com.sgfootcal.android.footcal.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sgfjcrmpr.android.foot_cal.R;


public class SplashPage extends AppCompatActivity {
    private static MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);


        Thread time = new Thread(){
            public void run(){
                try{
                    // soundPlayer(getApplicationContext(),R.raw.howyoudoin);
                    sleep(2000);
                    Intent intent = new Intent(SplashPage.this,LoginScreen.class);
                    startActivity(intent);
                    finish();

                }
                catch(InterruptedException e){
                    e.printStackTrace();

                }
            }

        };
        time.start();

    }

}
