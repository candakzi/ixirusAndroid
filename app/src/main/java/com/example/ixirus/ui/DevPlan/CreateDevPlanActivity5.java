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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ixirus.ListAdapters.AnswersListAdapter;
import com.example.ixirus.ListAdapters.GenericListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateDevPlanActivity5 extends AppCompatActivity {
    private ListView lv1;
    private ListView lv2;
    private ListView lv3;
    private ListView lv4;
    private ListView lv5;

    private Object selectedItem1 = null;
    private Object selectedItem2 = null;
    private Object selectedItem3 = null;
    private Object selectedItem4 = null;
    private Object selectedItem5 = null;

    private JSONObject object;

    private int selectedPerfectionId;
    private int selectedProgramId;
    private String selectedDevPlanName;
    private int selectedBehaviourId;
    private String typedBenefit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan5);

        lv1 = findViewById(R.id.listView);

        lv1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv1.setClickable(true);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem1 = lv1.getItemAtPosition(position);
            }
        });

        lv2 = findViewById(R.id.listView2);
        lv2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv2.setClickable(true);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem2 = lv2.getItemAtPosition(position);
            }
        });

        lv3 = findViewById(R.id.listView3);
        lv3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv3.setClickable(true);
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem3 = lv3.getItemAtPosition(position);
            }
        });

        lv4 = findViewById(R.id.listView4);
        lv4.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv4.setClickable(true);
        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem4 = lv4.getItemAtPosition(position);
            }
        });

        lv5 = findViewById(R.id.listView5);
        lv5.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv5.setClickable(true);
        lv5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem5 = lv5.getItemAtPosition(position);
            }
        });

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
        loadListItems();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                selectedBehaviourId = extras.getInt("behaviourId");
                selectedPerfectionId = extras.getInt("perfectionId");
                selectedProgramId = extras.getInt("programId");
                selectedDevPlanName = extras.getString("planName");
                typedBenefit = extras.getString("benefit");

                if (extras.getString("editedDevPlan") != null) { //edit modda açılmıştır
                    imageView.setVisibility(View.GONE);
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    Integer question1 = object.getInt("question1");
                    Integer question2 = object.getInt("question2");
                    Integer question3 = object.getInt("question3");
                    Integer question4 = object.getInt("question4");
                    Integer question5 = object.getInt("question5");

                    selectedItem1 = lv1.getItemAtPosition(question1 - 1);
                    selectedItem2 = lv2.getItemAtPosition(question2 - 1);
                    selectedItem3 = lv3.getItemAtPosition(question3 - 1);
                    selectedItem4 = lv4.getItemAtPosition(question4 - 1);
                    selectedItem5 = lv5.getItemAtPosition(question5 - 1);

                    lv1.setSelection(question1 - 1);
                    lv1.setItemChecked(question1 - 1, true);

                    lv2.setSelection(question2 - 1);
                    lv2.setItemChecked(question2 - 1, true);

                    lv3.setSelection(question3 - 1);
                    lv3.setItemChecked(question3 - 1, true);

                    lv4.setSelection(question4 - 1);
                    lv4.setItemChecked(question4 - 1, true);

                    lv5.setSelection(question5 - 1);
                    lv5.setItemChecked(question5 - 1, true);

                } else {
                    if (getIntent().hasExtra("question1")) {
                        lv1.setSelection(getIntent().getExtras().getInt("question1") - 1);
                        lv1.setItemChecked(getIntent().getExtras().getInt("question1") - 1, true);
                    }
                    if (getIntent().hasExtra("question2")) {
                        lv2.setSelection(getIntent().getExtras().getInt("question2") - 1);
                        lv2.setItemChecked(getIntent().getExtras().getInt("question2") - 1, true);
                    }
                    if (getIntent().hasExtra("question3")) {
                        lv3.setSelection(getIntent().getExtras().getInt("question3") - 1);
                        lv3.setItemChecked(getIntent().getExtras().getInt("question3") - 1, true);
                    }
                    if (getIntent().hasExtra("question4")) {
                        lv4.setSelection(getIntent().getExtras().getInt("question4") - 1);
                        lv4.setItemChecked(getIntent().getExtras().getInt("question4") - 1, true);
                    }
                    if (getIntent().hasExtra("question5")) {
                        lv5.setSelection(getIntent().getExtras().getInt("question5") - 1);
                        lv5.setItemChecked(getIntent().getExtras().getInt("question5") - 1, true);
                    }

                    selectedItem1 = lv1.getItemAtPosition(getIntent().getExtras().getInt("question1") - 1);
                    selectedItem2 = lv2.getItemAtPosition(getIntent().getExtras().getInt("question2") - 1);
                    selectedItem3 = lv3.getItemAtPosition(getIntent().getExtras().getInt("question3") - 1);
                    selectedItem4 = lv4.getItemAtPosition(getIntent().getExtras().getInt("question4") - 1);
                    selectedItem5 = lv5.getItemAtPosition(getIntent().getExtras().getInt("question5") - 1);
                }

            } catch (Throwable t) {
                return;
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity4.class);
                intent.putExtra("programId", selectedProgramId);
                intent.putExtra("planName", selectedDevPlanName);
                intent.putExtra("perfectionId", selectedPerfectionId);
                intent.putExtra("behaviourId", selectedBehaviourId);
                intent.putExtra("benefit", typedBenefit);

                if (object != null)
                    intent.putExtra("editedDevPlan", object.toString());

                if (selectedItem1 != null)
                    intent.putExtra("question1", ((ListItem) selectedItem1).Id);
                if (selectedItem2 != null)
                    intent.putExtra("question2", ((ListItem) selectedItem2).Id);
                if (selectedItem3 != null)
                    intent.putExtra("question3", ((ListItem) selectedItem3).Id);
                if (selectedItem4 != null)
                    intent.putExtra("question4", ((ListItem) selectedItem4).Id);
                if (selectedItem5 != null)
                    intent.putExtra("question5", ((ListItem) selectedItem5).Id);

                startActivity(intent);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
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

                    if (selectedItem1 == null || selectedItem2 == null || selectedItem3 == null || selectedItem4 == null || selectedItem5 == null) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.answer_the_questions), Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        if (object != null) {
                            Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);
                            try {
                                object.put("question1", ((ListItem) selectedItem1).Id);
                                object.put("question2", ((ListItem) selectedItem2).Id);
                                object.put("question3", ((ListItem) selectedItem3).Id);
                                object.put("question4", ((ListItem) selectedItem4).Id);
                                object.put("question5", ((ListItem) selectedItem5).Id);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (getIntent().hasExtra("fromEdit"))
                                intent.putExtra("fromEdit", true);
                            intent.putExtra("editedDevPlan", object.toString());
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(getBaseContext(), CreateDevPlanQuestionsSummary.class);
                            intent.putExtra("behaviourId", behaviourId);
                            intent.putExtra("perfectionId", perfectionId);
                            intent.putExtra("programId", programId);
                            intent.putExtra("benefit", benefit);
                            intent.putExtra("question1", ((ListItem) selectedItem1).Id);
                            intent.putExtra("question2", ((ListItem) selectedItem2).Id);
                            intent.putExtra("question3", ((ListItem) selectedItem3).Id);
                            intent.putExtra("question4", ((ListItem) selectedItem4).Id);
                            intent.putExtra("question5", ((ListItem) selectedItem5).Id);
                            intent.putExtra("planName", planName);

                            startActivity(intent);
                        }
                    }
                }
            }
        });


    }

    public void loadListItems() {
        ArrayList<ListItem> arr = new ArrayList<ListItem>();
        String[] answers = new String[]{getResources().getString(R.string.answer_1), getResources().getString(R.string.answer_2), getResources().getString(R.string.answer_3)};
        for (int i = 0; i < answers.length; i++) {
            ListItem item = new ListItem();
            item.Id = i + 1;
            item.Name = answers[i];
            arr.add(item);
        }
        AnswersListAdapter adapter = new AnswersListAdapter(getBaseContext(), arr);
        lv1.setAdapter(adapter);
        lv2.setAdapter(adapter);
        lv3.setAdapter(adapter);
        lv4.setAdapter(adapter);
        lv5.setAdapter(adapter);

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
            Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity4.class);
            intent.putExtra("programId", selectedProgramId);
            intent.putExtra("planName", selectedDevPlanName);
            intent.putExtra("perfectionId", selectedPerfectionId);
            intent.putExtra("behaviourId", selectedBehaviourId);
            intent.putExtra("benefit", typedBenefit);

            if (object != null)
                intent.putExtra("editedDevPlan", object.toString());

            if (selectedItem1 != null)
                intent.putExtra("question1", ((ListItem) selectedItem1).Id);
            if (selectedItem2 != null)
                intent.putExtra("question2", ((ListItem) selectedItem2).Id);
            if (selectedItem3 != null)
                intent.putExtra("question3", ((ListItem) selectedItem3).Id);
            if (selectedItem4 != null)
                intent.putExtra("question4", ((ListItem) selectedItem4).Id);
            if (selectedItem5 != null)
                intent.putExtra("question5", ((ListItem) selectedItem5).Id);

            startActivity(intent);
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }
}

