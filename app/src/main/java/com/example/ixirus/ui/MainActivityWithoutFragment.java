package com.example.ixirus.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.ixirus.CustomListItem;
import com.example.ixirus.ListAdapters.DiscQuestionsListAdapter;
import com.example.ixirus.ListAdapters.MainListAdapter;
import com.example.ixirus.ListItemDiscQuestions;
import com.example.ixirus.R;
import com.example.ixirus.ui.DevPlan.MessagesActivity;
import com.example.ixirus.ui.DevPlan.MyDevPlanListActivity;
import com.example.ixirus.ui.DevPlan.NotificationsActivity;
import com.example.ixirus.ui.DevSource.DevSourceListActivity;
import com.example.ixirus.ui.Disc.DiscMainActivity;
import com.example.ixirus.ui.Disc.DiscQuestionsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivityWithoutFragment extends AppCompatActivity {
    private ListView lv;
    private boolean hasDisc;
    private String passedDiscObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_without_fragment);
        lv = findViewById(R.id.listView);

        CustomListItem item1 = new CustomListItem();
        item1.Name = getString(R.string.menu_development_plan);
        item1.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.development_plan);
        item1.Activity = new MyDevPlanListActivity();

        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(R.mipmap.ixirus_logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(imgView);

        CustomListItem item11 = new CustomListItem();
        item11.Name = getString(R.string.menu_behavior_style);
        item11.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.xperience_sharing);
        if (hasDisc)
            item11.Activity = new DiscMainActivity();
        else
            item11.Activity = new DiscQuestionsActivity();


        CustomListItem item3 = new CustomListItem();
        item3.Name = getString(R.string.menu_messages);
        item3.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.messages);
        item3.Activity = new MessagesActivity();

        CustomListItem item4 = new CustomListItem();
        item4.Name = getString(R.string.notifications);
        item4.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.feedbacks);
        item4.Activity = new NotificationsActivity();

        CustomListItem item6 = new CustomListItem();
        item6.Name = getString(R.string.menu_dev_sources);
        item6.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.dev_sources);
        item6.Activity = new DevSourceListActivity();

        CustomListItem[] cListItems = new CustomListItem[]{item1, item11, item3, item4, item6};

        lv.setAdapter(new MainListAdapter(this, cListItems));

        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = lv.getItemAtPosition(position);
                CustomListItem item = (CustomListItem) o;
                if (item.Name.contains("Style") || item.Name.contains("Stil") || item.Name.contains("Davranış")) {
                    try {
                        CheckDisc();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent intent = new Intent(getBaseContext(), item.Activity.getClass());
                    startActivity(intent);
                }
            }
        });


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.d("TAG", token);
                        Toast.makeText(MainActivityWithoutFragment.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void CheckDisc() throws JSONException {
        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);
        int langId = 0;
        String language = Locale.getDefault().getDisplayLanguage();
        if (!language.equals("English"))
            langId = 1;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/disc/existv2?lang=" + langId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject result = response.getJSONObject("data");
                    String discResult = result.getString("discResult");
                    if (discResult.equals("null")) {
                        Intent intent = new Intent(getBaseContext(), DiscQuestionsActivity.class);
                        startActivity(intent);
                    } else {
                        passedDiscObject = result.toString();
                        Intent intent = new Intent(getBaseContext(), DiscMainActivity.class);
                        intent.putExtra("DiscObject", passedDiscObject);
                        startActivity(intent);
                    }


                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                    lv.setClickable(true);
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
                } else if (error.networkResponse.statusCode == 401) {
                    Intent intent = new Intent(getBaseContext(), BaseScreenActivity.class);
                    startActivity(intent);
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
