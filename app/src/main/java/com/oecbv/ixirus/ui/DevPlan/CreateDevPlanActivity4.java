package com.oecbv.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oecbv.ixirus.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateDevPlanActivity4 extends AppCompatActivity {
    private JSONObject object;
    private int selectedPerfectionId;
    private int selectedProgramId;
    private String selectedDevPlanName;
    private int selectedBehaviourId;

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
                selectedBehaviourId = extras.getInt("behaviourId");
                selectedPerfectionId = extras.getInt("perfectionId");
                selectedProgramId = extras.getInt("programId");
                selectedDevPlanName = extras.getString("planName");

                if (extras.getString("editedDevPlan") != null) {
                    imageView.setVisibility(View.GONE);//geri tuşu inaktif
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    String benefitText = object.getString("benefit");

                    ((EditText) findViewById(R.id.editTextBenefits)).setText(benefitText);
                } else if (extras.getString("benefit") != null) {
                    ((EditText) findViewById(R.id.editTextBenefits)).setText(extras.getString("benefit"));
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

                    if (object != null) {
                        Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);

                        try {
                            object.put("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (getIntent().hasExtra("fromEdit"))
                            intent.putExtra("fromEdit", true);

                        intent.putExtra("editedDevPlan", object.toString());

                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity5.class);

                        if (getIntent().hasExtra("question1")) {
                            intent.putExtra("question1", getIntent().getExtras().getInt("question1"));
                        }
                        if (getIntent().hasExtra("question2"))
                            intent.putExtra("question2", getIntent().getExtras().getInt("question2"));
                        if (getIntent().hasExtra("question3"))
                            intent.putExtra("question3", getIntent().getExtras().getInt("question3"));
                        if (getIntent().hasExtra("question4"))
                            intent.putExtra("question4", getIntent().getExtras().getInt("question4"));
                        if (getIntent().hasExtra("question5"))
                            intent.putExtra("question5", getIntent().getExtras().getInt("question5"));

                        intent.putExtra("behaviourId", behaviourId);
                        intent.putExtra("perfectionId", perfectionId);
                        intent.putExtra("programId", programId);
                        intent.putExtra("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());
                        intent.putExtra("planName", planName);

                        if (getIntent().hasExtra("program"))
                            intent.putExtra("program", getIntent().getExtras().getString("program"));

                        if (getIntent().hasExtra("perfection"))
                            intent.putExtra("perfection", getIntent().getExtras().getString("perfection"));

                        if (getIntent().hasExtra("behavior"))
                            intent.putExtra("behavior", getIntent().getExtras().getString("behavior"));


                        startActivity(intent);
                    }
                }
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity3.class);
                        if (object != null) {
                    try {
                        object.put("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("editedDevPlan", object.toString());
                } else {

                    intent.putExtra("programId", selectedProgramId);
                    intent.putExtra("planName", selectedDevPlanName);
                    intent.putExtra("perfectionId", selectedPerfectionId);
                    intent.putExtra("behaviourId", selectedBehaviourId);
                    intent.putExtra("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());

                    if (getIntent().hasExtra("program"))
                        intent.putExtra("program", getIntent().getExtras().getString("program"));

                    if (getIntent().hasExtra("perfection"))
                        intent.putExtra("perfection", getIntent().getExtras().getString("perfection"));

                    if (getIntent().hasExtra("behavior"))
                        intent.putExtra("behavior", getIntent().getExtras().getString("behavior"));

                    startActivity(intent);
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (object != null) {
            Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);
            if (getIntent().hasExtra("fromEdit"))
                intent.putExtra("fromEdit", true);
            intent.putExtra("editedDevPlan", object.toString());

            startActivity(intent);
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        } else {
            Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity3.class);

            intent.putExtra("programId", selectedProgramId);
            intent.putExtra("planName", selectedDevPlanName);
            intent.putExtra("perfectionId", selectedPerfectionId);
            intent.putExtra("behaviourId", selectedBehaviourId);
            intent.putExtra("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());

            if (getIntent().hasExtra("program"))
                intent.putExtra("program", getIntent().getExtras().getString("program"));

            if (getIntent().hasExtra("perfection"))
                intent.putExtra("perfection", getIntent().getExtras().getString("perfection"));

            if (getIntent().hasExtra("behavior"))
                intent.putExtra("behavior", getIntent().getExtras().getString("behavior"));

            startActivity(intent);
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }
}
