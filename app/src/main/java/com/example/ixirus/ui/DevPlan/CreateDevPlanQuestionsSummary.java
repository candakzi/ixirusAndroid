package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ixirus.ListAdapters.AnswersListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class CreateDevPlanQuestionsSummary extends AppCompatActivity {
    private ListView lv1;
    private ListView lv2;
    private ListView lv3;
    private ListView lv4;
    private ListView lv5;

    private JSONObject object;

    private int selectedPerfectionId;
    private int selectedProgramId;
    private String selectedDevPlanName;
    private int selectedBehaviourId;
    private String typedBenefit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan_questions_summary);

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
                if (extras.getString("editedDevPlan") != null)
                    object = new JSONObject(extras.getString("editedDevPlan"));

                selectedBehaviourId = extras.getInt("behaviourId");
                selectedPerfectionId = extras.getInt("perfectionId");
                selectedProgramId = extras.getInt("programId");
                selectedDevPlanName = extras.getString("planName");
                typedBenefit = extras.getString("benefit");

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
                    intent.putExtra("question1", getIntent().getExtras().getInt("question1") - 1);
                    intent.putExtra("question2", getIntent().getExtras().getInt("question2") - 1);
                    intent.putExtra("question3", getIntent().getExtras().getInt("question3") - 1);
                    intent.putExtra("question4", getIntent().getExtras().getInt("question4") - 1);
                    intent.putExtra("question5", getIntent().getExtras().getInt("question5") - 1);
                    intent.putExtra("planName", planName);

                    startActivity(intent);
                }
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
        lv.setItemChecked(0,true);

    }
}

