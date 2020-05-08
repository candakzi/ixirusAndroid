package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
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
import com.example.ixirus.CustomListItem;
import com.example.ixirus.LanguageHelper;
import com.example.ixirus.ListAdapters.GenericListAdapter;
import com.example.ixirus.ListAdapters.MyDevPlanListAdapter;
import com.example.ixirus.ListAdapters.WaitingCompletedListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.R;
import com.example.ixirus.ui.BaseScreenActivity;
import com.example.ixirus.ui.MainActivityWithoutFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyDevPlanListActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dev_plan_list);
        Button fab = findViewById(R.id.button);
        lv = findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setClickable(true);
        final TextView tv = (TextView) findViewById(R.id.textView2);
        ImageView imageView = findViewById(R.id.buttonBack);
        final ImageView refreshImage = (ImageView) findViewById(R.id.refreshIco);

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
        tv.setTextSize(9 * density);

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                long viewId = arg1.getId();
                final Object selectedItem = lv.getItemAtPosition(position);
                final String selectedId = Integer.toString(((ListItem) selectedItem).Id);

                if (viewId == R.id.btnDelete) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyDevPlanListActivity.this);
                    builder.setTitle(getString(R.string.dev_plan));
                    builder.setMessage(getString(R.string.are_you_sure));

                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            StringRequest jsonObjRequest = new StringRequest(Request.Method.DELETE, "https://ixirus.azurewebsites.net/api/developmentplan?devPlanId=" + selectedId, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    loadListItem(savedToken);
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
                } else if (viewId == R.id.btnEdit) {

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/developmentplan?devPlanId=" + selectedId, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            ArrayList<ListItem> arrWaiting = new ArrayList<ListItem>();
                            ArrayList<ListItem> arrCompleted = new ArrayList<ListItem>();

                            try {

                                JSONObject objectt = response.getJSONObject("data");
                                Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);
                                intent.putExtra("editedDevPlan", objectt.toString());
                                intent.putExtra("fromEdit", true);

                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NetworkError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NoConnectionError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } else if (error.networkResponse.statusCode == 401) {
                                Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
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
                } else if (viewId == R.id.btnSummary) {


                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/developmentplan?devPlanId=" + selectedId, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONObject objectt = response.getJSONObject("data");
                                Intent intent = new Intent(getBaseContext(), DevPlanPreviewActivity.class);
                                intent.putExtra("editedDevPlan", objectt.toString());
                                intent.putExtra("summary", true);
                                intent.putExtra("fromEdit", true);
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NetworkError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NoConnectionError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                            } else if (error.networkResponse.statusCode == 401) {
                                Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
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
                } else if (viewId == R.id.btnView) {
                    Intent intent = new Intent(getBaseContext(), WaitingCompletedActionsActivity.class);
                    intent.putExtra("devPlanId", selectedId);
                    startActivity(intent);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivityWithoutFragment.class);
                startActivity(intent);
                overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);
            }
        });

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshImage.setVisibility(View.GONE);
                findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                loadListItem(savedToken);

            }
        });

        loadListItem(savedToken);
    }

    public void loadListItem(final String savedToken) {
        lv.setAdapter(null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/developmentplanlist", null, new Response.Listener<JSONObject>() {
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
                    final MyDevPlanListAdapter adapter = new MyDevPlanListAdapter(getBaseContext(), arr);
                    lv.setAdapter(adapter);

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), MainActivityWithoutFragment.class);
        startActivity(intent);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);
    }
}
