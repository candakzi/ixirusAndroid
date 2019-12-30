package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.android.volley.toolbox.Volley;
import com.example.ixirus.ListAdapters.GenericListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateDevPlanActivity7 extends AppCompatActivity {
    private ListView lv;
    private Calendar myCalendar;
    private TextView dateTextView;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan7);
        ImageView imageView = findViewById(R.id.buttonBack);
        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan) ;

        lv = findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

            }
        });

        final TextView tv = (TextView)findViewById(R.id.textView2);
        final ImageView refreshImage = (ImageView)findViewById(R.id.refreshIco) ;
        final Button nextButton = (Button) findViewById(R.id.button);
        final TextView resourceText = (TextView)findViewById(R.id.resourceText) ;

        final BottomSheetDialog dialog = new BottomSheetDialog(CreateDevPlanActivity7.this);
        dialog.setContentView(R.layout.dialog_resources);
        TextView pdfText =  dialog.findViewById(R.id.text);
        TextView videoText =  dialog.findViewById(R.id.text2);
        TextView imageText =  dialog.findViewById(R.id.text3);
        TextView voiceText =  dialog.findViewById(R.id.text4);

        dateTextView = (TextView)findViewById(R.id.editTextDate);
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateDevPlanActivity7.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        resourceText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        pdfText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkPermission())
                    {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivityForResult(intent, 7);
                    } else {
                        requestPermission();
                    }
                }
            }
        });

        videoText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkPermission())
                    {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("video/*");
                        startActivityForResult(intent, 7);
                    } else {
                        requestPermission();
                    }
                }
            }
        });

        imageText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkPermission())
                    {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 7);
                    } else {
                        requestPermission();
                    }
                }
            }
        });

        voiceText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkPermission())
                    {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("audio/*");
                        startActivityForResult(intent, 7);
                    } else {
                        requestPermission();
                    }
                }
            }
        });

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
        tv.setTextSize(9 * density);

        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token",null);
        loadListItem(savedToken,null,false);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity8.class);
                startActivity(intent);
            }
        });

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshImage.setVisibility(View.GONE);
                findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                loadListItem(savedToken,null,false);

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateTextView.setText(sdf.format(myCalendar.getTime()));
    }

    public void loadListItem(final String savedToken,final String addedText, final boolean fromAddItem)
    {
        lv.setAdapter(null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/task", null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<ListItem> arr = new ArrayList<ListItem>();
                try {
                    JSONArray programArray = response.getJSONArray("data");
                    for (int i=0; i < programArray.length(); i++) {
                        JSONObject obj =  programArray.getJSONObject(i);
                        ListItem item =  new ListItem();
                        item.Id =  Integer.parseInt(obj.getString("id"));
                        item.Name =  obj.getString("name");
                        arr.add(item);
                    }
                    final GenericListAdapter adapter = new GenericListAdapter(getBaseContext(),arr);
                    lv.setAdapter(adapter);
                    if(fromAddItem)
                    {
                        for (int position=0; position<adapter.getCount(); position++)
                            if (((ListItem)adapter.getItem(position)).Name.equals(addedText)) {
                                lv.setItemChecked(position, true);
                                lv.setSelection(position);
                            }
                    }
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
                    findViewById(R.id.refreshIco).setVisibility(View.VISIBLE);
                    findViewById(R.id.progressBar2).setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),getResources().getString(R.string.click_list_ico), Toast.LENGTH_SHORT).show();
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

    public void addProgramm(final String savedToken,String addedText)
    {
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        Toast.makeText(getBaseContext(), getResources().getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
        loadListItem(savedToken,addedText,true);
        lv.setItemChecked(0,true);
        findViewById(R.id.progressBar2).setVisibility(View.GONE);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(CreateDevPlanActivity7.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(CreateDevPlanActivity7.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(CreateDevPlanActivity7.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(CreateDevPlanActivity7.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, 7);
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}
