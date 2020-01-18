package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ixirus.ListItem;
import com.example.ixirus.R;

import org.json.JSONObject;

public class CreateDevPlanActivity4 extends AppCompatActivity {
    private JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan4);
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

        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setTextSize(9 * density);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                if(extras.getString("editedDevPlan")!=null) {
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    String  benefitText = object.getString("benefit");

                    ((EditText) findViewById(R.id.editTextBenefits)).setText(benefitText);
                }
            } catch (Throwable t) {
                return;
            }
        }

        Button nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                int programId;
                int perfectionId;
                int behaviourId;
                String planName;
                if (extras != null) {
                    programId = extras.getInt("programId");
                    perfectionId = extras.getInt("perfectionId");
                    behaviourId = extras.getInt("behaviourId");
                    planName = extras.getString("planName");

                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity5.class);
                    if (object != null)
                        intent.putExtra("editedDevPlan", object.toString());

                    intent.putExtra("behaviourId", behaviourId);
                    intent.putExtra("perfectionId", perfectionId);
                    intent.putExtra("programId", programId);
                    intent.putExtra("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());
                    intent.putExtra("planName", planName);
                    startActivity(intent);
                }
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
