package com.oecbv.ixirus.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.oecbv.ixirus.CustomListItem;
import com.oecbv.ixirus.LanguageHelper;
import com.oecbv.ixirus.ListAdapters.MainListAdapter;
import com.oecbv.ixirus.R;
import com.oecbv.ixirus.ui.DevPlan.MyDevPlanListActivity;
import com.oecbv.ixirus.ui.DevPlan.NotificationsActivity;
import com.oecbv.ixirus.ui.DevSource.DevSourceListActivity;
import com.oecbv.ixirus.ui.Disc.DiscMainActivity;
import com.oecbv.ixirus.ui.Disc.DiscQuestionsActivity;
import com.oecbv.ixirus.ui.Settings.SettingsActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
        item1.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.assignments);
        item1.Activity = new MyDevPlanListActivity();

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.text1);

        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
        mActionBar.setCustomView(mCustomView, layout);
        mActionBar.setDisplayShowCustomEnabled(true);


        CustomListItem item11 = new CustomListItem();
        item11.Name = getString(R.string.menu_behavior_style);
        item11.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.messages);
        if (hasDisc)
            item11.Activity = new DiscMainActivity();
        else
            item11.Activity = new DiscQuestionsActivity();


//        CustomListItem item3 = new CustomListItem();
//        item3.Name = getString(R.string.menu_messages);
//        item3.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.messages);
//        item3.Activity = new MessagesActivity();

        CustomListItem item4 = new CustomListItem();
        item4.Name = getString(R.string.notifications);
        item4.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.xperience_sharing);
        item4.Activity = new NotificationsActivity();

        CustomListItem item6 = new CustomListItem();
        item6.Name = getString(R.string.menu_dev_sources);
        item6.Drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.feedbacks);
        item6.Activity = new DevSourceListActivity();

        CustomListItem[] cListItems = new CustomListItem[]{item1, item11, item4, item6};

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


        mTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
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
                headers.put("langType", new LanguageHelper().getLanguage());
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
