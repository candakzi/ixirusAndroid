package com.example.ixirus.ui.Disc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.ixirus.ListAdapters.AnswersListAdapter;
import com.example.ixirus.ListAdapters.DiscQuestionsListAdapter;
import com.example.ixirus.ListAdapters.GenericListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.ListItemDiscQuestions;
import com.example.ixirus.R;
import com.example.ixirus.ui.BaseScreenActivity;
import com.example.ixirus.ui.DevPlan.CreateDevPlanActivity4;
import com.example.ixirus.ui.DevPlan.CreateDevPlanQuestionsSummary;
import com.example.ixirus.ui.DevPlan.DevPlanPreviewActivity;
import com.example.ixirus.ui.DevPlan.MyDevPlanListActivity;
import com.example.ixirus.ui.DevPlan.WaitingCompletedActionsActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DiscQuestionsActivity extends AppCompatActivity {

    private ListView lv1;
    private ListView lv2;
    private ListView lv3;
    private ListView lv4;
    private ListView lv5;
    private ListView lv6;
    private ListView lv7;
    private ListView lv8;
    private ListView lv9;
    private ListView lv10;

    private Object selectedItem1 = null;
    private Object selectedItem2 = null;
    private Object selectedItem3 = null;
    private Object selectedItem4 = null;
    private Object selectedItem5 = null;
    private Object selectedItem6 = null;
    private Object selectedItem7 = null;
    private Object selectedItem8 = null;
    private Object selectedItem9 = null;
    private Object selectedItem10 = null;

    private BottomSheetDialog bsDialog;
    private JSONObject object;
    private String _discInfoTitle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disc_questions);

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


        lv6 = findViewById(R.id.listView6);
        lv6.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv6.setClickable(true);
        lv6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem6 = lv6.getItemAtPosition(position);
            }
        });


        lv7 = findViewById(R.id.listView7);
        lv7.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv7.setClickable(true);
        lv7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem7 = lv7.getItemAtPosition(position);
            }
        });

        lv8 = findViewById(R.id.listView8);
        lv8.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv8.setClickable(true);
        lv8.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem8 = lv8.getItemAtPosition(position);
            }
        });


        lv9 = findViewById(R.id.listView9);
        lv9.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv9.setClickable(true);
        lv9.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem9 = lv9.getItemAtPosition(position);
            }
        });

        lv10 = findViewById(R.id.listView10);
        lv10.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv10.setClickable(true);
        lv10.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem10 = lv10.getItemAtPosition(position);
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

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        float density = getResources().getDisplayMetrics().density;

        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setTextSize(9 * density);
        try {
            loadListItems(savedToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

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
                try {
                    int langId = 0;
                    String language = Locale.getDefault().getDisplayLanguage();
                    if (!language.equals("English"))
                        langId = 1;

                    if (selectedItem1 == null || selectedItem2 == null || selectedItem3 == null || selectedItem4 == null || selectedItem5 == null || selectedItem6 == null
                            || selectedItem7 == null || selectedItem8 == null || selectedItem8 == null || selectedItem9 == null || selectedItem10 == null
                    ) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.answer_the_questions), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONObject jsonObject = new JSONObject();
                    JSONArray arr = new JSONArray();
                    JSONObject jsonObjectInner = new JSONObject();

                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem1).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem1).Value);
                    arr.put(jsonObjectInner);

                    jsonObjectInner = new JSONObject();
                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem2).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem2).Value);
                    arr.put(jsonObjectInner);

                    jsonObjectInner = new JSONObject();
                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem3).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem3).Value);
                    arr.put(jsonObjectInner);

                    jsonObjectInner = new JSONObject();
                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem4).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem4).Value);
                    arr.put(jsonObjectInner);

                    jsonObjectInner = new JSONObject();
                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem5).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem5).Value);
                    arr.put(jsonObjectInner);

                    jsonObjectInner = new JSONObject();
                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem6).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem6).Value);
                    arr.put(jsonObjectInner);

                    jsonObjectInner = new JSONObject();
                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem7).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem7).Value);
                    arr.put(jsonObjectInner);

                    jsonObjectInner = new JSONObject();
                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem8).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem8).Value);
                    arr.put(jsonObjectInner);

                    jsonObjectInner = new JSONObject();
                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem9).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem9).Value);
                    arr.put(jsonObjectInner);

                    jsonObjectInner = new JSONObject();
                    jsonObjectInner.put("Id", ((ListItemDiscQuestions) selectedItem10).Id);
                    jsonObjectInner.put("Value", ((ListItemDiscQuestions) selectedItem10).Value);
                    arr.put(jsonObjectInner);
                    jsonObject.put("answers", arr);
                    jsonObject.put("lang", langId);

                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    JsonObjectRequest jsonForPutRequest = new JsonObjectRequest(
                            Request.Method.POST, "https://ixirus.azurewebsites.net/api/disc/checkResultv2", jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        JSONArray result = response.getJSONArray("data");
                                        _discInfoTitle = result.getJSONObject(0).getString("previousBehavior");

                                        if (result.length() > 1) {
                                            bsDialog = new BottomSheetDialog(DiscQuestionsActivity.this);
                                            bsDialog.setContentView(R.layout.dialog_choose_disc_behavior);//çoklu cevap için bottomsheetlayout açılacak
                                            ConstraintLayout layout = bsDialog.findViewById(R.id.discBehaviorLayout);
                                            ConstraintSet set = new ConstraintSet();
                                            set.clone(layout);
                                            List<Integer> nums = new ArrayList<>();

                                            float dip = result.length() == 2 ? 100f : result.length() == 3 ? 66f : 50f;
                                            Resources r = getResources();
                                            float px = TypedValue.applyDimension(
                                                    TypedValue.COMPLEX_UNIT_DIP,
                                                    dip,
                                                    r.getDisplayMetrics()
                                            );

                                            for (int i = 0; i < result.length(); i++) {
                                                JSONObject obj = result.getJSONObject(i);
                                                String name = obj.getString("discName");
                                                int discType = obj.getInt("discType");

                                                //set the properties for button
                                                Button btnTag = new Button(getBaseContext());
                                                btnTag.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                                                btnTag.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_button));
                                                btnTag.setClickable(true);
                                                btnTag.setFocusable(true);
                                                btnTag.setText(name);
                                                btnTag.setId(discType);
                                                btnTag.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        final int id = ((Button) v).getId();
                                                        String text = ((Button) v).getText().toString();
                                                        if (!text.equals(_discInfoTitle)) {
                                                            //confirmation popup 4 items
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(DiscQuestionsActivity.this);
                                                            builder.setTitle(getString(R.string.quiz_confirmation));
                                                            builder.setMessage(getString(R.string.quiz_confirmation_text) + "\n" + _discInfoTitle + "->" + text);

                                                            builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/disc/addAnswer", new Response.Listener<String>() {
                                                                        @Override
                                                                        public void onResponse(String response) {
                                                                            try {
                                                                                int langId = 0;
                                                                                String language = Locale.getDefault().getDisplayLanguage();
                                                                                if (!language.equals("English"))
                                                                                    langId = 1;

                                                                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/disc/existv2?lang=" + langId, null, new Response.Listener<JSONObject>() {
                                                                                    @Override
                                                                                    public void onResponse(JSONObject response) {
                                                                                        try {
                                                                                            JSONObject result = response.getJSONObject("data");
                                                                                            String passedDiscObject = result.toString();
                                                                                            Intent intent = new Intent(getBaseContext(), DiscMainActivity.class);
                                                                                            intent.putExtra("DiscObject", passedDiscObject);
                                                                                            startActivity(intent);
                                                                                        } catch (JSONException e) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    }
                                                                                }, new Response.ErrorListener() {
                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                        findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                                        if (error instanceof NetworkError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof ServerError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof AuthFailureError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof ParseError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof NoConnectionError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof TimeoutError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                                                            } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    }, new Response.ErrorListener() {

                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            if (error instanceof NetworkError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof ServerError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof AuthFailureError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof ParseError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof NoConnectionError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof TimeoutError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                                                        protected Map<String, String> getParams() throws AuthFailureError {
                                                                            Map<String, String> params = new HashMap<String, String>();
                                                                            params.put("DISC_TYPE", Integer.toString(id));
                                                                            return params;
                                                                        }

                                                                    };

                                                                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                                                                    queue.add(jsonObjRequest);
                                                                    dialog.dismiss();
                                                                }
                                                            });

                                                            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            });

                                                            AlertDialog alert = builder.create();
                                                            alert.show();
                                                        } else {
                                                            //4 items without confirmation
                                                            StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/disc/addAnswer", new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {
                                                                    try {
                                                                        int langId = 0;
                                                                        String language = Locale.getDefault().getDisplayLanguage();
                                                                        if (!language.equals("English"))
                                                                            langId = 1;
                                                                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/disc/existv2?lang=" + langId, null, new Response.Listener<JSONObject>() {
                                                                            @Override
                                                                            public void onResponse(JSONObject response) {
                                                                                try {
                                                                                    JSONObject result = response.getJSONObject("data");
                                                                                    String passedDiscObject = result.toString();
                                                                                    Intent intent = new Intent(getBaseContext(), DiscMainActivity.class);
                                                                                    intent.putExtra("DiscObject", passedDiscObject);
                                                                                    startActivity(intent);
                                                                                } catch (JSONException e) {
                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        }, new Response.ErrorListener() {
                                                                            @Override
                                                                            public void onErrorResponse(VolleyError error) {
                                                                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                                if (error instanceof NetworkError) {
                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                                } else if (error instanceof ServerError) {
                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                } else if (error instanceof AuthFailureError) {
                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                                } else if (error instanceof ParseError) {
                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                                } else if (error instanceof NoConnectionError) {
                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                } else if (error instanceof TimeoutError) {
                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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

                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }, new Response.ErrorListener() {

                                                                @Override
                                                                public void onErrorResponse(VolleyError error) {
                                                                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                    if (error instanceof NetworkError) {
                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                    } else if (error instanceof ServerError) {
                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                    } else if (error instanceof AuthFailureError) {
                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                    } else if (error instanceof ParseError) {
                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                    } else if (error instanceof NoConnectionError) {
                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                    } else if (error instanceof TimeoutError) {
                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                                    Map<String, String> params = new HashMap<String, String>();
                                                                    params.put("DISC_TYPE", Integer.toString(id));
                                                                    return params;
                                                                }

                                                            };

                                                            RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                                                            queue.add(jsonObjRequest);
                                                        }
                                                    }
                                                });
                                                nums.add(discType);
                                                layout.addView(btnTag);
                                                if (i == 0)
                                                    set.connect(btnTag.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 10);
                                                else
                                                    set.connect(btnTag.getId(), ConstraintSet.BOTTOM, nums.get(i - 1), ConstraintSet.TOP, 10);

                                                set.connect(btnTag.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 20);
                                                set.connect(btnTag.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 20);
                                                set.constrainHeight(btnTag.getId(), Math.round(px));
                                                set.applyTo(layout);
                                            }
                                            bsDialog.show();
                                        } else {// tek bir cevap için yes no popup gösterilecek no seçilirse 4 adet cevap bottom sheetlayout olarak açılacak
                                            JSONObject obj = result.getJSONObject(0);
                                            final String name = obj.getString("discName");
                                            final int discType = obj.getInt("discType");

                                            AlertDialog.Builder builder = new AlertDialog.Builder(DiscQuestionsActivity.this);
                                            builder.setTitle(getString(R.string.disc_result));
                                            builder.setMessage(getString(R.string.disc_result_shown_below) + "\n" + name);

                                            builder.setPositiveButton(getString(R.string.agree), new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (!name.equals(_discInfoTitle)) {
                                                        //confirmation popup 4 items
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(DiscQuestionsActivity.this);
                                                        builder.setTitle(getString(R.string.quiz_confirmation));
                                                        builder.setMessage(getString(R.string.quiz_confirmation_text) + "\n" + _discInfoTitle + "->" + name);

                                                        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/disc/addAnswer", new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        try {
                                                                            int langId = 0;
                                                                            String language = Locale.getDefault().getDisplayLanguage();
                                                                            if (!language.equals("English"))
                                                                                langId = 1;

                                                                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/disc/existv2?lang=" + langId, null, new Response.Listener<JSONObject>() {
                                                                                @Override
                                                                                public void onResponse(JSONObject response) {
                                                                                    try {
                                                                                        JSONObject result = response.getJSONObject("data");
                                                                                        String passedDiscObject = result.toString();
                                                                                        Intent intent = new Intent(getBaseContext(), DiscMainActivity.class);
                                                                                        intent.putExtra("DiscObject", passedDiscObject);
                                                                                        startActivity(intent);
                                                                                    } catch (JSONException e) {
                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            }, new Response.ErrorListener() {
                                                                                @Override
                                                                                public void onErrorResponse(VolleyError error) {
                                                                                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                                    if (error instanceof NetworkError) {
                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                                    } else if (error instanceof ServerError) {
                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                    } else if (error instanceof AuthFailureError) {
                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                                    } else if (error instanceof ParseError) {
                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                                    } else if (error instanceof NoConnectionError) {
                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                    } else if (error instanceof TimeoutError) {
                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }, new Response.ErrorListener() {

                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                        if (error instanceof NetworkError) {
                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                        } else if (error instanceof ServerError) {
                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                        } else if (error instanceof AuthFailureError) {
                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                        } else if (error instanceof ParseError) {
                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                        } else if (error instanceof NoConnectionError) {
                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                        } else if (error instanceof TimeoutError) {
                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                                        Map<String, String> params = new HashMap<String, String>();
                                                                        params.put("DISC_TYPE", Integer.toString(discType));
                                                                        return params;
                                                                    }

                                                                };

                                                                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                                                                queue.add(jsonObjRequest);
                                                                dialog.dismiss();
                                                            }
                                                        });

                                                        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {//4 cevaplı menu gelecek
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });

                                                        AlertDialog alert = builder.create();
                                                        alert.show();
                                                    } else {
                                                        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/disc/addAnswer", new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    int langId = 0;
                                                                    String language = Locale.getDefault().getDisplayLanguage();
                                                                    if (!language.equals("English"))
                                                                        langId = 1;
                                                                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/disc/existv2?lang=" + langId, null, new Response.Listener<JSONObject>() {
                                                                        @Override
                                                                        public void onResponse(JSONObject response) {
                                                                            try {
                                                                                JSONObject result = response.getJSONObject("data");
                                                                                String passedDiscObject = result.toString();
                                                                                Intent intent = new Intent(getBaseContext(), DiscMainActivity.class);
                                                                                intent.putExtra("DiscObject", passedDiscObject);
                                                                                startActivity(intent);
                                                                            } catch (JSONException e) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    }, new Response.ErrorListener() {
                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                            if (error instanceof NetworkError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof ServerError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof AuthFailureError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof ParseError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof NoConnectionError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                            } else if (error instanceof TimeoutError) {
                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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

                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }, new Response.ErrorListener() {

                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                if (error instanceof NetworkError) {
                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                } else if (error instanceof ServerError) {
                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                } else if (error instanceof AuthFailureError) {
                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                } else if (error instanceof ParseError) {
                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                } else if (error instanceof NoConnectionError) {
                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                } else if (error instanceof TimeoutError) {
                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                                Map<String, String> params = new HashMap<String, String>();
                                                                params.put("DISC_TYPE", Integer.toString(discType));
                                                                return params;
                                                            }

                                                        };

                                                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                                                        queue.add(jsonObjRequest);
                                                    }
                                                }
                                            });

                                            builder.setNegativeButton(getString(R.string.disagree), new DialogInterface.OnClickListener() {//4 cevaplı menu gelecek
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    int langId = 0;
                                                    String language = Locale.getDefault().getDisplayLanguage();
                                                    if (!language.equals("English"))
                                                        langId = 1;

                                                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/disc/allTypes?lang=" + langId, null, new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            try {
                                                                JSONArray result = response.getJSONArray("data");

                                                                bsDialog = new BottomSheetDialog(DiscQuestionsActivity.this);
                                                                bsDialog.setContentView(R.layout.dialog_choose_disc_behavior);//çoklu cevap için bottomsheetlayout açılacak
                                                                ConstraintLayout layout = bsDialog.findViewById(R.id.discBehaviorLayout);
                                                                ConstraintSet set = new ConstraintSet();
                                                                set.clone(layout);
                                                                List<Integer> nums = new ArrayList<>();

                                                                float dip = result.length() == 2 ? 100f : result.length() == 3 ? 66f : 50f;
                                                                Resources r = getResources();
                                                                float px = TypedValue.applyDimension(
                                                                        TypedValue.COMPLEX_UNIT_DIP,
                                                                        dip,
                                                                        r.getDisplayMetrics()
                                                                );

                                                                for (int i = 0; i < result.length(); i++) {
                                                                    JSONObject obj = result.getJSONObject(i);
                                                                    String name = obj.getString("name");
                                                                    int discType = obj.getInt("id");

                                                                    Button btnTag = new Button(getBaseContext());
                                                                    btnTag.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                                                                    btnTag.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_button));
                                                                    btnTag.setClickable(true);
                                                                    btnTag.setFocusable(true);
                                                                    btnTag.setText(name);
                                                                    btnTag.setId(discType);

                                                                    btnTag.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            final int id = ((Button) v).getId();
                                                                            String text = ((Button) v).getText().toString();
                                                                            if (!text.equals(_discInfoTitle)) {
                                                                                //confirmation popup 4 items
                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(DiscQuestionsActivity.this);
                                                                                builder.setTitle(getString(R.string.quiz_confirmation));
                                                                                builder.setMessage(getString(R.string.quiz_confirmation_text) + "\n" + _discInfoTitle + "->" + text);

                                                                                builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/disc/addAnswer", new Response.Listener<String>() {
                                                                                            @Override
                                                                                            public void onResponse(String response) {
                                                                                                try {
                                                                                                    int langId = 0;
                                                                                                    String language = Locale.getDefault().getDisplayLanguage();
                                                                                                    if (!language.equals("English"))
                                                                                                        langId = 1;

                                                                                                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/disc/existv2?lang=" + langId, null, new Response.Listener<JSONObject>() {
                                                                                                        @Override
                                                                                                        public void onResponse(JSONObject response) {
                                                                                                            try {
                                                                                                                JSONObject result = response.getJSONObject("data");
                                                                                                                String passedDiscObject = result.toString();
                                                                                                                Intent intent = new Intent(getBaseContext(), DiscMainActivity.class);
                                                                                                                intent.putExtra("DiscObject", passedDiscObject);
                                                                                                                startActivity(intent);
                                                                                                            } catch (JSONException e) {
                                                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        }
                                                                                                    }, new Response.ErrorListener() {
                                                                                                        @Override
                                                                                                        public void onErrorResponse(VolleyError error) {
                                                                                                            findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                                                            if (error instanceof NetworkError) {
                                                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                                                            } else if (error instanceof ServerError) {
                                                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                                            } else if (error instanceof AuthFailureError) {
                                                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                                                            } else if (error instanceof ParseError) {
                                                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                                                            } else if (error instanceof NoConnectionError) {
                                                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                                            } else if (error instanceof TimeoutError) {
                                                                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                                                                                } catch (Exception e) {
                                                                                                    e.printStackTrace();
                                                                                                }
                                                                                            }
                                                                                        }, new Response.ErrorListener() {

                                                                                            @Override
                                                                                            public void onErrorResponse(VolleyError error) {
                                                                                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                                                if (error instanceof NetworkError) {
                                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                                                } else if (error instanceof ServerError) {
                                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                                } else if (error instanceof AuthFailureError) {
                                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                                                } else if (error instanceof ParseError) {
                                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                                                } else if (error instanceof NoConnectionError) {
                                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                                } else if (error instanceof TimeoutError) {
                                                                                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                                                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                                                                Map<String, String> params = new HashMap<String, String>();
                                                                                                params.put("DISC_TYPE", Integer.toString(id));
                                                                                                return params;
                                                                                            }

                                                                                        };

                                                                                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                                                                                        queue.add(jsonObjRequest);
                                                                                        dialog.dismiss();
                                                                                    }
                                                                                });

                                                                                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {//4 cevaplı menu gelecek
                                                                                    @Override
                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                        dialog.dismiss();
                                                                                    }
                                                                                });

                                                                                AlertDialog alert = builder.create();
                                                                                alert.show();
                                                                            } else {
                                                                                //4 items without confirmation
                                                                                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/disc/addAnswer", new Response.Listener<String>() {
                                                                                    @Override
                                                                                    public void onResponse(String response) {
                                                                                        try {
                                                                                            int langId = 0;
                                                                                            String language = Locale.getDefault().getDisplayLanguage();
                                                                                            if (!language.equals("English"))
                                                                                                langId = 1;
                                                                                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/disc/existv2?lang=" + langId, null, new Response.Listener<JSONObject>() {
                                                                                                @Override
                                                                                                public void onResponse(JSONObject response) {
                                                                                                    try {
                                                                                                        JSONObject result = response.getJSONObject("data");
                                                                                                        String passedDiscObject = result.toString();
                                                                                                        Intent intent = new Intent(getBaseContext(), DiscMainActivity.class);
                                                                                                        intent.putExtra("DiscObject", passedDiscObject);
                                                                                                        startActivity(intent);
                                                                                                    } catch (JSONException e) {
                                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                }
                                                                                            }, new Response.ErrorListener() {
                                                                                                @Override
                                                                                                public void onErrorResponse(VolleyError error) {
                                                                                                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                                                    if (error instanceof NetworkError) {
                                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                                                    } else if (error instanceof ServerError) {
                                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                                    } else if (error instanceof AuthFailureError) {
                                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                                                    } else if (error instanceof ParseError) {
                                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                                                    } else if (error instanceof NoConnectionError) {
                                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                                    } else if (error instanceof TimeoutError) {
                                                                                                        Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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

                                                                                        } catch (Exception e) {
                                                                                            e.printStackTrace();
                                                                                        }
                                                                                    }
                                                                                }, new Response.ErrorListener() {

                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                        findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                                                        if (error instanceof NetworkError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof ServerError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof AuthFailureError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof ParseError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof NoConnectionError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                                                        } else if (error instanceof TimeoutError) {
                                                                                            Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                                                        Map<String, String> params = new HashMap<String, String>();
                                                                                        params.put("DISC_TYPE", Integer.toString(id));
                                                                                        return params;
                                                                                    }

                                                                                };

                                                                                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                                                                                queue.add(jsonObjRequest);
                                                                            }
                                                                        }
                                                                    });

                                                                    nums.add(discType);
                                                                    layout.addView(btnTag);
                                                                    if (i == 0)
                                                                        set.connect(btnTag.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 10);
                                                                    else
                                                                        set.connect(btnTag.getId(), ConstraintSet.BOTTOM, nums.get(i - 1), ConstraintSet.TOP, 10);

                                                                    set.connect(btnTag.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 20);
                                                                    set.connect(btnTag.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 20);
                                                                    set.constrainHeight(btnTag.getId(), Math.round(px));
                                                                    set.applyTo(layout);
                                                                }
                                                                bsDialog.show();

                                                            } catch (JSONException e) {

                                                                // findViewById(R.id.progressBar2).setVisibility(View.GONE);
                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                                            if (error instanceof NetworkError) {
                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                                                            } else if (error instanceof ServerError) {
                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                            } else if (error instanceof AuthFailureError) {
                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                                                            } else if (error instanceof ParseError) {
                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                                                            } else if (error instanceof NoConnectionError) {
                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                                            } else if (error instanceof TimeoutError) {
                                                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
                                            });

                                            AlertDialog alert = builder.create();
                                            alert.show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                            if (error instanceof NetworkError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NoConnectionError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
        });
    }


    public void loadListItems(final String savedToken) throws JSONException {
        String language = Locale.getDefault().getDisplayLanguage();
        int langId = 0;
        if (!language.equals("English"))
            langId = 1;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/disc/list?lang=" + langId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<ListItemDiscQuestions> arr1 = new ArrayList<ListItemDiscQuestions>();
                ArrayList<ListItemDiscQuestions> arr2 = new ArrayList<ListItemDiscQuestions>();
                ArrayList<ListItemDiscQuestions> arr3 = new ArrayList<ListItemDiscQuestions>();
                ArrayList<ListItemDiscQuestions> arr4 = new ArrayList<ListItemDiscQuestions>();
                ArrayList<ListItemDiscQuestions> arr5 = new ArrayList<ListItemDiscQuestions>();
                ArrayList<ListItemDiscQuestions> arr6 = new ArrayList<ListItemDiscQuestions>();
                ArrayList<ListItemDiscQuestions> arr7 = new ArrayList<ListItemDiscQuestions>();
                ArrayList<ListItemDiscQuestions> arr8 = new ArrayList<ListItemDiscQuestions>();
                ArrayList<ListItemDiscQuestions> arr9 = new ArrayList<ListItemDiscQuestions>();
                ArrayList<ListItemDiscQuestions> arr10 = new ArrayList<ListItemDiscQuestions>();
                try {
                    JSONArray programArray = response.getJSONArray("data");
                    for (int i = 0; i < programArray.length(); i++) {
                        JSONObject obj = programArray.getJSONObject(i);
                        String[] questions = new String[]{"q1", "q2", "q3", "q4"};
                        for (String s : questions) {
                            ListItemDiscQuestions item = new ListItemDiscQuestions();
                            int id = Integer.parseInt(obj.getString("id"));
                            item.Id = Integer.parseInt(obj.getString("id"));

                            JSONObject questionObj = obj.getJSONObject(s);
                            String text = questionObj.getString("text");
                            int value = Integer.parseInt(questionObj.getString("value"));
                            item.Name = text;
                            item.Value = value;
                            if (i == 0)
                                arr1.add(item);
                            else if (i == 1)
                                arr2.add(item);
                            else if (i == 2)
                                arr3.add(item);
                            else if (i == 3)
                                arr4.add(item);
                            else if (i == 4)
                                arr5.add(item);
                            else if (i == 5)
                                arr6.add(item);
                            else if (i == 6)
                                arr7.add(item);
                            else if (i == 7)
                                arr8.add(item);
                            else if (i == 8)
                                arr9.add(item);
                            else if (i == 9)
                                arr10.add(item);
                        }
                    }

                    final DiscQuestionsListAdapter adapter1 = new DiscQuestionsListAdapter(getBaseContext(), arr1);
                    final DiscQuestionsListAdapter adapter2 = new DiscQuestionsListAdapter(getBaseContext(), arr2);
                    final DiscQuestionsListAdapter adapter3 = new DiscQuestionsListAdapter(getBaseContext(), arr3);
                    final DiscQuestionsListAdapter adapter4 = new DiscQuestionsListAdapter(getBaseContext(), arr4);
                    final DiscQuestionsListAdapter adapter5 = new DiscQuestionsListAdapter(getBaseContext(), arr5);
                    final DiscQuestionsListAdapter adapter6 = new DiscQuestionsListAdapter(getBaseContext(), arr6);
                    final DiscQuestionsListAdapter adapter7 = new DiscQuestionsListAdapter(getBaseContext(), arr7);
                    final DiscQuestionsListAdapter adapter8 = new DiscQuestionsListAdapter(getBaseContext(), arr8);
                    final DiscQuestionsListAdapter adapter9 = new DiscQuestionsListAdapter(getBaseContext(), arr9);
                    final DiscQuestionsListAdapter adapter10 = new DiscQuestionsListAdapter(getBaseContext(), arr10);

                    lv1.setAdapter(adapter1);
                    lv2.setAdapter(adapter2);
                    lv3.setAdapter(adapter3);
                    lv4.setAdapter(adapter4);
                    lv5.setAdapter(adapter5);
                    lv6.setAdapter(adapter6);
                    lv7.setAdapter(adapter7);
                    lv8.setAdapter(adapter8);
                    lv9.setAdapter(adapter9);
                    lv10.setAdapter(adapter10);

                    Bundle extras = getIntent().getExtras();
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                if (error instanceof NetworkError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
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
}
