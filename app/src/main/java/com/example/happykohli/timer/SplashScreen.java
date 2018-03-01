package com.example.happykohli.timer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread t = new Thread()
        {
            @Override
        public void run(){
            try{
                sleep(4000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                Intent i=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);
            }
        }
        };
        t.start();

    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
