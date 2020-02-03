package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ixirus.ListAdapters.AnswersListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class DevPlanPreviewActivity extends AppCompatActivity {
    private ListView lv1;
    private ListView lv2;
    private ListView lv3;
    private ListView lv4;
    private ListView lv5;

    private JSONObject object;

    private int programId;
    private int perfectionId;
    private int behaviourId;
    private String benefit;
    private int question1;
    private int question2;
    private int question3;
    private int question4;
    private int question5;
    private String planName;
    private ArrayList<Integer> actionTasks;
    private ArrayList<Integer> sourceTasks;
    private Boolean managerCheck;
    private Boolean educatorCheck;

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
                if (getIntent().hasExtra("fromEdit")) {
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
                    managerCheck = extras.getBoolean("managerCanFollow");
                    educatorCheck = extras.getBoolean("lecturerCanFollow");

                    object = new JSONObject();
                    object.put("programId", programId);
                    object.put("name", planName);
                    object.put("perfectionId", perfectionId);
                    object.put("behaviourId", behaviourId);
                    object.put("benefit", benefit);
                    object.put("question1", question1);
                    object.put("question2", question2);
                    object.put("question3", question3);
                    object.put("question4", question4);
                    object.put("question5", question5);
                    object.put("question5", question5);
                    object.put("actionTasks", actionTasks);
                    object.put("sourceTasks", sourceTasks);
                    object.put("managerCanFollow", managerCheck);
                    object.put("lecturerCanFollow", educatorCheck);

                    textDevPlanName.setText(planName);
                    textProgramName.setText(programId);
                    textPerfection.setText(perfectionId);
                    textBehaviour.setText(behaviourId);
                    textBenefit.setText(benefit);

                    if (getIntent().hasExtra("question1")) {
                        loadListItem(getIntent().getExtras().getInt("question1") - 1, lv1);
                        lv1.setSelection(0);
                        lv1.setItemChecked(0, true);
                    }
                    if (getIntent().hasExtra("question2")) {
                        loadListItem(getIntent().getExtras().getInt("question2") - 1, lv2);
                        lv2.setSelection(0);
                        lv2.setItemChecked(0, true);
                    }
                    if (getIntent().hasExtra("question3")) {
                        loadListItem(getIntent().getExtras().getInt("question3") - 1, lv3);
                        lv3.setSelection(0);
                        lv3.setItemChecked(0, true);
                    }
                    if (getIntent().hasExtra("question4")) {
                        loadListItem(getIntent().getExtras().getInt("question4") - 1, lv4);
                        lv4.setSelection(0);
                        lv4.setItemChecked(0, true);
                    }
                    if (getIntent().hasExtra("question5")) {
                        loadListItem(getIntent().getExtras().getInt("question5") - 1, lv5);
                        lv5.setSelection(0);
                        lv5.setItemChecked(0, true);
                    }
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

        Button nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                int programId;
                int perfectionId;
                int behaviourId;
                String benefit;
                String planName;

                if (extras != null) {
                    programId = extras.getInt("programId");
                    perfectionId = extras.getInt("perfectionId");
                    behaviourId = extras.getInt("behaviourId");
                    benefit = extras.getString("benefit");
                    planName = extras.getString("planName");
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity6.class);
                    if (object != null)
                        intent.putExtra("editedDevPlan", object.toString());

                    intent.putExtra("behaviourId", behaviourId);
                    intent.putExtra("perfectionId", perfectionId);
                    intent.putExtra("programId", programId);
                    intent.putExtra("benefit", benefit);
                    intent.putExtra("question1", getIntent().getExtras().getInt("question1"));
                    intent.putExtra("question2", getIntent().getExtras().getInt("question2"));
                    intent.putExtra("question3", getIntent().getExtras().getInt("question3"));
                    intent.putExtra("question4", getIntent().getExtras().getInt("question4"));
                    intent.putExtra("question5", getIntent().getExtras().getInt("question5"));
                    intent.putExtra("planName", planName);

                    startActivity(intent);
                }
            }
        });

        textDevPlanName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (textDevPlanName.getRight() - textDevPlanName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (object != null) {
                            Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity1.class);
                            if (getIntent().hasExtra("fromEdit"))
                                intent.putExtra("fromEdit", true);

                            intent.putExtra("editedDevPlan", object.toString());
                            startActivity(intent);
                        }

                        return true;
                    }
                }
                return false;
            }
        });

        textProgramName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (textProgramName.getRight() - textProgramName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity1.class);
                        if (getIntent().hasExtra("fromEdit"))
                            intent.putExtra("fromEdit", true);
                        intent.putExtra("editedDevPlan", object.toString());
                        startActivity(intent);

                        return true;
                    }
                }
                return false;
            }
        });

        textPerfection.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (textPerfection.getRight() - textPerfection.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity2.class);
                        if (getIntent().hasExtra("fromEdit"))
                            intent.putExtra("fromEdit", true);
                        intent.putExtra("editedDevPlan", object.toString());
                        startActivity(intent);

                        return true;
                    }
                }
                return false;
            }
        });

        textBehaviour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (textBehaviour.getRight() - textBehaviour.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity3.class);
                        if (getIntent().hasExtra("fromEdit"))
                            intent.putExtra("fromEdit", true);
                        intent.putExtra("editedDevPlan", object.toString());
                        startActivity(intent);

                        return true;
                    }
                }
                return false;
            }
        });

        textBenefit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (textBenefit.getRight() - textBenefit.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity4.class);
                        if (getIntent().hasExtra("fromEdit"))
                            intent.putExtra("fromEdit", true);
                        intent.putExtra("editedDevPlan", object.toString());
                        startActivity(intent);

                        return true;
                    }
                }
                return false;
            }
        });

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
}
