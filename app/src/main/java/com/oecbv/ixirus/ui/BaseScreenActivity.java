package com.oecbv.ixirus.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
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
import com.oecbv.ixirus.LanguageHelper;
import com.oecbv.ixirus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BaseScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_screen);
        ImageView imgView = findViewById(R.id.imageView7);
        imgView.setImageResource(R.mipmap.ixirus_logo_big);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences loginPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

                if (loginPreferences.contains("Token")) {
                    final String email = loginPreferences.getString("Email", null);
                    final String password = loginPreferences.getString("Password", null);

                    //Get valid token again
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
                            editor.putString("Email", email);
                            editor.putString("Password", password);
                            editor.apply();

                            final String firebaseToken = sp.getString("FirebaseToken", null);
                            sendRegistrationToServer((firebaseToken));

                            Intent intentMain = new Intent(getBaseContext(), MainActivityWithoutFragment.class);
                            intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intentMain);
                            BaseScreenActivity.this.overridePendingTransition(0, 0);
                            finish();

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NetworkError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                //Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                Intent intentMain = new Intent(getBaseContext(), LoginActivity.class);
                                intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentMain);
                                BaseScreenActivity.this.overridePendingTransition(0, 0);
                                finish();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NoConnectionError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.login_error_toast), Toast.LENGTH_SHORT).show();
                                Intent intentMain = new Intent(getBaseContext(), LoginActivity.class);
                                intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentMain);
                                BaseScreenActivity.this.overridePendingTransition(0, 0);
                                finish();
                            }

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    queue.add(jsonObjRequest);

                } else {

                    Intent intentMain = new Intent(getBaseContext(), LoginActivity.class);
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intentMain);
                    BaseScreenActivity.this.overridePendingTransition(0, 0);
                    finish();
                }
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences loginPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

                if (loginPreferences.contains("Token")) {
                    final String email = loginPreferences.getString("Email", null);
                    final String password = loginPreferences.getString("Password", null);

                    //Get valid token again
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
                            editor.putString("Email", email);
                            editor.putString("Password", password);
                            editor.apply();

                            final String firebaseToken = sp.getString("FirebaseToken", null);
                            sendRegistrationToServer((firebaseToken));


                            Intent intentMain = new Intent(getBaseContext(), MainActivityWithoutFragment.class);
                            intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intentMain);
                            BaseScreenActivity.this.overridePendingTransition(0, 0);
                            finish();

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NetworkError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                               // Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                Intent intentMain = new Intent(getBaseContext(), LoginActivity.class);
                                intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentMain);
                                BaseScreenActivity.this.overridePendingTransition(0, 0);
                                finish();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NoConnectionError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), getResources().getString(R.string.login_error_toast), Toast.LENGTH_SHORT).show();
                                Intent intentMain = new Intent(getBaseContext(), LoginActivity.class);
                                intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentMain);
                                BaseScreenActivity.this.overridePendingTransition(0, 0);
                                finish();
                            }

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    queue.add(jsonObjRequest);

                } else {

                    Intent intentMain = new Intent(getBaseContext(), LoginActivity.class);
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intentMain);
                    BaseScreenActivity.this.overridePendingTransition(0, 0);
                    finish();
                }
            }
        }, 500);
    }

    private void sendRegistrationToServer(final String token) {
        // TODO: Implement this method to send token to your app server.
        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/user/updateToken", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                } else
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.login_error_toast), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("deviceToken", token);
                return params;
            }

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
}
