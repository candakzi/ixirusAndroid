package com.example.ixirus.ui.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ixirus.LanguageHelper;
import com.example.ixirus.R;
import com.example.ixirus.ui.BaseScreenActivity;
import com.example.ixirus.ui.DevPlan.DevPlanPreviewActivity;
import com.example.ixirus.ui.LoginActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final TextView tv = (TextView) findViewById(R.id.textView2);
        ImageView imageView = findViewById(R.id.buttonBack);

        Button resetPassButton = findViewById(R.id.resetPassButton);
        Button gpdrButton = findViewById(R.id.gdprButton);
        Button logoutButton = findViewById(R.id.logoutButton);

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


        resetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, "https://ixirus.azurewebsites.net/api/auth/forgotpass", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                        builder.setTitle(getString(R.string.forgot_pass_title));
                        builder.setMessage(getString(R.string.forgot_pass_body))
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
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
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/auth/logout", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences mySPrefs = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySPrefs.edit();
                        editor.remove("Token");
                        editor.apply();

                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
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
        });

        gpdrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bsDialog = new BottomSheetDialog(SettingsActivity.this);
                bsDialog.setContentView(R.layout.web_view_dialog);
                bsDialog.show();

                final WebView wv = bsDialog.findViewById(R.id.WebView);
                wv.getSettings().setJavaScriptEnabled(true);
                String pdf;
                if (new LanguageHelper().getLanguage().equals("1"))
                    pdf = "https://ixirusblob.blob.core.windows.net/ixirus-files/f5fe200004b845d1a3207a46d942d499.pdf";
                else
                    pdf = "https://ixirusblob.blob.core.windows.net/ixirus-files/193e164b4e084eed9dad07d94306bdac.pdf";

                WebSettings settings = wv.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setSupportMultipleWindows(true);
                settings.setBuiltInZoomControls(true);
                wv.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);

            }
        });

    }
}


