package com.box.billy.billybox.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.box.billy.billybox.R;


/**
 * Created by han on 5/15/2018.
 */

public class Splash extends AppCompatActivity {

    private static int splash_interval = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent( Splash.this, MainMember.class );
                startActivity(i);

                this.finish();
            }
            private void finish(){

            }
        }, splash_interval);
    }
}
