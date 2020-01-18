package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.ixirus.ListItem;
import com.example.ixirus.R;
import com.example.ixirus.ui.BaseScreenActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateDevPlanActivity1 extends AppCompatActivity {
    private ListView lv;
    private Object selectedItem = null;
    private JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan1);
        lv = findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem = lv.getItemAtPosition(position);
            }
        });

        final EditText editText = findViewById(R.id.editTextNewProgramme);
        final TextView tv = (TextView) findViewById(R.id.textView2);
        final ImageView refreshImage = (ImageView) findViewById(R.id.refreshIco);
        final Button nextButton = (Button) findViewById(R.id.button);
        final EditText editTextPlanName = (EditText) findViewById(R.id.editTextPlanName);
        ImageView imageView = findViewById(R.id.buttonBack);

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

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        tv.setTextSize(9 * density);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                if (extras.getString("editedDevPlan") != null) {
                    object = new JSONObject(extras.getString("editedDevPlan"));
                    String devPlanName = object.getString("name");
                    ((EditText) findViewById(R.id.editTextPlanName)).setText(devPlanName);
                    loadListItemFromEdit(savedToken, object);
                } else
                    loadListItem(savedToken, null, false);
            } catch (Throwable t) {
                return;
            }
        } else
            loadListItem(savedToken, null, false);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject devPlanObject = new JSONObject();
                Object selectedObj = selectedItem;
                EditText planEditText = (EditText) findViewById(R.id.editTextPlanName);

                if (planEditText.getText().toString().trim().matches("")) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.type_plan_name), Toast.LENGTH_SHORT).show();
                    return;
                } else if (selectedObj == null) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.select_item), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ListItem selectedListItem = (ListItem) selectedObj;
                    Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity2.class);
                    if (object != null)
                        intent.putExtra("editedDevPlan", object.toString());

                    intent.putExtra("programId", selectedListItem.Id);
                    intent.putExtra("planName", planEditText.getText().toString().trim());
                    startActivity(intent);
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

        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    final String currentText = editText.getText().toString().trim();
                    if (currentText.matches(""))
                        return false;
                    else {
                        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/program", new Response.Listener<String>() {
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
                                if (error.networkResponse.statusCode == 401) {
                                    Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Name", currentText);
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
                return false;
            }
        });
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public void setItemSelected(View view) {
        View rowView = view;
        View v2 = ((ViewGroup) view).getChildAt(0);
        View v3 = ((ViewGroup) v2).getChildAt(0);
        TextView txtview = (TextView) v3;

        TextView tv = (TextView) v3;
        txtview.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
    }

    public void setItemNormal() {
        for (int i = 0; i < lv.getChildCount(); i++) {
            View v = lv.getChildAt(i);

            View v2 = ((ViewGroup) v).getChildAt(0);
            View v3 = ((ViewGroup) v2).getChildAt(0);

            TextView txtview = (TextView) v3;
            txtview.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.black_overlay));
        }
    }

    public void loadListItem(final String savedToken, final String addedText, final boolean fromAddItem) {
        lv.setAdapter(null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/program", null, new Response.Listener<JSONObject>() {
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
                    final GenericListAdapter adapter = new GenericListAdapter(getBaseContext(), arr);
                    lv.setAdapter(adapter);
                    if (fromAddItem) {
                        for (int position = 0; position < adapter.getCount(); position++)
                            if (((ListItem) adapter.getItem(position)).Name.equals(addedText)) {
                                lv.setItemChecked(position, true);
                                lv.setSelection(position);
                                selectedItem = lv.getItemAtPosition(position);
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
                if (error.networkResponse.statusCode == 401) {
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
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        queue.add(request);
    }

    public void loadListItemFromEdit(final String savedToken, final JSONObject object) {
        lv.setAdapter(null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/program", null, new Response.Listener<JSONObject>() {
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
                    final GenericListAdapter adapter = new GenericListAdapter(getBaseContext(), arr);
                    lv.setAdapter(adapter);

                    Integer programId = object.getInt("programId");
                    for (int position = 0; position < adapter.getCount(); position++)
                        if (((ListItem) adapter.getItem(position)).Id == programId) {
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
                if (error.networkResponse.statusCode == 401) {
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
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        queue.add(request);
    }

    public void addProgramm(final String savedToken, String addedText) {
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        Toast.makeText(getBaseContext(), getResources().getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
        loadListItem(savedToken, addedText, true);
        lv.setItemChecked(0, true);
        findViewById(R.id.progressBar2).setVisibility(View.GONE);
    }
}
