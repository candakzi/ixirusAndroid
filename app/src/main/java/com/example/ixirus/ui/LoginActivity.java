package com.example.ixirus.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ixirus.HttpUtils;
import com.example.ixirus.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.*;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.progressBar).setVisibility(View.GONE);

        Button loginButton = (Button) findViewById(R.id.login);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/auth/login", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String authToken = null;
                                JSONObject obj = null;
                                try {
                                    obj = new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    authToken = obj.getJSONObject("data").getString("auth_token");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();

                                editor.putString("Token", (String) authToken);
                                editor.putString("Email", usernameEditText.getText().toString().trim());
                                editor.putString("Password", passwordEditText.getText().toString().trim());

                                editor.apply();
                                findViewById(R.id.progressBar).setVisibility(View.GONE);

                                Intent intent = new Intent(getBaseContext(), MainActivityWithoutFragment.class);
                                startActivity(intent);
                                finish();

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.login_error_toast), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", usernameEditText.getText().toString().trim());
                        params.put("password", passwordEditText.getText().toString().trim());
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                queue.add(jsonObjRequest);
            }
        });
    }
}
