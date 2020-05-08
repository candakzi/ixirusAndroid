package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.ixirus.LanguageHelper;
import com.example.ixirus.ListAdapters.TaskListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.ListItemTasks;
import com.example.ixirus.R;
import com.example.ixirus.ui.BaseScreenActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateDevPlanActivity6 extends AppCompatActivity {
    private ListView lv;
    private Calendar myCalendar;
    private TextView dateTextView;
    private JSONObject object;
    private ArrayList<ListItemTasks> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan6);
        ImageView imageView = findViewById(R.id.buttonBack);
        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan);

        lv = findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_NONE);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

            }
        });

        final EditText editText = findViewById(R.id.editTextNewBehaviour);
        final TextView tv = (TextView) findViewById(R.id.textView2);
        final ImageView refreshImage = (ImageView) findViewById(R.id.refreshIco);
        final Button nextButton = (Button) findViewById(R.id.button);
        dateTextView = (TextView) findViewById(R.id.editTextDate);
        myCalendar = Calendar.getInstance();
        final Button addButton = findViewById(R.id.buttonAdd);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateDevPlanActivity6.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final View activityRootView = findViewById(R.id.rootView);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > dpToPx(getBaseContext(), 200)) { // if more than 200 dp, it's probably a keyboard...
                    nextButton.setVisibility(View.GONE);
                } else
                    nextButton.setVisibility(View.VISIBLE);
            }
        });

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
        tv.setTextSize(9 * density);

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        arr = new ArrayList<ListItemTasks>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                if (extras.getString("editedDevPlan") != null) {
                    imageView.setVisibility(View.GONE);
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    JSONArray selectedActionTasks = object.getJSONArray("actionTasks");
                    loadDefaultListItem(selectedActionTasks);

                }
            } catch (Throwable t) {
                return;
            }
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                long viewId = arg1.getId();
                final Object selectedItem = lv.getItemAtPosition(position);
                final String selectedId = Integer.toString(((ListItemTasks) selectedItem).Id);

                if (viewId == R.id.deleteImage) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateDevPlanActivity6.this);
                    builder.setTitle(getString(R.string.action_step_delete));
                    builder.setMessage(getString(R.string.are_you_sure));

                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            StringRequest jsonObjRequest = new StringRequest(Request.Method.DELETE, "https://ixirus.azurewebsites.net/api/task?taskId=" + selectedId, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    arr.remove(position);
                                    ((TaskListAdapter) lv.getAdapter()).notifyDataSetChanged();
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
                            };

                            RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                            queue.add(jsonObjRequest);
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                final String currentText = editText.getText().toString().trim();
                String myFormatAdded = "dd.MM.yyyy";
                String myFormatPosted = "MM/dd/yy";

                SimpleDateFormat sdfPosted = new SimpleDateFormat(myFormatPosted, Locale.US);
                SimpleDateFormat sdfAdded = new SimpleDateFormat(myFormatAdded, Locale.US);

                final String endDatePosted = sdfPosted.format(myCalendar.getTime());
                final String endDateAdded = sdfAdded.format(myCalendar.getTime());

                if (currentText.matches("")) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.type_name), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);

                    return;
                } else if (dateTextView.getText() == "") {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.select_date), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);

                } else {
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/task", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject addedObject = new JSONObject(response);
                                int addedId = Integer.parseInt(addedObject.getString("data"));

                                ListItemTasks item = new ListItemTasks();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String myFormat = "dd.MM.yyyy"; //In which you need put here
                                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                                item.Id = addedId;
                                item.Name = currentText + " - " + endDateAdded;
                                item.Date = myCalendar.getTime();
                                item.SourceId = 0;
                                arr.add(item);

                                final TaskListAdapter adapter = new TaskListAdapter(getBaseContext(), arr);
                                lv.setAdapter(adapter);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } catch (JSONException e) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);

                            }
                            dateTextView.setText(null);
                            editText.getText().clear();

                            findViewById(R.id.refreshIco).setVisibility(View.GONE);
                            findViewById(R.id.progressBar2).setVisibility(View.GONE);
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
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();


                            params.put("Name", currentText);
                            params.put("SourceId", "0");
                            params.put("EndDate", endDatePosted);

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
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskListAdapter finalAdapter = (TaskListAdapter) lv.getAdapter();
                if (finalAdapter == null) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.add_action_step), Toast.LENGTH_SHORT).show();
                    return;
                } else if (finalAdapter.getCount() == 0) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.add_action_step), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Bundle extras = getIntent().getExtras();
                    int programId;
                    int perfectionId;
                    int behaviourId;
                    String benefit;
                    int question1;
                    int question2;
                    int question3;
                    int question4;
                    int question5;
                    String planName;

                    if (extras != null) {
                        if (object != null) {
                            Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);
                            try {
                                JSONArray newArr = new JSONArray();

                                for (int position = 0; position < finalAdapter.getCount(); position++) {
                                    JSONObject actionTaskObject = new JSONObject();

                                    ListItemTasks item = (ListItemTasks) lv.getItemAtPosition(position);
                                    String name = item.Name.split("-")[0].trim();
                                    actionTaskObject.put("id", item.Id);
                                    actionTaskObject.put("name", name);
                                    actionTaskObject.put("sourceId", null);
                                    String myFormatPosted = "yyyy-MM-dd";
                                    SimpleDateFormat sdfPosted = new SimpleDateFormat(myFormatPosted, Locale.US);
                                    final String endDatePosted = sdfPosted.format(item.Date);


                                    actionTaskObject.put("endDate", endDatePosted);
                                    newArr.put(actionTaskObject);
                                }
                                object.put("actionTasks", newArr);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (getIntent().hasExtra("fromEdit"))
                                intent.putExtra("fromEdit", true);
                            intent.putExtra("editedDevPlan", object.toString());
                            startActivity(intent);
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

                            Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity7.class);
                            intent.putExtra("behaviourId", behaviourId);
                            intent.putExtra("perfectionId", perfectionId);
                            intent.putExtra("programId", programId);
                            intent.putExtra("benefit", benefit);
                            intent.putExtra("question1", question1);
                            intent.putExtra("question2", question2);
                            intent.putExtra("question3", question3);
                            intent.putExtra("question4", question4);
                            intent.putExtra("question5", question5);
                            intent.putExtra("planName", planName);

                            if (getIntent().hasExtra("program"))
                                intent.putExtra("program", getIntent().getExtras().getString("program"));

                            if (getIntent().hasExtra("perfection"))
                                intent.putExtra("perfection", getIntent().getExtras().getString("perfection"));

                            if (getIntent().hasExtra("behavior"))
                                intent.putExtra("behavior", getIntent().getExtras().getString("behavior"));

                            JSONArray array = new JSONArray();
                            ArrayList<Integer> list = new ArrayList<Integer>();
                            ArrayList<ListItemTasks> passedActionList = new ArrayList<ListItemTasks>();

                            for (int position = 0; position < finalAdapter.getCount(); position++) {
                                ListItemTasks item = (ListItemTasks) lv.getItemAtPosition(position);
                                String name = item.Name.split("-")[0].trim();
                                String myFormatPosted = "yyy/MM/dd";
                                SimpleDateFormat sdfPosted = new SimpleDateFormat(myFormatPosted, Locale.US);
                                final String endDatePosted = sdfPosted.format(item.Date);
                                int sourceId = item.SourceId;
                                int id = item.Id;

                                list.add(id);
                                passedActionList.add(item);
                            }

                            JSONArray newArr = new JSONArray();
                            try {
                                for (int position = 0; position < finalAdapter.getCount(); position++) {
                                    JSONObject actionTaskObject = new JSONObject();

                                    ListItemTasks item = (ListItemTasks) lv.getItemAtPosition(position);
                                    String name = item.Name.split("-")[0].trim();

                                    actionTaskObject.put("id", item.Id);
                                    actionTaskObject.put("name", name);
                                    actionTaskObject.put("sourceId", null);
                                    String myFormatPosted = "yyyy-MM-dd";
                                    SimpleDateFormat sdfPosted = new SimpleDateFormat(myFormatPosted, Locale.US);
                                    final String endDatePosted = sdfPosted.format(item.Date);


                                    actionTaskObject.put("endDate", endDatePosted);
                                    newArr.put(actionTaskObject);

                                }
                                intent.putExtra("actionTasksObject", newArr.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            intent.putExtra("actionTasks", list);
                            intent.putExtra("passedActionList", passedActionList);

                            startActivity(intent);

                        }
                    }
                }
            }
        });
    }

    private void loadDefaultListItem(JSONArray selectedActionTasks) {
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

        final TaskListAdapter adapter = new TaskListAdapter(getBaseContext(), arr);
        lv.setAdapter(adapter);
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    private void updateLabel() {
        String myFormat = "dd.MM.yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateTextView.setText(sdf.format(myCalendar.getTime()));
    }
}
