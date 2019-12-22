package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ixirus.CustomListItem;
import com.example.ixirus.HttpUtils;
import com.example.ixirus.ListAdapters.MainListAdapter;
import com.example.ixirus.ListAdapters.MyProgramListAdapter;
import com.example.ixirus.MyProgram;
import com.example.ixirus.R;
import com.example.ixirus.ui.MainActivityWithoutFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ParseException;
import cz.msebera.android.httpclient.auth.AuthScope;

public class CreateDevPlanActivity1 extends AppCompatActivity {
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan1);
        lv = findViewById(R.id.listView);
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);


        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan) ;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(R.mipmap.ixirus_logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(imgView);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;


        TextView tv = (TextView)findViewById(R.id.textView2) ;
        tv.setTextSize(9 * density);

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token",null);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/program", null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               ArrayList<MyProgram> arr = new ArrayList<MyProgram>();
                try {
                    JSONArray programArray = response.getJSONArray("data");
                    for (int i=0; i < programArray.length(); i++) {
                       JSONObject obj =  programArray.getJSONObject(i);
                        MyProgram item =  new MyProgram();
                        item.Id =  Integer.parseInt(obj.getString("id"));
                        item.Name =  obj.getString("name");
                        arr.add(item);
                    }

                    lv.setAdapter(new MyProgramListAdapter(getBaseContext(),arr));

                    findViewById(R.id.progressBar2).setVisibility(View.GONE);


                    lv.setClickable(true);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                            setItemNormal();
                            View rowView = arg1;
                            setItemSelected(rowView);
                        }
                    });
                    
                    
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), "cant load programmes", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "failure", Toast.LENGTH_SHORT).show();
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

        Button nextButton = (Button) findViewById(R.id.button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity2.class);
                startActivity(intent);
            }
        });
    }

    public void setItemSelected(View view){
        View rowView = view;

        View v2 =  ((ViewGroup)view).getChildAt(0);
        View v3 =  ((ViewGroup)v2).getChildAt(0);
        TextView txtview = (TextView)v3;

        TextView tv = (TextView)v3;
        txtview.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));

    }

    public void setItemNormal()
    {
        for (int i=0; i< lv.getChildCount(); i++)
        {
            View v = lv.getChildAt(i);

            View v2 =  ((ViewGroup)v).getChildAt(0);
            View v3 =  ((ViewGroup)v2).getChildAt(0);

            TextView txtview = (TextView)v3;
            txtview.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.black_overlay));
        }
    }
}
