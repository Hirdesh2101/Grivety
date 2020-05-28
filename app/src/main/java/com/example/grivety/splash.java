package com.example.grivety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        logolauncher logolauncher = new logolauncher();
        logolauncher.start();
    }

    private class logolauncher extends Thread{
        @Override
        public void run() {
            try{
                sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Intent intent = new Intent(splash.this,Login_Form.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fragment_open_enter,R.anim.nav_default_exit_anim);
            splash.this.finish();
        }
    }
}

