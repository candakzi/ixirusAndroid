package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ixirus.CustomListItem;
import com.example.ixirus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MyDevPlanListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dev_plan_list);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity1.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });

    }
}
