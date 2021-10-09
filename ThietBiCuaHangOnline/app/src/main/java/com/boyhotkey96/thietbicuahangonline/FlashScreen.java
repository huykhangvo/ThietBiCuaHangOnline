package com.boyhotkey96.thietbicuahangonline;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boyhotkey96.thietbicuahangonline.activity.Login;

public class FlashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        //sau 2s open Login
        initDisplay();
    }

    private void initDisplay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FlashScreen.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                finish();
            }
        }, 2000);
    }
}
