package it.spasia.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import it.spasia.R;

public class Splash extends Activity{
    private final int SPLASH_DISPLAY_LENGHT = 2000;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Tabbed.class);
                Splash.this.startActivity(intent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
