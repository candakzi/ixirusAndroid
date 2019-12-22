package com.example.ixirus.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.ixirus.R;

public class BaseScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_screen);
        ImageView imgView = findViewById(R.id.imageView7);
        imgView.setImageResource(R.mipmap.ixirus_logo_big);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences loginPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

                if (loginPreferences.contains("Token")) {
                    Intent intentMain = new Intent(getBaseContext() ,MainActivityWithoutFragment.class );
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
                    startActivity(intentMain);
                    BaseScreenActivity.this.overridePendingTransition(0, 0);
                    finish();

                } else {

                    Intent intentMain = new Intent(getBaseContext(), LoginActivity.class );
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
                    startActivity(intentMain);
                    BaseScreenActivity.this.overridePendingTransition(0, 0);
                    finish();
                }
            }
        }, 500);
    }
}
