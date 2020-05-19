package com.example.ixirus.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.ixirus.ListItem;
import com.example.ixirus.R;
import com.example.ixirus.ui.DevPlan.CreateDevPlanActivity2;
import com.example.ixirus.ui.Settings.SettingsActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements TextWatcher {

    private EditText emailEdit;
    private Button okButton;
    private EditText nameEdit;
    private EditText lastNameEdit;
    private EditText passwordEdit;
    private EditText companyCode;
    private Boolean lastState;
    private TextView textViewGdpr;
    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailEdit = findViewById(R.id.email);
        nameEdit = findViewById(R.id.name);
        lastNameEdit = findViewById(R.id.lastName);
        passwordEdit = findViewById(R.id.password);
        companyCode = findViewById(R.id.companyCode);
        textViewGdpr = findViewById(R.id.textViewGdpr);
        okButton = (Button) findViewById(R.id.signUp);
        emailEdit.addTextChangedListener(this);
        nameEdit.addTextChangedListener(this);
        lastNameEdit.addTextChangedListener(this);
        passwordEdit.addTextChangedListener(this);
        ConstraintLayout layout = findViewById(R.id.rootView);


        final BottomSheetDialog bsDialog = new BottomSheetDialog(SignUpActivity.this);
        bsDialog.setContentView(R.layout.web_view_dialog);
        bsDialog.setCancelable(false);
        bsDialog.setCanceledOnTouchOutside(true);

        wv = bsDialog.findViewById(R.id.WebView);
        String pdf;
        if (new LanguageHelper().getLanguage().equals("1"))
            pdf = "https://ixirusblob.blob.core.windows.net/ixirus-files/f5fe200004b845d1a3207a46d942d499.pdf";
        else
            pdf = "https://ixirusblob.blob.core.windows.net/ixirus-files/193e164b4e084eed9dad07d94306bdac.pdf";

        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setBuiltInZoomControls(false);
        wv.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);


        textViewGdpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialog.show();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialog.hide();
            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Integer companyCodeInt;
                if(companyCode.getText().toString().trim().matches("")){
                    companyCodeInt = 0;
                }
                else
                {
                    companyCodeInt = Integer.parseInt(companyCode.getText().toString().trim());
                }
                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, "https://ixirus.azurewebsites.net/api/user/register", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.login_credentials), Toast.LENGTH_SHORT).show();
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
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.auth_failure_error), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.timeout_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Email", emailEdit.getText().toString().trim());
                        params.put("FirstName", nameEdit.getText().toString().trim());
                        params.put("LastName", lastNameEdit.getText().toString().trim());
                        params.put("Password", passwordEdit.getText().toString().trim());
                        params.put("CompanyCode", companyCodeInt.toString());

                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                queue.add(jsonObjRequest);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == emailEdit.getEditableText()) {
            if (!isEmailValid(s.toString())) {
                emailEdit.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.sign_up_text_background_error));
                getStatusOfButton();
            } else {
                emailEdit.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.sign_up_text_background));
                getStatusOfButton();
            }
        } else if (s == nameEdit.getEditableText()) {

            if (s.toString().matches("")) {
                nameEdit.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.sign_up_text_background_error));
                getStatusOfButton();
            } else {
                getStatusOfButton();
                nameEdit.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.sign_up_text_background));

            }

        } else if (s == lastNameEdit.getEditableText()) {

            if (s.toString().matches("")) {
                lastNameEdit.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.sign_up_text_background_error));
                getStatusOfButton();
            } else {
                    lastNameEdit.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.sign_up_text_background));
                getStatusOfButton();
            }
        } else if (s == passwordEdit.getEditableText()) {

            if (s.toString().matches("")) {
                passwordEdit.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.sign_up_text_background_error));
                getStatusOfButton();
            } else {
                passwordEdit.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.sign_up_text_background));
                getStatusOfButton();
            }
        }
    }

    private void getStatusOfButton() {
        if (nameEdit.getText().toString().trim().matches("") ||
                lastNameEdit.getText().toString().trim().matches("") ||
                passwordEdit.getText().toString().trim().matches("") ||
                !isEmailValid(emailEdit.getText().toString().trim())
        ) {
            okButton.setBackgroundResource(R.color.colorButtonDisabled);
            okButton.setEnabled(false);
        }
        else{
            okButton.setBackgroundResource(R.color.colorPrimary);
            okButton.setEnabled(true);
        }
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }
}
