package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ixirus.R;

public class CreateDevPlanActivity8 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan8);
        ImageView imageView = findViewById(R.id.buttonBack);
        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan) ;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(R.mipmap.ixirus_logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(imgView);

        final TextView tv = (TextView)findViewById(R.id.textView2);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        tv.setTextSize(9 * density);
    }
}
