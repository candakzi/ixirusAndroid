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
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    String benefitText = object.getString("benefit");

                    ((EditText) findViewById(R.id.editTextBenefits)).setText(benefitText);
                }
                else if(extras.getString("benefit") != null){
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

                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity5.class);
                    if (object != null) {
                        if(getIntent().hasExtra("question1")) {
                            try {
                                object.put("question1", getIntent().getExtras().getInt("question1"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                         if(getIntent().hasExtra("question2")) {
                            try {
                                object.put("question2", getIntent().getExtras().getInt("question2"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                         if(getIntent().hasExtra("question3")) {
                            try {
                                object.put("question3", getIntent().getExtras().getInt("question3"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(getIntent().hasExtra("question4")) {
                            try {
                                object.put("question4", getIntent().getExtras().getInt("question4"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(getIntent().hasExtra("question5")) {
                            try {
                                object.put("question5", getIntent().getExtras().getInt("question5"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        intent.putExtra("editedDevPlan", object.toString());
                    }
                    else {

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
                    }

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
                Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity3.class);
                if(object!=null) {
                    try {
                        object.put("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("editedDevPlan", object.toString());
                }

                intent.putExtra("programId", selectedProgramId);
                intent.putExtra("planName", selectedDevPlanName);
                intent.putExtra("perfectionId", selectedPerfectionId);
                intent.putExtra("behaviourId", selectedBehaviourId);
                intent.putExtra("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());

                startActivity(intent);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity3.class);

        if(object!=null) {
            try {
                object.put("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            intent.putExtra("editedDevPlan", object.toString());
        }

        intent.putExtra("programId", selectedProgramId);
        intent.putExtra("planName", selectedDevPlanName);
        intent.putExtra("perfectionId", selectedPerfectionId);
        intent.putExtra("behaviourId", selectedBehaviourId);
        intent.putExtra("benefit", ((EditText) findViewById(R.id.editTextBenefits)).getText().toString());

        startActivity(intent);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
