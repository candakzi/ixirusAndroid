package com.oecbv.ixirus.ui.Disc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oecbv.ixirus.ListAdapters.ExpandableListAdapter;
import com.oecbv.ixirus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscDetailActivity extends AppCompatActivity {

    private JSONObject object;
    private boolean type;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disc_detail);
        final TextView tv = (TextView) findViewById(R.id.textView2);
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
        float density = getResources().getDisplayMetrics().density;

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        tv.setTextSize(9 * density);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView = (ExpandableListView) findViewById(R.id.expandableList);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                object = new JSONObject(extras.getString("DiscObject"));
                type = extras.getBoolean("Type");
            } catch (Throwable t) {
                return;
            }
        }

        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);

    }

    private void initData() throws JSONException {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        JSONArray discInfoObject = object.getJSONArray("discInfo");

        if (type) {
            String discInfoTitle = ((JSONObject) discInfoObject.get(0)).getString("Title");
            String discInfo = ((JSONObject) discInfoObject.get(0)).getString("Description");
            listDataHeader.add(discInfoTitle);

            List<String> edmtDev = new ArrayList<>();
            edmtDev.add(discInfo);

            listHash.put(listDataHeader.get(0), edmtDev);

            JSONArray childrenObjectArray = ((JSONObject) discInfoObject.get(0)).getJSONArray("Children");

            for (int i = 1; i <= childrenObjectArray.length(); i++) {
                JSONObject obj = childrenObjectArray.getJSONObject(i - 1);
                String title = obj.getString("Title");
                String description = obj.getString("Description");
                listDataHeader.add(title);
                edmtDev = new ArrayList<>();
                edmtDev.add(description);
                String innerStr="";
                if (obj.has("Children")) {
                    for (int j = 1; j <= obj.getJSONArray("Children").length(); j++) {
                        JSONObject innerObject = obj.getJSONArray("Children").getJSONObject(j - 1);
                        String innerTitle = innerObject.getString("Title");

                        SpannableStringBuilder str = new SpannableStringBuilder(innerTitle);
                        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),0,innerTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        String boldTitle = "<b>"+innerTitle+"</b>";
                        String innerDescription = innerObject.getString("Description");
                        innerStr += "\n"+ str +"\n\n"+innerDescription+"\n";
                    }
                    edmtDev.add(innerStr);
                    listHash.put(listDataHeader.get(i), edmtDev);
                }
                else
                {
                    listHash.put(listDataHeader.get(i), edmtDev);
                }
            }
        } else {
            JSONObject otherDiscInfoObject = object.getJSONObject("otherDiscInfo");
                String discInfoTitle = otherDiscInfoObject.getString("Title");
                String discInfo = otherDiscInfoObject.getString("Description");
                listDataHeader.add(discInfoTitle);

                List<String> edmtDev = new ArrayList<>();
                edmtDev.add(discInfo);

                listHash.put(listDataHeader.get(0), edmtDev);

                JSONArray childrenObjectArray = otherDiscInfoObject.getJSONArray("Children");

                for (int i = 1; i <= childrenObjectArray.length(); i++) {
                    JSONObject obj = childrenObjectArray.getJSONObject(i - 1);
                    String title = obj.getString("Title");
                    String description = obj.getString("Description");
                    listDataHeader.add(title);
                    edmtDev = new ArrayList<>();
                    edmtDev.add(description);
                    listHash.put(listDataHeader.get(i), edmtDev);
                }
        }
    }
}
