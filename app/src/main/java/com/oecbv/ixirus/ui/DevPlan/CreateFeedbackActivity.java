package com.oecbv.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.oecbv.ixirus.LanguageHelper;
import com.oecbv.ixirus.R;
import com.oecbv.ixirus.ui.BaseScreenActivity;

import java.util.HashMap;
import java.util.Map;

public class CreateFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feedback);
        ImageView imageView = findViewById(R.id.buttonBack);
        getWindow().setBackgroundDrawableResource(R.mipmap.background_development_plan);

        final TextView tv = (TextView) findViewById(R.id.textView2);
        final ImageView refreshImage = (ImageView) findViewById(R.id.refreshIco);
        final Button nextButton = (Button) findViewById(R.id.button);
        final EditText emailText = (EditText) findViewById(R.id.editTextEmail);
        final EditText messageText = (EditText) findViewById(R.id.editTextMessage);
        Bundle extras = getIntent().getExtras();
        final String devPlanId = extras.getString("devPlanId");

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
        SharedPreferences sp = getSharedPreferences("LoginPrefs", Activity.MODE_PRIVATE);
        final String savedToken = sp.getString("Token", null);

        final View activityRootView = findViewById(R.id.rootView);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > dpToPx(getBaseContext(), 200)) { // if more than 200 dp, it's probably a keyboard...
                    nextButton.setVisibility(View.GONE);
                } else
                    nextButton.setVisibility(View.VISIBLE);
            }
        });

        float density = getResources().getDisplayMetrics().density;
        tv.setTextSize(9 * density);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentEmailText = emailText.getText().toString().trim();
                final String currentMesageText = messageText.getText().toString().trim();

                if (currentEmailText.matches(""))
                    return;
                else {
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/feedback", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.feedback_request_sent), Toast.LENGTH_SHORT).show();
                            onBackPressed();
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
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("DevPlanId", devPlanId);
                            params.put("Email", currentEmailText);
                            params.put("Message", currentMesageText);

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
        });
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }
}
