package com.example.ixirus.ui.DevPlan;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateDevPlanActivity8 extends AppCompatActivity {

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
                final String actionTasks;
                String sourceTasks;

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
                    actionTasks = extras.getString("actionTasks");
                    sourceTasks = extras.getString("sourceTasks");
                    final Boolean managerCheck = ((CheckBox) findViewById(R.id.managerCheck)).isChecked();
                    final Boolean educatorCheck = ((CheckBox) findViewById(R.id.educatorCheck)).isChecked();


//                    JsonObjectRequest jsonObjRequest = new JsonObjectRequest (Request.Method.POST, "https://ixirus.azurewebsites.net/api/developmentplan", new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject addedObject = new JSONObject(response);
//                            } catch (JSONException e) {
//                                Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
//
//                            }
//
//                            findViewById(R.id.refreshIco).setVisibility(View.GONE);
//                            findViewById(R.id.progressBar2).setVisibility(View.GONE);
//                        }
//                    }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, Object> getParams() throws AuthFailureError {
//                            Map<String, Object> params = new HashMap<String, Object>();
//                            params.put("Name", "Cagdas Test DevPlan");
//                            params.put("ProgramId", programId);
//                            params.put("BehaviorId", behaviourId);
//                            params.put("PerfectionId", perfectionId);
//                            params.put("Tasks", actionTasks);
//                            params.put("Benefit", benefit);
//                            params.put("Question1", question1);
//                            params.put("Question2", question2);
//                            params.put("Question3", question3);
//                            params.put("Question4", question4);
//                            params.put("Question5", question5);
//                            params.put("ManagerCanFollow", managerCheck);
//                            params.put("LecturerCanFollow", educatorCheck);
//
//                            return params;
//                        }
//
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            Map<String, String> headers = new HashMap<>();
//                            headers.put("Authorization", "Bearer " + savedToken);
//                            return headers;
//                        }
//                    };
//
//                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
//                    queue.add(jsonObjRequest);


                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Name", "Cagdas Test DevPlan");
                        jsonObject.put("ProgramId", programId);
                        jsonObject.put("BehaviorId", behaviourId);
                        jsonObject.put("PerfectionId", perfectionId);
                        jsonObject.put("Tasks", actionTasks);
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
                            Request.Method.POST,"https://ixirus.azurewebsites.net/api/developmentplan",jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Toast.makeText(getApplicationContext(),""+response.getString("mesaj"),Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }


                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            if(response != null && response.data != null){
                                JSONObject jsonObject = null;
                                String errorMessage = null;

                                switch(response.statusCode){
                                    case 400:
                                        errorMessage = new String(response.data);

                                        try {

                                            jsonObject = new JSONObject(errorMessage);
                                            String serverResponseMessage =  (String)jsonObject.get("hataMesaj");
                                            Toast.makeText(getApplicationContext(),""+serverResponseMessage,Toast.LENGTH_LONG).show();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                }
                            }
                        }


                    }) {


                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Authorization", "Bearer " + savedToken);
                            return headers;
                        }

                    };

                    queue.add(jsonForPutRequest);

                }
            }
        });
    }
}
