package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.ixirus.ListAdapters.SourceListAdapter;
import com.example.ixirus.ListAdapters.TaskListAdapter;
import com.example.ixirus.ListItemSources;
import com.example.ixirus.ListItemTasks;
import com.example.ixirus.R;
import com.example.ixirus.ui.BaseScreenActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateDevPlanActivity7 extends AppCompatActivity {
    private ListView lv;
    private Calendar myCalendar;
    private TextView dateTextView;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Object selectedSourceObject;
    private JSONObject object;
    private ArrayList<ListItemTasks> arr;
    private BottomSheetDialog dialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan7);
        ImageView imageView = findViewById(R.id.buttonBack);
        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan);

        lv = findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_NONE);
        lv.setClickable(true);


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
        arr = new ArrayList<ListItemTasks>();

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                long viewId = arg1.getId();
                final Object selectedItem = lv.getItemAtPosition(position);
                final String selectedId = Integer.toString(((ListItemTasks) selectedItem).Id);

                if (viewId == R.id.deleteImage) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateDevPlanActivity7.this);
                    builder.setTitle(getString(R.string.source_step_delete));
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
                                    if (error.networkResponse.statusCode == 401) {
                                        Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
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

        dialog = new BottomSheetDialog(CreateDevPlanActivity7.this);
        dialog.setContentView(R.layout.dialog_devplan_resources);
        //dialog.setCancelable(false);
        final ListView sourcesListView = (ListView) dialog.findViewById(R.id.listViewResources);

        sourcesListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        sourcesListView.setClickable(true);

        sourcesListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow NestedScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow NestedScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        final TabLayout tabLayout = (TabLayout) dialog.findViewById(R.id.tabs);

        loadSourceListItem(savedToken, sourcesListView, 0);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                if (extras.getString("editedDevPlan") != null) {
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    JSONArray selectedSourceTasks = object.getJSONArray("sourceTasks");
                    loadDefaultListItemFromEdit(selectedSourceTasks);
                } else {
                    loadDefaultListItem();

                }
            } catch (Throwable t) {
                return;
            }
        }

        sourcesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                long viewId = arg1.getId();
                final Object selectedItem = sourcesListView.getItemAtPosition(position);
                final int sourceType = ((ListItemSources) selectedItem).SourceType;
                final String sourceUrl = ((ListItemSources) selectedItem).Url;

                if (viewId == R.id.buttonPreview) {
                    String extension = MimeTypeMap.getFileExtensionFromUrl(sourceUrl);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                    mediaIntent.setDataAndType(Uri.parse(sourceUrl), mimeType);
                    if (mediaIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mediaIntent);
                    }
                }
                else if(viewId == R.id.buttonSelectSource){

                    dialog.hide();
                    Object listItem = sourcesListView.getItemAtPosition(position);
                    selectedSourceObject = listItem;
                    resourceText.setText(((ListItemSources) listItem).Name);
                }
            }
        });

        resourceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadSourceListItem(savedToken, sourcesListView, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskListAdapter finalAdapter = (TaskListAdapter) lv.getAdapter();
                if (finalAdapter.getCount() == 0) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.add_source_step), Toast.LENGTH_SHORT).show();
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
                    final ArrayList<Integer> actionTasks;

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

                        Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity8.class);

                        if (object != null)
                            intent.putExtra("editedDevPlan", object.toString());

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
                        intent.putExtra("planName", planName);

                        JSONArray array = new JSONArray();
                        ArrayList<Integer> list = new ArrayList<Integer>();
                        for (int position = 0; position < finalAdapter.getCount(); position++) {
                            ListItemTasks item = (ListItemTasks) lv.getItemAtPosition(position);
                            if (item.SourceId == 0)
                                continue;
                            int id = item.Id;
                            list.add(id);
                        }
                        if (list.toArray().length == 0)
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.add_source_step), Toast.LENGTH_SHORT).show();
                        else {
                            intent.putExtra("sourceTasks", list);
                            startActivity(intent);
                        }
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
                dialog.findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                final String currentText = resourceText.getText().toString().trim();
                String myFormatAdded = "dd.MM.yyyy";
                String myFormatPosted = "MM/dd/yy";

                SimpleDateFormat sdfPosted = new SimpleDateFormat(myFormatPosted, Locale.US);
                SimpleDateFormat sdfAdded = new SimpleDateFormat(myFormatAdded, Locale.US);

                final String endDatePosted = sdfPosted.format(myCalendar.getTime());
                final String endDateAdded = sdfAdded.format(myCalendar.getTime());

                if (selectedSourceObject == null) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.add_source_step), Toast.LENGTH_SHORT).show();
                    dialog.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                    return;
                } else if (dateTextView.getText() == ("")) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.select_date), Toast.LENGTH_SHORT).show();
                    dialog.findViewById(R.id.progressBar2).setVisibility(View.GONE);

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
                                dialog.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } catch (JSONException e) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
                                dialog.findViewById(R.id.progressBar2).setVisibility(View.GONE);

                            }
                            dateTextView.setText(null);
                            resourceText.setText(null);
                            selectedSourceObject = null;

                            dialog.findViewById(R.id.refreshIco).setVisibility(View.GONE);
                            dialog.findViewById(R.id.progressBar2).setVisibility(View.GONE);
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

    public void loadSourceListItem(final String savedToken, final ListView sourcesLv, int tabPosition) {
        sourcesLv.setAdapter(null);
        dialog.findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        int selectedBehaviourId = getIntent().getExtras().getInt("behaviourId");
        String url = tabPosition == 0 ? "https://ixirus.azurewebsites.net/api/source?behaviorId=" + Integer.toString(selectedBehaviourId) : "https://ixirus.azurewebsites.net/api/source";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
                        String url = obj.getString("fileUrl");

                        String name = obj.getString("name");
                        int id = Integer.parseInt(obj.getString("id"));

                        item.Id = id;
                        item.Name = name;
                        item.Description = description;
                        item.SourceType = sourceType;
                        item.Url = url;
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

                    dialog.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                    dialog.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 401) {
                    Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                    dialog.findViewById(R.id.progressBar2).setVisibility(View.GONE);
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

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        queue.add(request);
    }

    private void loadDefaultListItemFromEdit(JSONArray selectedActionTasks) {

        if (getIntent().hasExtra("passedActionList")) {
            ArrayList<ListItemTasks> passedTasks = new ArrayList<ListItemTasks>();
            passedTasks = (ArrayList<ListItemTasks>) getIntent().getSerializableExtra("passedActionList");

            for (int position = 0; position < passedTasks.toArray().length; position++) {
                ListItemTasks current = (ListItemTasks) passedTasks.toArray()[position];

                ListItemTasks item = new ListItemTasks();
                String myFormat = "dd.MM.yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                final String currentDateStr = dateFormat.format(current.Date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                item.Id = current.Id;
                item.Name = current.Name;
                item.Date = current.Date;
                item.SourceId = current.SourceId;
                arr.add(item);
            }
        }


        for (int position = 0; position < selectedActionTasks.length(); position++) {
            try {
                JSONObject obj = selectedActionTasks.getJSONObject(position);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String myFormat = "dd.MM.yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);


                int id = obj.getInt("id");
                String name = obj.getString("name");
                String endDateStr = obj.getString("endDate");
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

        final TaskListAdapter adapter = new TaskListAdapter(getBaseContext(), arr);
        lv.setAdapter(adapter);
    }

    private void loadDefaultListItem() {

        if (getIntent().hasExtra("passedActionList")) {
            ArrayList<ListItemTasks> passedTasks = new ArrayList<ListItemTasks>();
            passedTasks = (ArrayList<ListItemTasks>) getIntent().getSerializableExtra("passedActionList");

            for (int position = 0; position < passedTasks.toArray().length; position++) {
                ListItemTasks current = (ListItemTasks) passedTasks.toArray()[position];

                ListItemTasks item = new ListItemTasks();
                String myFormat = "dd.MM.yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                final String currentDateStr = dateFormat.format(current.Date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                item.Id = current.Id;
                item.Name = current.Name;
                item.Date = current.Date;
                item.SourceId = current.SourceId;
                arr.add(item);
            }
        }

        final TaskListAdapter adapter = new TaskListAdapter(getBaseContext(), arr);
        lv.setAdapter(adapter);
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

    public boolean canScrollVertically (AbsListView view) {
        boolean canScroll = false;

        if (view !=null && view.getChildCount ()> 0) {
            boolean isOnTop = view.getFirstVisiblePosition() != 0 || view.getChildAt(0).getTop() != 0;
            boolean isAllItemsVisible = isOnTop && view.getLastVisiblePosition() == view.getChildCount();

            if (isOnTop || isAllItemsVisible) {
                canScroll = true;
            }
        }

        return  canScroll;
    }
}
