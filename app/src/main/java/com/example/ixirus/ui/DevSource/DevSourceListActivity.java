package com.example.ixirus.ui.DevSource;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
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
import com.android.volley.toolbox.Volley;
import com.example.ixirus.ListAdapters.SourceListAdapter;
import com.example.ixirus.ListItemSources;
import com.example.ixirus.R;
import com.example.ixirus.ui.DevPlan.CreateDevPlanActivity7;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DevSourceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_source_list);
        ImageView imageView = findViewById(R.id.buttonBack);
        ImageView filterImage = findViewById(R.id.buttonFilter);

        getWindow().setBackgroundDrawableResource(R.mipmap.dev_sources_back) ;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(R.mipmap.ixirus_logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(imgView);

        final TextView tv = (TextView)findViewById(R.id.textView2);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        tv.setTextSize(9 * density);

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);
        final ListView sourcesListView = findViewById(R.id.listView);
        loadSourceListItem(savedToken, sourcesListView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final BottomSheetDialog dialog = new BottomSheetDialog(DevSourceListActivity.this);
        dialog.setContentView(R.layout.dialog_resources);

        filterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


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
}
