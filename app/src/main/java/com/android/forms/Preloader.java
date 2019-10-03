package com.android.forms;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import java.util.Timer;
import java.util.TimerTask;

public class Preloader extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preloader);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Preloader.this, MainActivity.class));
            }
        }, 1000);
    }
}
