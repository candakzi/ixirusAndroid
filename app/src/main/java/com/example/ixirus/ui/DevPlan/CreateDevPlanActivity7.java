package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ixirus.ListAdapters.GenericListAdapter;
import com.example.ixirus.ListAdapters.SourceListAdapter;
import com.example.ixirus.ListAdapters.TaskListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.ListItemSources;
import com.example.ixirus.ListItemTasks;
import com.example.ixirus.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.transform.Source;

public class CreateDevPlanActivity7 extends AppCompatActivity {
    private ListView lv;
    private Calendar myCalendar;
    private TextView dateTextView;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Object selectedSourceObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan7);
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

        final TextView tv = (TextView) findViewById(R.id.textView2);
        final ImageView refreshImage = (ImageView) findViewById(R.id.refreshIco);
        final Button nextButton = (Button) findViewById(R.id.button);
        final TextView resourceText = (TextView) findViewById(R.id.resourceText);

        dateTextView = (TextView) findViewById(R.id.editTextDate);
        myCalendar = Calendar.getInstance();
        final Button addButton = findViewById(R.id.buttonAdd);

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
        final ArrayList<ListItemTasks> arr = new ArrayList<ListItemTasks>();

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

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
                new DatePickerDialog(CreateDevPlanActivity7.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final BottomSheetDialog dialog = new BottomSheetDialog(CreateDevPlanActivity7.this);
        dialog.setContentView(R.layout.dialog_devplan_resources);
        dialog.setCancelable(false);
        final ListView sourcesListView = dialog.findViewById(R.id.listViewResources);
        loadSourceListItem(savedToken, sourcesListView);

        sourcesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                dialog.setCancelable(true);
                dialog.hide();
                Object listItem = sourcesListView.getItemAtPosition(position);
                selectedSourceObject = listItem;
                resourceText.setText(((ListItemSources) listItem).Name);
            }
        });


        resourceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskListAdapter finalAdapter = (TaskListAdapter) lv.getAdapter();
                if (finalAdapter.getCount() == 0) {
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
                    String actionTasks;

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

                        Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity8.class);

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

                        JSONArray array = new JSONArray();
                        for (int position = 0; position < finalAdapter.getCount(); position++) {
                            ListItemTasks item = (ListItemTasks) lv.getItemAtPosition(position);
                            String name = item.Name.split("-")[0].trim();
                            String myFormatPosted = "MM/dd/yy";
                            SimpleDateFormat sdfPosted = new SimpleDateFormat(myFormatPosted, Locale.US);
                            final String endDatePosted = sdfPosted.format(item.Date);
                            int sourceId = item.SourceId;
                            int id = item.Id;
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("id",id);
                                obj.put("name",name);
                                obj.put("sourceId",sourceId);
                                obj.put("endDate",endDatePosted);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            array.put(obj);
                        }
                        intent.putExtra("sourceTasks", array.toString());
                        startActivity(intent);
                    }
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                final String currentText = resourceText.getText().toString().trim();
                String myFormatAdded = "dd.MM.yyyy";
                String myFormatPosted = "MM/dd/yy";

                SimpleDateFormat sdfPosted = new SimpleDateFormat(myFormatPosted, Locale.US);
                SimpleDateFormat sdfAdded = new SimpleDateFormat(myFormatAdded, Locale.US);

                final String endDatePosted = sdfPosted.format(myCalendar.getTime());
                final String endDateAdded = sdfAdded.format(myCalendar.getTime());

                if (selectedSourceObject == null) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.select_source), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);

                    return;
                } else if (dateTextView.getText()==("")) {
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
                                item.Id = addedId;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String myFormat = "dd.MM.yyyy"; //In which you need put here
                                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                                item.Id = addedId;
                                item.Name = currentText + " - " + endDateAdded;
                                item.Date = myCalendar.getTime();
                                item.SourceId = ((ListItemSources) selectedSourceObject).Id;
                                arr.add(item);

                                final TaskListAdapter adapter = new TaskListAdapter(getBaseContext(), arr);
                                lv.setAdapter(adapter);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } catch (JSONException e) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);

                            }
                            dateTextView.setText(null);
                            resourceText.setText(null);
                            selectedSourceObject= null;

                            findViewById(R.id.refreshIco).setVisibility(View.GONE);
                            findViewById(R.id.progressBar2).setVisibility(View.GONE);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();


                            params.put("Name", "");
                            params.put("SourceId", Integer.toString(((ListItemSources) selectedSourceObject).Id));
                            params.put("EndDate", endDatePosted);

                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Authorization", "Bearer " + savedToken);
                            return headers;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    queue.add(jsonObjRequest);
                }
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateTextView.setText(sdf.format(myCalendar.getTime()));
    }

    public void loadSourceListItem(final String savedToken, final ListView sourcesLv) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/source", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<ListItemSources> arr = new ArrayList<ListItemSources>();
                try {
                    JSONArray programArray = response.getJSONArray("data");
                    for (int i = 0; i < programArray.length(); i++) {
                        JSONObject obj = programArray.getJSONObject(i);
                        ListItemSources item = new ListItemSources();
                        int sourceType = Integer.parseInt(obj.getString("sourceType"));
                        String description = obj.getString("description");
                        String name = obj.getString("name");
                        int id = Integer.parseInt(obj.getString("id"));

                        item.Id = id;
                        item.Name = name;
                        item.Description = description;
                        if (sourceType == 1)
                            item.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.pdf_icon);
                        else if (sourceType == 2)
                            item.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.video_icon);
                        else if (sourceType == 3)
                            item.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.music_icon);
                        else
                            item.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.image_icon);

                        arr.add(item);
                    }
                    final SourceListAdapter adapter = new SourceListAdapter(getBaseContext(), arr);
                    sourcesLv.setAdapter(adapter);

//                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                findViewById(R.id.progressBar2).setVisibility(View.GONE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + savedToken);
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        queue.add(request);
    }

    public void addProgramm(final String savedToken, String addedText) {
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        Toast.makeText(getBaseContext(), getResources().getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
        //loadListItem(savedToken);
        lv.setItemChecked(0, true);
        findViewById(R.id.progressBar2).setVisibility(View.GONE);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(CreateDevPlanActivity7.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(CreateDevPlanActivity7.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(CreateDevPlanActivity7.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(CreateDevPlanActivity7.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, 7);
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}