package com.oecbv.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.oecbv.ixirus.LanguageHelper;
import com.oecbv.ixirus.ListAdapters.BehaviorListAdapter;
import com.oecbv.ixirus.ListItem;
import com.oecbv.ixirus.R;
import com.oecbv.ixirus.ui.BaseScreenActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateDevPlanActivity3 extends AppCompatActivity {
    private ListView lv;
    private Object selectedItem = null;
    private JSONObject object;
    private int selectedPerfectionId;
    private int selectedProgramId;

    private String selectedDevPlanName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan3);
        ImageView imageView = findViewById(R.id.buttonBack);
        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan);

        lv = findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setClickable(true);


        final EditText editText = findViewById(R.id.editTextNewBehaviour);
        final TextView tv = (TextView) findViewById(R.id.textView2);
        final ImageView refreshImage = (ImageView) findViewById(R.id.refreshIco);
        final Button nextButton = (Button) findViewById(R.id.button);
        final Button addButton = findViewById(R.id.buttonAdd);
        final Button cancelButton = findViewById(R.id.buttonCancel);
        final Button updateButton = findViewById(R.id.buttonUpdate);

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

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                selectedPerfectionId = extras.getInt("perfectionId");
                selectedProgramId = extras.getInt("programId");
                selectedDevPlanName = extras.getString("planName");

                if (extras.getString("editedDevPlan") != null) {
                    imageView.setVisibility(View.GONE);//geri tuşu inaktif
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    loadListItemFromEdit(savedToken, object);
                } else
                    loadListItem(savedToken, null, false);
            } catch (Throwable t) {
                return;
            }
        } else
            loadListItem(savedToken, null, false);

        float density = getResources().getDisplayMetrics().density;
        tv.setTextSize(9 * density);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                int programId;
                int perfectionId;
                String planName;
                if (extras != null) {
                    programId = extras.getInt("programId");
                    perfectionId = extras.getInt("perfectionId");
                    planName = extras.getString("planName");

                    JSONObject devPlanObject = new JSONObject();
                    Object selectedObj = selectedItem;
                    if (selectedObj == null) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.select_item), Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        ListItem selectedListItem = (ListItem) selectedObj;

                        if (object != null) {
                            Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);
                            try {
                                JSONObject behviorObj = new JSONObject();
                                behviorObj.put("id", selectedListItem.Id);
                                behviorObj.put("name", selectedListItem.Name);

                                object.put("behavior", behviorObj);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (getIntent().hasExtra("fromEdit"))
                                intent.putExtra("fromEdit", true);

                            intent.putExtra("editedDevPlan", object.toString());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity4.class);
                            if (getIntent().hasExtra("benefit"))
                                intent.putExtra("benefit", getIntent().getExtras().getString("benefit"));

                            try {
                                JSONObject behviorObj = new JSONObject();
                                behviorObj.put("id", selectedListItem.Id);
                                behviorObj.put("name", selectedListItem.Name);

                                intent.putExtra("behaviourId", selectedListItem.Id);
                                intent.putExtra("perfectionId", perfectionId);
                                intent.putExtra("programId", programId);
                                intent.putExtra("planName", planName);
                                intent.putExtra("behavior", behviorObj.toString());

                                if (getIntent().hasExtra("program"))
                                    intent.putExtra("program", getIntent().getExtras().getString("program"));

                                if (getIntent().hasExtra("perfection"))
                                    intent.putExtra("perfection", getIntent().getExtras().getString("perfection"));

                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshImage.setVisibility(View.GONE);
                findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                loadListItem(savedToken, null, false);

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                long viewId = arg1.getId();
                selectedItem = lv.getItemAtPosition(position);
                final String selectedId = Integer.toString(((ListItem) selectedItem).Id);
                final String selectedText = ((ListItem) selectedItem).Name;

                if (viewId == R.id.editImage) {
                    addButton.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.VISIBLE);
                    updateButton.setVisibility(View.VISIBLE);
                    editText.setText(selectedText);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity2.class);
                if (selectedItem != null) {
                    intent.putExtra("behaviourId", ((ListItem) selectedItem).Id);
                    JSONObject behaviorObject = new JSONObject();
                    try {
                        behaviorObject.put("id", ((ListItem) selectedItem).Id);
                        behaviorObject.put("name", ((ListItem) selectedItem).Name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("behavior", behaviorObject.toString());
                }

                if (object != null) {
                    if (selectedItem != null) {
                        try {
                            object.put("behaviorId", ((ListItem) selectedItem).Id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    intent.putExtra("editedDevPlan", object.toString());
                } else {

                    if (getIntent().hasExtra("program"))
                        intent.putExtra("program", getIntent().getExtras().getString("program"));

                    if (getIntent().hasExtra("perfection"))
                        intent.putExtra("perfection", getIntent().getExtras().getString("perfection"));

                    if (getIntent().hasExtra("benefit"))
                        intent.putExtra("benefit", getIntent().getExtras().getString("benefit")); // 5 6 7 8 komple gelecek

                    if (getIntent().hasExtra("question1"))
                        intent.putExtra("question1", getIntent().getExtras().getInt("question1"));  // 5 6 7 8 komple gelecek

                    if (getIntent().hasExtra("question2"))
                        intent.putExtra("question2", getIntent().getExtras().getInt("question2"));

                    if (getIntent().hasExtra("question3"))
                        intent.putExtra("question3", getIntent().getExtras().getInt("question3"));

                    if (getIntent().hasExtra("question4"))
                        intent.putExtra("question4", getIntent().getExtras().getInt("question4"));

                    if (getIntent().hasExtra("question5"))
                        intent.putExtra("question5", getIntent().getExtras().getInt("question5"));

                    intent.putExtra("perfectionId", selectedPerfectionId);
                    intent.putExtra("programId", selectedProgramId);

                    intent.putExtra("planName", selectedDevPlanName);

                    startActivity(intent);
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentText = editText.getText().toString().trim();
                if (currentText.matches(""))
                    return;
                else {
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/behavior", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            addProgramm(savedToken, currentText);
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
                            params.put("PerfectionId", Integer.toString(selectedPerfectionId));

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

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentText = editText.getText().toString().trim();
                final int selectedBehaviorId = ((ListItem) selectedItem).Id;
                if (currentText.matches(""))
                    return;
                else {
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.PUT, "https://ixirus.azurewebsites.net/api/behavior", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            addProgramm(savedToken, currentText);
                            editText.getText().clear();
                            addButton.setVisibility(View.VISIBLE);
                            cancelButton.setVisibility(View.GONE);
                            updateButton.setVisibility(View.GONE);
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
                            params.put("Id", Integer.toString(selectedBehaviorId));

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

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText().clear();
                addButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.GONE);
                updateButton.setVisibility(View.GONE);
            }
        });

    }

    public void loadListItem(final String savedToken, final String addedText, final boolean fromAddItem) {
        lv.setAdapter(null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/behavior?perfectionId=" + selectedPerfectionId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<ListItem> arr = new ArrayList<ListItem>();
                try {
                    JSONArray programArray = response.getJSONArray("data");
                    for (int i = 0; i < programArray.length(); i++) {
                        JSONObject obj = programArray.getJSONObject(i);
                        ListItem item = new ListItem();
                        item.Id = Integer.parseInt(obj.getString("id"));
                        item.Name = obj.getString("name");
                        arr.add(item);
                    }
                    final BehaviorListAdapter adapter = new BehaviorListAdapter(getBaseContext(), arr);
                    lv.setAdapter(adapter);
                    if (fromAddItem) {
                        for (int position = 0; position < adapter.getCount(); position++)
                            if (((ListItem) adapter.getItem(position)).Name.equals(addedText)) {
                                lv.setItemChecked(position, true);
                                lv.setSelection(position);
                                selectedItem = lv.getItemAtPosition(position);
                            }
                    } else {
                        Bundle extras = getIntent().getExtras();
                        if (extras != null) {
                            if (getIntent().hasExtra("behaviourId")) {
                                int OverridedBehaviourIddInt = extras.getInt("behaviourId");
                                for (int position = 0; position < adapter.getCount(); position++)
                                    if (((ListItem) adapter.getItem(position)).Id == OverridedBehaviourIddInt) {
                                        lv.setItemChecked(position, true);
                                        lv.setSelection(position);
                                        selectedItem = lv.getItemAtPosition(position);
                                    }
                            }
                        }
                    }
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                }
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
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
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

    public void loadListItemFromEdit(final String savedToken, final JSONObject object) {
        lv.setAdapter(null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/behavior?perfectionId=" + selectedPerfectionId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<ListItem> arr = new ArrayList<ListItem>();
                try {
                    JSONArray programArray = response.getJSONArray("data");
                    for (int i = 0; i < programArray.length(); i++) {
                        JSONObject obj = programArray.getJSONObject(i);
                        ListItem item = new ListItem();
                        item.Id = Integer.parseInt(obj.getString("id"));
                        item.Name = obj.getString("name");
                        arr.add(item);
                    }
                    final BehaviorListAdapter adapter = new BehaviorListAdapter(getBaseContext(), arr);
                    lv.setAdapter(adapter);

                    JSONObject behavior = object.getJSONObject("behavior");
                    Integer behaviorId = behavior.getInt("id");
                    for (int position = 0; position < adapter.getCount(); position++)
                        if (((ListItem) adapter.getItem(position)).Id == behaviorId) {
                            lv.setItemChecked(position, true);
                            lv.setSelection(position);
                            selectedItem = lv.getItemAtPosition(position);
                        }

                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                }
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
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
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

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public void addProgramm(final String savedToken, String addedText) {
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        Toast.makeText(getBaseContext(), getResources().getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
        loadListItem(savedToken, addedText, true);
        lv.setItemChecked(0, true);
        findViewById(R.id.progressBar2).setVisibility(View.GONE);
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
            Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity2.class);
            if (selectedItem != null) {
                intent.putExtra("behaviourId", ((ListItem) selectedItem).Id);
                JSONObject behaviorObject = new JSONObject();
                try {
                    behaviorObject.put("id", ((ListItem) selectedItem).Id);
                    behaviorObject.put("name", ((ListItem) selectedItem).Name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("behavior", behaviorObject.toString());
            }

            if (getIntent().hasExtra("program"))
                intent.putExtra("program", getIntent().getExtras().getString("program"));

            if (getIntent().hasExtra("perfection"))
                intent.putExtra("perfection", getIntent().getExtras().getString("perfection"));

            if (getIntent().hasExtra("benefit"))
                intent.putExtra("benefit", getIntent().getExtras().getString("benefit")); // 5 6 7 8 komple gelecek

            if (getIntent().hasExtra("question1"))
                intent.putExtra("question1", getIntent().getExtras().getInt("question1"));  // 5 6 7 8 komple gelecek

            if (getIntent().hasExtra("question2"))
                intent.putExtra("question2", getIntent().getExtras().getInt("question2"));

            if (getIntent().hasExtra("question3"))
                intent.putExtra("question3", getIntent().getExtras().getInt("question3"));

            if (getIntent().hasExtra("question4"))
                intent.putExtra("question4", getIntent().getExtras().getInt("question4"));

            if (getIntent().hasExtra("question5"))
                intent.putExtra("question5", getIntent().getExtras().getInt("question5"));

            intent.putExtra("perfectionId", selectedPerfectionId);
            intent.putExtra("programId", selectedProgramId);

            intent.putExtra("planName", selectedDevPlanName);

            startActivity(intent);
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }
}
