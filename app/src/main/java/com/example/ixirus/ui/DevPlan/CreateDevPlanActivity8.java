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
        final Button saveButton = (Button) findViewById(R.id.button);
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
                if(extras.getString("editedDevPlan")!=null) {
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    boolean  selectedManager = object.getBoolean("managerCanFollow");
                    boolean  selectedlecturer = object.getBoolean("lecturerCanFollow");

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

        saveButton.setOnClickListener(new View.OnClickListener() {
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
                if (extras != null) {
                    if (extras.getString("editedDevPlan") != null) {
                        try {
                            int devPlanId = object.getInt("id");
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
                            final Boolean managerCheck = ((CheckBox) findViewById(R.id.managerCheck)).isChecked();
                            final Boolean educatorCheck = ((CheckBox) findViewById(R.id.educatorCheck)).isChecked();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                ArrayList<Integer> joinedList = new ArrayList<Integer>();
                                joinedList.addAll(actionTasks);
                                joinedList.addAll(sourceTasks);
                                JSONArray arr = new JSONArray();
                                for (int i = 0; i < joinedList.toArray().length; i++) {
                                    arr.put(joinedList.toArray()[i]);
                                }
                                jsonObject.put("Id", devPlanId);
                                jsonObject.put("Name", planName);
                                jsonObject.put("ProgramId", programId);
                                jsonObject.put("BehaviorId", behaviourId);
                                jsonObject.put("PerfectionId", perfectionId);
                                jsonObject.put("Tasks", arr);
                                jsonObject.put("Benefit", benefit);
                                jsonObject.put("Question1", question1);
                                jsonObject.put("Question2", question2);
                                jsonObject.put("Question3", question3);
                                jsonObject.put("Question4", question4);
                                jsonObject.put("Question5", question5);
                                jsonObject.put("ManagerCanFollow", managerCheck);
                                jsonObject.put("LecturerCanFollow", educatorCheck);

                            } catch (JSONException e) {
                                // handle exception
                            }

                            RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                            JsonObjectRequest jsonForPutRequest = new JsonObjectRequest(
                                    Request.Method.PUT, "https://ixirus.azurewebsites.net/api/developmentplan", jsonObject,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.plan_saved_successfully) + "\n" + planName, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getBaseContext(), MyDevPlanListActivity.class);
                                            startActivity(intent);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if (error.networkResponse.statusCode == 401) {
                                        Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                                        startActivity(intent);
                                    } else
                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> headers = new HashMap<>();
                                    headers.put("Authorization", "Bearer " + savedToken);
                                    return headers;
                                }

                                @Override
                                public String getBodyContentType() {
                                    // TODO Auto-generated method stub
                                    return "application/json";
                                }
                            };
                            queue.add(jsonForPutRequest);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    } else {
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
                        final Boolean managerCheck = ((CheckBox) findViewById(R.id.managerCheck)).isChecked();
                        final Boolean educatorCheck = ((CheckBox) findViewById(R.id.educatorCheck)).isChecked();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            ArrayList<Integer> joinedList = new ArrayList<Integer>();
                            joinedList.addAll(actionTasks);
                            joinedList.addAll(sourceTasks);
                            JSONArray arr = new JSONArray();
                            for (int i = 0; i < joinedList.toArray().length; i++) {
                                arr.put(joinedList.toArray()[i]);
                            }

                            jsonObject.put("Name", planName);
                            jsonObject.put("ProgramId", programId);
                            jsonObject.put("BehaviorId", behaviourId);
                            jsonObject.put("PerfectionId", perfectionId);
                            jsonObject.put("Tasks", arr);
                            jsonObject.put("Benefit", benefit);
                            jsonObject.put("Question1", question1);
                            jsonObject.put("Question2", question2);
                            jsonObject.put("Question3", question3);
                            jsonObject.put("Question4", question4);
                            jsonObject.put("Question5", question5);
                            jsonObject.put("ManagerCanFollow", managerCheck);
                            jsonObject.put("LecturerCanFollow", educatorCheck);

                        } catch (JSONException e) {
                            // handle exception
                        }

                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                        JsonObjectRequest jsonForPutRequest = new JsonObjectRequest(
                                Request.Method.POST, "https://ixirus.azurewebsites.net/api/developmentplan", jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.plan_saved_successfully) + "\n" + planName, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getBaseContext(), MyDevPlanListActivity.class);
                                        startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.networkResponse.statusCode == 401) {
                                    Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Authorization", "Bearer " + savedToken);
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                // TODO Auto-generated method stub
                                return "application/json";
                            }
                        };
                        queue.add(jsonForPutRequest);
                    }
                }
            }
        });
    }
}
