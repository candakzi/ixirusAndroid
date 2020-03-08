package com.example.ixirus.ui.Disc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ixirus.R;
import com.example.ixirus.ui.DevPlan.CreateDevPlanActivity3;
import com.example.ixirus.ui.DevPlan.MyDevPlanListActivity;
import com.example.ixirus.ui.MainActivityWithoutFragment;

import org.json.JSONArray;
import org.json.JSONObject;

public class DiscMainActivity extends AppCompatActivity {
    private JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disc_main);
        final TextView tv = (TextView) findViewById(R.id.textView2);
        final TextView tvTitle = (TextView) findViewById(R.id.textViewTitle);
        final TextView tvBehavStyleTitle = (TextView) findViewById(R.id.textBehaviorTitle);
        final TextView tvBehavStyle = (TextView) findViewById(R.id.textBehavior);
        final TextView tvOtherColorsTitle = (TextView) findViewById(R.id.textOtherColorsTitle);
        final TextView tvOtherColors = (TextView) findViewById(R.id.textOtherColors);
        final TextView tvInvite = (TextView) findViewById(R.id.textInviteOthers);
        final TextView tvRetake = (TextView) findViewById(R.id.textRetakeQuiz);

        ImageView imageView = findViewById(R.id.buttonBack);
        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(R.mipmap.ixirus_logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(imgView);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        tv.setTextSize(9 * density);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                object = new JSONObject(extras.getString("DiscObject"));
                JSONArray discInfoObject = object.getJSONArray("discInfo");
                JSONObject otherDiscInfoObject = object.getJSONObject("otherDiscInfo");

                String discInfoTitle = ((JSONObject)discInfoObject.get(0)).getString("Title");
                String discInfo =((JSONObject)discInfoObject.get(0)).getString("Description");

                String otherDiscInfoTitle = otherDiscInfoObject.getString("Title");
                String otherDiscInfo = otherDiscInfoObject.getString("Description");

                tvBehavStyleTitle.setText(discInfoTitle);
                tvBehavStyle.setText(discInfo);

                tvOtherColorsTitle.setText(otherDiscInfoTitle);
                tvOtherColors.setText(otherDiscInfo);

                String titleText = object.getString("discResult");
                tvTitle.setText(titleText);

            } catch (Throwable t) {
                return;
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivityWithoutFragment.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

        tvRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DiscQuestionsActivity.class);
                startActivity(intent);
            }
        });

        tvBehavStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DiscDetailActivity.class);
                intent.putExtra("DiscObject", object.toString());
                intent.putExtra("Type", true);
                startActivity(intent);
            }
        });

        tvOtherColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DiscDetailActivity.class);
                intent.putExtra("DiscObject", object.toString());
                intent.putExtra("Type", false);
                startActivity(intent);
            }
        });
    }
}
