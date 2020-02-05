package com.example.ixirus.ui.DevPlan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ixirus.ListAdapters.TaskListAdapter;
import com.example.ixirus.ListItemTasks;
import com.example.ixirus.R;
import com.example.ixirus.ui.BaseScreenActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CreateDevPlanActivity8 extends AppCompatActivity {
    private JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan8);
        ImageView imageView = findViewById(R.id.buttonBack);
        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(R.mipmap.ixirus_logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(imgView);
        final Button nextButton = (Button) findViewById(R.id.button);
        final TextView tv = (TextView) findViewById(R.id.textView2);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        tv.setTextSize(9 * density);

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                if (extras.getString("editedDevPlan") != null) {
                    imageView.setVisibility(View.GONE);
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    boolean selectedManager = object.getBoolean("managerCanFollow");
                    boolean selectedlecturer = object.getBoolean("lecturerCanFollow");

                    ((CheckBox) findViewById(R.id.managerCheck)).setChecked(selectedManager);
                    ((CheckBox) findViewById(R.id.educatorCheck)).setChecked(selectedlecturer);
                }
            } catch (Throwable t) {
                return;
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                final int programId;
                final int perfectionId;
                final int behaviourId;
                final String benefit;
                final int question1;
                final int question2;
                final int question3;
                final int question4;
                final int question5;
                final String planName;
                final ArrayList<Integer> actionTasks;
                final ArrayList<Integer> sourceTasks;
                final Boolean managerCheck = ((CheckBox) findViewById(R.id.managerCheck)).isChecked();
                final Boolean educatorCheck = ((CheckBox) findViewById(R.id.educatorCheck)).isChecked();
                if (object != null) { //edit butonundan gelince
                    Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);
                    try {
                        object.put("managerCanFollow", managerCheck);
                        object.put("lecturerCanFollow", educatorCheck);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                } else { //yeni ekleme
                    if (extras != null) {
                        programId = extras.getInt("programId");
                        perfectionId = extras.getInt("perfectionId");
                        behaviourId = extras.getInt("behaviourId");
                        benefit = extras.getString("benefit");
                        question1 = extras.getInt("question1");
                        question2 = extras.getInt("question2");
                        question3 = extras.getInt("question3");
                        question4 = extras.getInt("question4");
                        question5 = extras.getInt("question5");
                        planName = extras.getString("planName");
                        actionTasks = extras.getIntegerArrayList("actionTasks");
                        sourceTasks = extras.getIntegerArrayList("sourceTasks");


                        Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);

                        intent.putExtra("behaviourId", behaviourId);
                        intent.putExtra("perfectionId", perfectionId);
                        intent.putExtra("programId", programId);
                        intent.putExtra("benefit", benefit);
                        intent.putExtra("question1", question1);
                        intent.putExtra("question2", question2);
                        intent.putExtra("question3", question3);
                        intent.putExtra("question4", question4);
                        intent.putExtra("question5", question5);
                        intent.putExtra("actionTasks", actionTasks);
                        intent.putExtra("sourceTasks", sourceTasks);
                        intent.putExtra("managerCanFollow", managerCheck);
                        intent.putExtra("lecturerCanFollow", educatorCheck);
                        intent.putExtra("planName", planName);

                        if (getIntent().hasExtra("actionTasksObject"))
                            intent.putExtra("actionTasksObject", getIntent().getExtras().getString("actionTasksObject"));

                        if (getIntent().hasExtra("sourceTasksObject"))
                            intent.putExtra("sourceTasksObject", getIntent().getExtras().getString("sourceTasksObject"));


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
    }
}
