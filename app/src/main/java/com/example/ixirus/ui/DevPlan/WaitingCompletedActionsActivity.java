package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
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
import com.example.ixirus.ListAdapters.GenericListAdapter;
import com.example.ixirus.ListAdapters.WaitingCompletedListAdapter;
import com.example.ixirus.ListItemSources;
import com.example.ixirus.R;
import com.example.ixirus.ui.BaseScreenActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WaitingCompletedActionsActivity extends AppCompatActivity {
    private ListView waitingActionsList;
    private ListView completedActionsList;
    private BottomSheetDialog dialog;
    private RatingBar rbar;
    private EditText editText;
    private TextView summaryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_completed_actions);

        ImageView imageView = findViewById(R.id.buttonBack);
        summaryText= findViewById(R.id.textViewSummary);
        Button askFeedbackButton = findViewById(R.id.askFeedBackButton);
        Button showFeedbacksButton = findViewById(R.id.showFeedbacksButton);
        Button rateButton = findViewById(R.id.rateButton);
        Button experienceSharingButton = findViewById(R.id.experienceSharingButton);

        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan);

        Bundle extras = getIntent().getExtras();
        final String devPlanId = extras.getString("devPlanId");

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        waitingActionsList = findViewById(R.id.listViewWaiting);
        waitingActionsList.setChoiceMode(ListView.CHOICE_MODE_NONE);
        waitingActionsList.setClickable(true);
        waitingActionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                long viewId = arg1.getId();
                final Object selectedItem = waitingActionsList.getItemAtPosition(position);
                final String selectedId = Integer.toString(((ListItemSources) selectedItem).Id);
                final String selectedSourceId = Integer.toString(((ListItemSources) selectedItem).SourceType);

                if (viewId == R.id.btnMarkCompleted) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WaitingCompletedActionsActivity.this);
                    builder.setTitle(getString(R.string.dev_plan));
                    builder.setMessage(getString(R.string.are_you_sure));

                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/taskcomplete?taskId=" + selectedId, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    loadListItems(savedToken, devPlanId);
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
                }
                else if(viewId == R.id.btnShowSource)
                {
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/source?sourceId=" + selectedSourceId, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           // loadListItems(savedToken, devPlanId);
                            try {
                                JSONObject obj = new JSONObject(response);
                                String fileUrl = obj.getJSONObject("data").getString("fileUrl");

                                String extension = MimeTypeMap.getFileExtensionFromUrl(fileUrl);
                                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                                Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                                mediaIntent.setDataAndType(Uri.parse(fileUrl), mimeType);
                                if (mediaIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(mediaIntent);
                                }

                            } catch (JSONException e) {
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

                }
            }
        });

        completedActionsList = findViewById(R.id.listViewCompleted);
        completedActionsList.setChoiceMode(ListView.CHOICE_MODE_NONE);
        completedActionsList.setClickable(true);
        completedActionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                long viewId = arg1.getId();
                final Object selectedItem = completedActionsList.getItemAtPosition(position);
                final String selectedId = Integer.toString(((ListItemSources) selectedItem).Id);
                final String selectedSourceId = Integer.toString(((ListItemSources) selectedItem).SourceType);

                if (viewId == R.id.btnMarkUncompleted) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WaitingCompletedActionsActivity.this);
                    builder.setTitle(getString(R.string.dev_plan));
                    builder.setMessage(getString(R.string.are_you_sure));

                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/taskcomplete?taskId=" + selectedId, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                  loadListItems(savedToken, devPlanId);
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
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else if(viewId == R.id.btnShowSource)
                {
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/source?sourceId=" + selectedSourceId, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // loadListItems(savedToken, devPlanId);
                            try {
                                JSONObject obj = new JSONObject(response);
                                String fileUrl = obj.getJSONObject("data").getString("fileUrl");

                                String extension = MimeTypeMap.getFileExtensionFromUrl(fileUrl);
                                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                                Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                                mediaIntent.setDataAndType(Uri.parse(fileUrl), mimeType);
                                if (mediaIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(mediaIntent);
                                }

                            } catch (JSONException e) {
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

                }
            }
        });

        final TextView tv = (TextView) findViewById(R.id.textView2);

        dialog = new BottomSheetDialog(WaitingCompletedActionsActivity.this);
        dialog.setContentView(R.layout.dialog_rate);

        editText = (EditText) dialog.findViewById(R.id.editText);
        Button button = (Button) dialog.findViewById(R.id.button);
        rbar = (RatingBar) dialog.findViewById(R.id.rating);
        TextView txt1 = (TextView) dialog.findViewById(R.id.textView);
        TextView txt2 = (TextView) dialog.findViewById(R.id.textView2);

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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        askFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateFeedbackActivity.class);
                intent.putExtra("devPlanId", devPlanId);
                startActivity(intent);
            }
        });

        showFeedbacksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FeedbackListActivity.class);
                intent.putExtra("devPlanId", devPlanId);
                startActivity(intent);
            }
        });

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = dialog.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = dialog.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        rbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = dialog.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int ratingVal = (int) rbar.getRating();
                final String currentMesageText = editText.getText().toString().trim();

                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/rating", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.rating_added), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("DevPlanId", devPlanId);
                        params.put("Message", currentMesageText);
                        params.put("Point", Integer.toString(ratingVal));

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
        });

        float density = getResources().getDisplayMetrics().density;
        tv.setTextSize(9 * density);

       loadListItems(savedToken, devPlanId);
    }

    public void loadListItems(final String savedToken, String devPlanId) {
        waitingActionsList.setAdapter(null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/task?devPlanId=" + devPlanId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<ListItemSources> arrWaiting = new ArrayList<ListItemSources>();
                ArrayList<ListItemSources> arrCompleted = new ArrayList<ListItemSources>();

                try {
                    JSONArray programArrayWaiting = response.getJSONObject("data").getJSONArray("unCompletedTasks");
                    JSONArray programArrayCompleted = response.getJSONObject("data").getJSONArray("completedTasks");
                    String programName = response.getJSONObject("data").getString("programName");
                    String behaviorName = response.getJSONObject("data").getString("behaviorName");
                    String perfectionName = response.getJSONObject("data").getString("perfectionName");
                    summaryText.setText(programName+" / "+behaviorName+" / "+perfectionName);
                    JSONObject rating = response.getJSONObject("data").getJSONObject("rating");
                    Integer point = rating.getInt("point");
                    String message = rating.getString("message");

                    for (int i = 0; i < programArrayWaiting.length(); i++) {
                        JSONObject obj = programArrayWaiting.getJSONObject(i);
                        ListItemSources item = new ListItemSources();
                        item.Id = Integer.parseInt(obj.getString("id"));
                        item.Name = obj.getString("name");
                        int sourceInt = -1;
                        String sourceId = obj.getString("sourceId");
                        if(!sourceId.equals("null"))
                            sourceInt = Integer.parseInt(sourceId);
                        item.SourceType = sourceInt;

                        arrWaiting.add(item);
                    }

                    for (int i = 0; i < programArrayCompleted.length(); i++) {
                        JSONObject obj = programArrayCompleted.getJSONObject(i);
                        ListItemSources item = new ListItemSources();
                        item.Id = Integer.parseInt(obj.getString("id"));
                        item.Name = obj.getString("name");

                        int sourceInt = -1;
                        String sourceId = obj.getString("sourceId");
                        if(!sourceId.equals("null"))
                            sourceInt = Integer.parseInt(sourceId);
                        item.SourceType = sourceInt;

                        arrCompleted.add(item);
                    }

                    final WaitingCompletedListAdapter adapter = new WaitingCompletedListAdapter(getBaseContext(), arrWaiting);
                    if (programArrayWaiting.length() == 0)
                        waitingActionsList.setAdapter(null);
                    else
                        waitingActionsList.setAdapter(adapter);

                    final WaitingCompletedListAdapter adapter2 = new WaitingCompletedListAdapter(getBaseContext(), arrCompleted);
                    if (programArrayCompleted.length() == 0)
                        completedActionsList.setAdapter(null);
                    else
                        completedActionsList.setAdapter(adapter2);

                    findViewById(R.id.progressBarWaiting).setVisibility(View.GONE);
                    findViewById(R.id.progressBarCompleted).setVisibility(View.GONE);

                    rbar.setRating((float) point);
                    if (!message.equals("null"))
                        editText.setText(message);

                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.refreshIcoWaiting).setVisibility(View.VISIBLE);
                    findViewById(R.id.refreshIcoCompleted).setVisibility(View.VISIBLE);

                    findViewById(R.id.progressBarWaiting).setVisibility(View.GONE);
                    findViewById(R.id.progressBarCompleted).setVisibility(View.GONE);

                }
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
        queue.add(request);
    }
}
