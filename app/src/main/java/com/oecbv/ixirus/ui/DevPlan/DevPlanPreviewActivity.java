package com.oecbv.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oecbv.ixirus.FeedbackListItem;
import com.oecbv.ixirus.LanguageHelper;
import com.oecbv.ixirus.ListAdapters.AnswersListAdapter;
import com.oecbv.ixirus.ListAdapters.FeedbackListAdapter;
import com.oecbv.ixirus.ListAdapters.TaskListAdapterPreview;
import com.oecbv.ixirus.ListItem;
import com.oecbv.ixirus.ListItemTasks;
import com.oecbv.ixirus.R;
import com.oecbv.ixirus.ui.BaseScreenActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DevPlanPreviewActivity extends AppCompatActivity {
    private ListView lv1;
    private ListView lv2;
    private ListView lv3;
    private ListView lv4;
    private ListView lv5;
    private ListView lvAction;
    private ListView lvSource;
    private Button buttonEdit;

    private BottomSheetDialog bsDialogRate;
    private BottomSheetDialog bsDialogShowFeedbacks;

    private RatingBar rbar;
    private EditText editTextRate;
    private JSONObject object;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_plan_preview);

        final TextView textDevPlanName = findViewById(R.id.textViewDevPlanName);
        final TextView textProgramName = findViewById(R.id.textViewSelectedProgram);
        final TextView textPerfection = findViewById(R.id.textViewSelectedPerfection);
        final TextView textBehaviour = findViewById(R.id.textViewSelectedBehaviour);
        final TextView textBenefit = findViewById(R.id.textViewSelectedBenefits);
        final TextView textQuiz = findViewById(R.id.textViewQuiz);
        final TextView textViewManager = findViewById(R.id.textViewManager);
        final TextView textViewLecturer = findViewById(R.id.textViewInstructor);

        final Button devPlanButton = findViewById(R.id.buttonDevPlan);
        final Button programButton = findViewById(R.id.buttonProgram);
        final Button perfectionButton = findViewById(R.id.buttonPerfection);
        final Button behviorButton = findViewById(R.id.buttonBehavior);
        final Button benefitsButton = findViewById(R.id.buttonBenefits);
        final Button quizButton = findViewById(R.id.buttonQuiz);
        final Button actionsButton = findViewById(R.id.buttonActionStep);
        final Button sourcesButton = findViewById(R.id.buttonSourceStep);
        final Button managerButton = findViewById(R.id.buttonManager);
        final Button instructorButton = findViewById(R.id.buttonInstructor);
        Button nextButton = (Button) findViewById(R.id.button);

        final Button buttonRate = findViewById(R.id.buttonRate);
        final Button buttonShowFeedbacks = findViewById(R.id.buttonShowFeedbacks);
        final Button buttonExperience = findViewById(R.id.buttonExperienceSharing);

        final Button buttonEdit = findViewById(R.id.buttonEdit);


        lv1 = findViewById(R.id.listView);

        lv1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv1.setClickable(false);


        lv2 = findViewById(R.id.listView2);
        lv2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv2.setClickable(false);

        lv3 = findViewById(R.id.listView3);
        lv3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv3.setClickable(false);

        lv4 = findViewById(R.id.listView4);
        lv4.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv4.setClickable(false);


        lv5 = findViewById(R.id.listView5);
        lv5.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv5.setClickable(false);

        lvAction = findViewById(R.id.listViewActionStep);
        lvAction.setChoiceMode(ListView.CHOICE_MODE_NONE);
        lvAction.setClickable(true);

        lvSource = findViewById(R.id.listViewSourceStep);
        lvSource.setChoiceMode(ListView.CHOICE_MODE_NONE);
        lvSource.setClickable(true);

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
        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);


        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialogRate.show();
            }
        });

        buttonShowFeedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialogShowFeedbacks = new BottomSheetDialog(DevPlanPreviewActivity.this);
                bsDialogShowFeedbacks.setContentView(R.layout.activity_feedback_list_popup);
                //bsDialogShowFeedbacks.getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan);
                bsDialogShowFeedbacks.show();
                loadFeedbackListItems(savedToken);
            }
        });

        bsDialogRate = new BottomSheetDialog(DevPlanPreviewActivity.this);
        bsDialogRate.setContentView(R.layout.dialog_rate);



        editTextRate = (EditText) bsDialogRate.findViewById(R.id.editText);
        Button buttonbsRate = (Button) bsDialogRate.findViewById(R.id.button);
        rbar = (RatingBar) bsDialogRate.findViewById(R.id.rating);
        TextView txt1 = (TextView) bsDialogRate.findViewById(R.id.textView);
        TextView txt2 = (TextView) bsDialogRate.findViewById(R.id.textView2);


        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = bsDialogRate.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = bsDialogRate.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        rbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = bsDialogRate.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        buttonbsRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final int planId = object.getInt("id");
                    final int ratingVal = (int) rbar.getRating();
                    final String currentMesageText = editTextRate.getText().toString().trim();

                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/rating", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.rating_added), Toast.LENGTH_SHORT).show();
                            bsDialogRate.dismiss();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NetworkError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NoConnectionError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
                            } else if (error.networkResponse.statusCode == 401) {
                                Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("DevPlanId", Integer.toString(planId));
                            params.put("Message", currentMesageText);
                            params.put("Point", Integer.toString(ratingVal));

                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Authorization", "Bearer " + savedToken);
                            headers.put("langType", new LanguageHelper().getLanguage());
                            return headers;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    queue.add(jsonObjRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                if (!getIntent().hasExtra("fromEdit")) {
                    imageView.setVisibility(View.GONE);
                }

                if (getIntent().hasExtra("summary")) {
                    devPlanButton.setVisibility(View.GONE);
                    programButton.setVisibility(View.GONE);
                    perfectionButton.setVisibility(View.GONE);
                    behviorButton.setVisibility(View.GONE);
                    benefitsButton.setVisibility(View.GONE);
                    quizButton.setVisibility(View.GONE);
                    actionsButton.setVisibility(View.GONE);
                    sourcesButton.setVisibility(View.GONE);
                    managerButton.setVisibility(View.GONE);
                    instructorButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                    buttonEdit.setVisibility(View.VISIBLE);
                    String devPlanId = Integer.toString(new JSONObject(extras.getString("editedDevPlan")).getInt("id"));
                    loadRateItems(savedToken, devPlanId);
                } else {
                    buttonRate.setVisibility(View.GONE);
                    buttonShowFeedbacks.setVisibility(View.GONE);
                    buttonExperience.setVisibility(View.GONE);
                    buttonEdit.setVisibility(View.GONE);

                }

                if (getIntent().hasExtra("editedDevPlan")) {
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    String devPlanName = object.getString("name");
                    JSONObject programIdObj = object.getJSONObject("program");
                    JSONObject perfectionIdObj = object.getJSONObject("perfection");
                    JSONObject behaviorIdObj = object.getJSONObject("behavior");
                    String benefitObj = object.getString("benefit");

                    int question1Obj = object.getInt("question1");
                    int question2Obj = object.getInt("question2");
                    int question3Obj = object.getInt("question3");
                    int question4Obj = object.getInt("question4");
                    int question5Obj = object.getInt("question5");
                    JSONArray selectedActionTasks = object.getJSONArray("actionTasks");
                    JSONArray selectedSourceTask = object.getJSONArray("sourceTasks");
                    boolean managerCanFollow = object.getBoolean("managerCanFollow");
                    boolean lecturerCanFollow = object.getBoolean("lecturerCanFollow");

                    textDevPlanName.setText(devPlanName);
                    textProgramName.setText(programIdObj.getString("name"));
                    textPerfection.setText(perfectionIdObj.getString("name"));
                    textBehaviour.setText(behaviorIdObj.getString("name"));
                    textBenefit.setText(benefitObj);

                    loadListItem(question1Obj - 1, lv1);
                    lv1.setSelection(0);
                    lv1.setItemChecked(0, true);


                    loadListItem(question2Obj - 1, lv2);
                    lv2.setSelection(0);
                    lv2.setItemChecked(0, true);


                    loadListItem(question3Obj - 1, lv3);
                    lv3.setSelection(0);
                    lv3.setItemChecked(0, true);


                    loadListItem(question4Obj - 1, lv4);
                    lv4.setSelection(0);
                    lv4.setItemChecked(0, true);


                    loadListItem(question5Obj - 1, lv5);
                    lv5.setSelection(0);
                    lv5.setItemChecked(0, true);

                    loadActonListItem(selectedActionTasks);
                    loadSourceItem(selectedSourceTask);

                    if (managerCanFollow)
                        textViewManager.setText(getResources().getString(R.string.yes));
                    else
                        textViewManager.setText(getResources().getString(R.string.no));

                    if (lecturerCanFollow)
                        textViewLecturer.setText(getResources().getString(R.string.yes));
                    else
                        textViewLecturer.setText(getResources().getString(R.string.no));
                } else {  // Buraya ilk defa yeni ekleden gelirse düşecek
                    String programObject = extras.getString("program");
                    String perfectionObject = extras.getString("perfection");
                    String behaviorObject = extras.getString("behavior");

                    String benefit = extras.getString("benefit");
                    int question1 = extras.getInt("question1");
                    int question2 = extras.getInt("question2");
                    int question3 = extras.getInt("question3");
                    int question4 = extras.getInt("question4");
                    int question5 = extras.getInt("question5");
                    String planName = extras.getString("planName");
                    JSONArray actionTasks = new JSONArray(extras.getString("actionTasksObject"));
                    JSONArray sourceTasks = new JSONArray(extras.getString("sourceTasksObject"));
                    Boolean managerCheck = extras.getBoolean("managerCanFollow");
                    Boolean educatorCheck = extras.getBoolean("lecturerCanFollow");

                    object = new JSONObject();
                    object.put("program", new JSONObject(programObject));
                    object.put("name", planName);
                    object.put("perfection", new JSONObject(perfectionObject));
                    object.put("behavior", new JSONObject(behaviorObject));
                    object.put("benefit", benefit);
                    object.put("question1", question1);
                    object.put("question2", question2);
                    object.put("question3", question3);
                    object.put("question4", question4);
                    object.put("question5", question5);
                    object.put("actionTasks", actionTasks);
                    object.put("sourceTasks", sourceTasks);
                    object.put("managerCanFollow", managerCheck);
                    object.put("lecturerCanFollow", educatorCheck);

                    String devPlanName = object.getString("name");
                    JSONObject programIdObj = object.getJSONObject("program");
                    JSONObject perfectionIdObj = object.getJSONObject("perfection");
                    JSONObject behaviorIdObj = object.getJSONObject("behavior");
                    String benefitObj = object.getString("benefit");

                    int question1Obj = object.getInt("question1");
                    int question2Obj = object.getInt("question2");
                    int question3Obj = object.getInt("question3");
                    int question4Obj = object.getInt("question4");
                    int question5Obj = object.getInt("question5");
                    JSONArray selectedActionTasks = object.getJSONArray("actionTasks");
                    JSONArray selectedSourceTask = object.getJSONArray("sourceTasks");
                    boolean managerCanFollow = object.getBoolean("managerCanFollow");
                    boolean lecturerCanFollow = object.getBoolean("lecturerCanFollow");

                    textDevPlanName.setText(devPlanName);
                    textProgramName.setText(programIdObj.getString("name"));
                    textPerfection.setText(perfectionIdObj.getString("name"));
                    textBehaviour.setText(behaviorIdObj.getString("name"));
                    textBenefit.setText(benefitObj);

                    loadListItem(question1Obj - 1, lv1);
                    lv1.setSelection(0);
                    lv1.setItemChecked(0, true);


                    loadListItem(question2Obj - 1, lv2);
                    lv2.setSelection(0);
                    lv2.setItemChecked(0, true);


                    loadListItem(question3Obj - 1, lv3);
                    lv3.setSelection(0);
                    lv3.setItemChecked(0, true);


                    loadListItem(question4Obj - 1, lv4);
                    lv4.setSelection(0);
                    lv4.setItemChecked(0, true);


                    loadListItem(question5Obj - 1, lv5);
                    lv5.setSelection(0);
                    lv5.setItemChecked(0, true);

                    loadActonListItem(selectedActionTasks);
                    loadSourceItem(selectedSourceTask);

                    if (managerCanFollow)
                        textViewManager.setText(getResources().getString(R.string.yes));
                    else
                        textViewManager.setText(getResources().getString(R.string.no));

                    if (lecturerCanFollow)
                        textViewLecturer.setText(getResources().getString(R.string.yes));
                    else
                        textViewLecturer.setText(getResources().getString(R.string.no));
                }

            } catch (Throwable t) {
                return;
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MyDevPlanListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);
                intent.putExtra("editedDevPlan", object.toString());
                intent.putExtra("fromEdit", true);
                startActivity(intent);
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().hasExtra("fromEdit")) {//editlenen obje
                    try {
                        JSONObject jsonObject = new JSONObject();

                        String devPlanId = object.getString("id");
                        final String devPlanName = object.getString("name");
                        JSONObject programIdObj = object.getJSONObject("program");
                        JSONObject perfectionIdObj = object.getJSONObject("perfection");
                        JSONObject behaviorIdObj = object.getJSONObject("behavior");

                        int programId = programIdObj.getInt("id");
                        int perfectionId = perfectionIdObj.getInt("id");
                        int behaviorId = behaviorIdObj.getInt("id");

                        String benefitObj = object.getString("benefit");
                        int question1Obj = object.getInt("question1");
                        int question2Obj = object.getInt("question2");
                        int question3Obj = object.getInt("question3");
                        int question4Obj = object.getInt("question4");
                        int question5Obj = object.getInt("question5");
                        JSONArray selectedActionTasks = object.getJSONArray("actionTasks");
                        JSONArray selectedSourceTask = object.getJSONArray("sourceTasks");
                        boolean managerCanFollow = object.getBoolean("managerCanFollow");
                        boolean lecturerCanFollow = object.getBoolean("lecturerCanFollow");

                        JSONArray arr = new JSONArray();
                        for (int i = 0; i < selectedActionTasks.length(); i++) {
                            JSONObject obj = selectedActionTasks.getJSONObject(i);
                            int id = obj.getInt("id");
                            arr.put(id);
                        }

                        for (int i = 0; i < selectedSourceTask.length(); i++) {
                            JSONObject obj = selectedSourceTask.getJSONObject(i);
                            int id = obj.getInt("id");
                            arr.put(id);
                        }

                        jsonObject.put("Id", devPlanId);
                        jsonObject.put("Name", devPlanName);
                        jsonObject.put("ProgramId", programId);
                        jsonObject.put("BehaviorId", behaviorId);
                        jsonObject.put("PerfectionId", perfectionId);
                        jsonObject.put("Tasks", arr);
                        jsonObject.put("Benefit", benefitObj);
                        jsonObject.put("Question1", question1Obj);
                        jsonObject.put("Question2", question2Obj);
                        jsonObject.put("Question3", question3Obj);
                        jsonObject.put("Question4", question4Obj);
                        jsonObject.put("Question5", question5Obj);
                        jsonObject.put("ManagerCanFollow", managerCanFollow);
                        jsonObject.put("LecturerCanFollow", lecturerCanFollow);

                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                        JsonObjectRequest jsonForPutRequest = new JsonObjectRequest(
                                Request.Method.PUT, "https://ixirus.azurewebsites.net/api/developmentplan", jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.plan_updated_successfully) + "\n" + devPlanName, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getBaseContext(), MyDevPlanListActivity.class);
                                        startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof NetworkError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ServerError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ParseError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                } else if (error instanceof NoConnectionError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                                } else if (error instanceof TimeoutError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                                } else if (error.networkResponse.statusCode == 401) {
                                    Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Authorization", "Bearer " + savedToken);
                                headers.put("langType", new LanguageHelper().getLanguage());
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
                } else { //yeni kayıt

                    try {
                        JSONObject jsonObject = new JSONObject();

                        final String devPlanName = object.getString("name");
                        JSONObject programIdObj = object.getJSONObject("program");
                        JSONObject perfectionIdObj = object.getJSONObject("perfection");
                        JSONObject behaviorIdObj = object.getJSONObject("behavior");

                        int programId = programIdObj.getInt("id");
                        int perfectionId = perfectionIdObj.getInt("id");
                        int behaviorId = behaviorIdObj.getInt("id");

                        String benefitObj = object.getString("benefit");
                        int question1Obj = object.getInt("question1");
                        int question2Obj = object.getInt("question2");
                        int question3Obj = object.getInt("question3");
                        int question4Obj = object.getInt("question4");
                        int question5Obj = object.getInt("question5");
                        JSONArray selectedActionTasks = object.getJSONArray("actionTasks");
                        JSONArray selectedSourceTask = object.getJSONArray("sourceTasks");
                        boolean managerCanFollow = object.getBoolean("managerCanFollow");
                        boolean lecturerCanFollow = object.getBoolean("lecturerCanFollow");

                        JSONArray arr = new JSONArray();
                        for (int i = 0; i < selectedActionTasks.length(); i++) {
                            JSONObject obj = selectedActionTasks.getJSONObject(i);
                            int id = obj.getInt("id");
                            arr.put(id);
                        }

                        for (int i = 0; i < selectedSourceTask.length(); i++) {
                            JSONObject obj = selectedSourceTask.getJSONObject(i);
                            int id = obj.getInt("id");
                            arr.put(id);
                        }

                        jsonObject.put("Name", devPlanName);
                        jsonObject.put("ProgramId", programId);
                        jsonObject.put("BehaviorId", behaviorId);
                        jsonObject.put("PerfectionId", perfectionId);
                        jsonObject.put("Tasks", arr);
                        jsonObject.put("Benefit", benefitObj);
                        jsonObject.put("Question1", question1Obj);
                        jsonObject.put("Question2", question2Obj);
                        jsonObject.put("Question3", question3Obj);
                        jsonObject.put("Question4", question4Obj);
                        jsonObject.put("Question5", question5Obj);
                        jsonObject.put("ManagerCanFollow", managerCanFollow);
                        jsonObject.put("LecturerCanFollow", lecturerCanFollow);

                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                        JsonObjectRequest jsonForPutRequest = new JsonObjectRequest(
                                Request.Method.POST, "https://ixirus.azurewebsites.net/api/developmentplan", jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.plan_saved_successfully) + "\n" + devPlanName, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getBaseContext(), MyDevPlanListActivity.class);
                                        startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof NetworkError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                                } else if (error instanceof ServerError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                                } else if (error instanceof ParseError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                } else if (error instanceof NoConnectionError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                                } else if (error instanceof TimeoutError) {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                                } else if (error.networkResponse.statusCode == 401) {
                                    Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Authorization", "Bearer " + savedToken);
                                headers.put("langType", new LanguageHelper().getLanguage());
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
                }
            }
        });


        devPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity1.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                }
            }
        });


        programButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity1.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                }
            }
        });

        perfectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity2.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                }
            }
        });

        behviorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity3.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                }
            }
        });

        benefitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity4.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                }
            }
        });

        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity5.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                }
            }
        });

        actionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity6.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                }
            }
        });
        sourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity7.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);
                    //Source edit tıklandığında aksiyon listesindekileri getirmek için
                    ArrayList<ListItemTasks> passedActionList = new ArrayList<ListItemTasks>();
                    TaskListAdapterPreview actionListAdapter = (TaskListAdapterPreview) lvAction.getAdapter();

                    for (int position = 0; position < actionListAdapter.getCount(); position++) {
                        ListItemTasks item = (ListItemTasks) lvAction.getItemAtPosition(position);
                        passedActionList.add(item);
                    }


                    intent.putExtra("editedDevPlan", object.toString());
                    intent.putExtra("passedActionList", passedActionList);
                    startActivity(intent);
                }
            }
        });

        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity8.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                }
            }
        });

        instructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity8.class);
                    if (getIntent().hasExtra("fromEdit"))
                        intent.putExtra("fromEdit", true);

                    intent.putExtra("editedDevPlan", object.toString());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), MyDevPlanListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);
    }

    public void loadListItem(int index, ListView lv) {
        ArrayList<ListItem> arr = new ArrayList<ListItem>();
        String answer = index == 0 ? getResources().getString(R.string.answer_1) : index == 1 ? getResources().getString(R.string.answer_2) : getResources().getString(R.string.answer_3);
        ListItem item = new ListItem();
        item.Id = index + 1;
        item.Name = answer;
        arr.add(item);
        AnswersListAdapter adapter = new AnswersListAdapter(getBaseContext(), arr);
        lv.setAdapter(adapter);
        lv.setSelection(0);
        lv.setItemChecked(0, true);

    }

    private void loadActonListItem(JSONArray selectedActionTasks) {
        ArrayList<ListItemTasks> arr = new ArrayList<>();
        float dip = 42f;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        CardView actionCard = findViewById(R.id.cardViewListActionStep);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) actionCard.getLayoutParams();
        lp.height = selectedActionTasks.length() * Math.round(px);
        actionCard.setLayoutParams(lp);

        for (int position = 0; position < selectedActionTasks.length(); position++) {
            try {
                JSONObject obj = selectedActionTasks.getJSONObject(position);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String myFormat = "dd.MM.yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);

                int id = obj.getInt("id");
                String name = obj.getString("name");
                String endDateStr = obj.getString("endDate");
                Date d = sdf.parse(endDateStr);
                ListItemTasks item = new ListItemTasks();
                final String currentDateStr = dateFormat.format(d);

                item.Id = id;
                item.Name = name + " - " + currentDateStr;
                item.Date = d;
                item.SourceId = 0;
                arr.add(item);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        final TaskListAdapterPreview adapter = new TaskListAdapterPreview(getBaseContext(), arr);
        lvAction.setAdapter(adapter);
    }

    private void loadSourceItem(JSONArray selectedActionTasks) {
        float dip = 42f;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );

        CardView sourceCard = findViewById(R.id.cardViewListSourceStep);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) sourceCard.getLayoutParams();
        lp.height = selectedActionTasks.length() * Math.round(px);
        sourceCard.setLayoutParams(lp);

        ArrayList<ListItemTasks> arr = new ArrayList<>();
        for (int position = 0; position < selectedActionTasks.length(); position++) {
            try {
                JSONObject obj = selectedActionTasks.getJSONObject(position);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String myFormat = "dd.MM.yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);

                int id = obj.getInt("id");
                String name = obj.getString("name");
                String endDateStr = obj.getString("endDate");
                if (!obj.has("sourceId"))
                    continue;

                int sourceId = obj.getInt("sourceId");

                Date d = sdf.parse(endDateStr);
                ListItemTasks item = new ListItemTasks();
                final String currentDateStr = dateFormat.format(d);

                item.Id = id;
                item.Name = name + " - " + currentDateStr;
                item.Date = d;
                item.SourceId = sourceId;
                arr.add(item);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        final TaskListAdapterPreview adapter = new TaskListAdapterPreview(getBaseContext(), arr);
        lvSource.setAdapter(adapter);
    }

    public void loadRateItems(final String savedToken, String devPlanId) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/task?devPlanId=" + devPlanId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONObject rating = response.getJSONObject("data").getJSONObject("rating");
                    Integer point = rating.getInt("point");
                    String message = rating.getString("message");
                    rbar.setRating((float) point);
                    if (!message.equals("null"))
                        editTextRate.setText(message);

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 401) {
                    Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + savedToken);
                headers.put("langType", new LanguageHelper().getLanguage());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        queue.add(request);
    }

    public void loadFeedbackListItems(final String savedToken) {
        final ListView lvFeedbacks =  bsDialogShowFeedbacks.findViewById(R.id.listView);
        lvFeedbacks.setAdapter(null);
        String devPlanId = null;
        try {
            devPlanId = object.getString("id");

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/feedback?devPlanId=" + devPlanId, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ArrayList<FeedbackListItem> arr = new ArrayList<FeedbackListItem>();
                    try {
                        JSONArray programArray = response.getJSONArray("data");
                        for (int i = 0; i < programArray.length(); i++) {
                            JSONObject obj = programArray.getJSONObject(i);
                            FeedbackListItem item = new FeedbackListItem();
                            item.Email = obj.getString("email");
                            item.Message = obj.getString("message").equals("null") ? "-" : obj.getString("message");
                            item.Response = obj.getString("response").equals("null") ? "-" : obj.getString("response");

                            arr.add(item);
                        }
                        final FeedbackListAdapter adapter = new FeedbackListAdapter(getBaseContext(), arr);
                        lvFeedbacks.setAdapter(adapter);

                       bsDialogShowFeedbacks.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                    } catch (JSONException e) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                        bsDialogShowFeedbacks.findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                        bsDialogShowFeedbacks.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        bsDialogShowFeedbacks.findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                        bsDialogShowFeedbacks.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        bsDialogShowFeedbacks.findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                        bsDialogShowFeedbacks.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        bsDialogShowFeedbacks.findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                        bsDialogShowFeedbacks.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
                        bsDialogShowFeedbacks.findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                        bsDialogShowFeedbacks.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                    } else if (error.networkResponse.statusCode == 401) {
                        Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                        bsDialogShowFeedbacks.findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                        bsDialogShowFeedbacks.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + savedToken);
                    headers.put("langType", new LanguageHelper().getLanguage());
                    return headers;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(getBaseContext());
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
