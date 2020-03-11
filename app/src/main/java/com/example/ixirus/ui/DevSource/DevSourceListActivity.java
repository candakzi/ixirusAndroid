package com.example.ixirus.ui.DevSource;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import com.android.volley.toolbox.Volley;
import com.example.ixirus.ListAdapters.DevSourceListAdapter;
import com.example.ixirus.ListAdapters.GenericListAdapter;
import com.example.ixirus.ListAdapters.SourceListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.ListItemSources;
import com.example.ixirus.R;
import com.example.ixirus.ui.BaseScreenActivity;
import com.example.ixirus.ui.DevPlan.CreateDevPlanActivity7;
import com.example.ixirus.ui.MainActivityWithoutFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DevSourceListActivity extends AppCompatActivity {
    private BottomSheetDialog dialog;
    private ListView dialogList;
    private Object selectedItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_source_list);
        ImageView imageView = findViewById(R.id.buttonBack);
        ImageView filterImage = findViewById(R.id.buttonFilter);

        getWindow().setBackgroundDrawableResource(R.mipmap.dev_sources_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(R.mipmap.ixirus_logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(imgView);

        final TextView tv = (TextView) findViewById(R.id.textView2);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        tv.setTextSize(9 * density);

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);
        final ListView sourcesListView = findViewById(R.id.listView);
        loadSourceListItem(savedToken, sourcesListView, selectedItem);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivityWithoutFragment.class);
                startActivity(intent);
                overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);
            }
        });

        dialog = new BottomSheetDialog(DevSourceListActivity.this);
        dialog.setContentView(R.layout.dialog_sources);

        filterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        dialogList = dialog.findViewById(R.id.listViewDialog);
        Button clearBtn = dialog.findViewById(R.id.buttonClear);

        dialogList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        dialogList.setClickable(true);

        dialogList.setOnTouchListener(new ListView.OnTouchListener() {
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
        loadBSheetLayout(savedToken);

        dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                selectedItem = dialogList.getItemAtPosition(position);
                loadSourceListItem(savedToken, sourcesListView, selectedItem);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericListAdapter adapter =  (GenericListAdapter)dialogList.getAdapter();
                for (int position = 0; position < adapter.getCount(); position++)
                        dialogList.setItemChecked(position, false);

                selectedItem = null;
                loadSourceListItem(savedToken, sourcesListView, selectedItem);
            }
        });

        sourcesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                final Object selectedItem = sourcesListView.getItemAtPosition(position);
                final int sourceType = ((ListItemSources) selectedItem).SourceType;
                final String sourceUrl = ((ListItemSources) selectedItem).Url;

                String extension = MimeTypeMap.getFileExtensionFromUrl(sourceUrl);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                mediaIntent.setDataAndType(Uri.parse(sourceUrl), mimeType);
                if (mediaIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mediaIntent);
                }
            }
        });
    }

    public void loadSourceListItem(final String savedToken, final ListView sourcesLv, final Object item) {
        int selectedId = -1;
        if (item != null) {
            selectedId = ((ListItem) selectedItem).Id;
        }
        String url = selectedId != -1 ? "https://ixirus.azurewebsites.net/api/source?perfectionId=" + Integer.toString(selectedId) : "https://ixirus.azurewebsites.net/api/source";
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
                        String name = obj.getString("name");
                        int id = Integer.parseInt(obj.getString("id"));
                        String url = obj.getString("fileUrl");
                        item.Id = id;
                        item.Name = name;
                        item.Description = description;
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
                    final DevSourceListAdapter adapter = new DevSourceListAdapter(getBaseContext(), arr);
                    sourcesLv.setAdapter(adapter);
                    findViewById(R.id.refreshIco).setVisibility(View.GONE);
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
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        queue.add(request);
    }

    public void loadBSheetLayout(final String savedToken) {
        dialogList.setAdapter(null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/perfection", null, new Response.Listener<JSONObject>() {
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
                    dialogList.setAdapter(adapter);

//                    for (int position = 0; position < adapter.getCount(); position++)
//                        if (((ListItem) adapter.getItem(position)).Id == perfectionId) {
//                            dialogList.setItemChecked(position, true);
//                            dialogList.setSelection(position);
//                            //selectedItem = dialogList.getItemAtPosition(position);
//                        }

                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
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
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        queue.add(request);
    }
}
