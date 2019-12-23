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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.ixirus.ListAdapters.MainListAdapter;
import com.example.ixirus.ListAdapters.MyProgramListAdapter;
import com.example.ixirus.MyProgram;
import com.example.ixirus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class CreateDevPlanActivity1 extends AppCompatActivity {
    private ListView lv;
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

            }
        });

        final EditText editText = findViewById(R.id.editTextNewProgramme);

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

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token",null);

        TextView tv = (TextView)findViewById(R.id.textView2) ;
        tv.setTextSize(9 * density);
        loadListItem(savedToken);
        Button nextButton = (Button) findViewById(R.id.button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity2.class);
                startActivity(intent);
            }
        });

        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId  == EditorInfo.IME_ACTION_DONE) {
                    final String currentText = editText.getText().toString().trim();
                    if (currentText.matches(""))
                        return false;
                    else {
                        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/program", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                               addProgramm(savedToken);
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.retry_add), Toast.LENGTH_SHORT).show();
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

    public void loadListItem(final String savedToken)
    {
        lv.setAdapter(null);
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
    }

    public void addProgramm(final String savedToken)
    {
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        Toast.makeText(getBaseContext(), getResources().getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
        loadListItem(savedToken);
        findViewById(R.id.progressBar2).setVisibility(View.GONE);
    }
}
