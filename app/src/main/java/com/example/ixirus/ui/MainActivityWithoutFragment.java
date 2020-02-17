package com.example.ixirus.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ixirus.CustomListItem;
import com.example.ixirus.ListAdapters.MainListAdapter;
import com.example.ixirus.R;
import com.example.ixirus.ui.DevPlan.MessagesActivity;
import com.example.ixirus.ui.DevPlan.MyDevPlanListActivity;
import com.example.ixirus.ui.DevPlan.NotificationsActivity;
import com.example.ixirus.ui.DevSource.DevSourceListActivity;

public class MainActivityWithoutFragment extends AppCompatActivity  {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_without_fragment);
        lv = findViewById(R.id.listView);

        CustomListItem item1 =  new CustomListItem();
        item1.Name = getString(R.string.menu_development_plan);
        item1.Drawable = ContextCompat.getDrawable(getApplicationContext(),R.mipmap.development_plan);
        item1.Activity = new MyDevPlanListActivity();

        CustomListItem item3 =  new CustomListItem();
        item3.Name = getString(R.string.menu_messages);
        item3.Drawable = ContextCompat.getDrawable(getApplicationContext(),R.mipmap.messages);
        item3.Activity = new MessagesActivity();

        CustomListItem item4 =  new CustomListItem();
        item4.Name =  getString(R.string.notifications);
        item4.Drawable = ContextCompat.getDrawable(getApplicationContext(),R.mipmap.feedbacks);
        item4.Activity = new NotificationsActivity();

//        CustomListItem item5 =  new CustomListItem();
//        item5.Name = getString(R.string.menu_xperince_sharing);
//        item5.Drawable = ContextCompat.getDrawable(getApplicationContext(),R.mipmap.xperience_sharing);
//        item5.Activity = new MyDevPlanListActivity();


        CustomListItem item6 =  new CustomListItem();
        item6.Name =  getString(R.string.menu_dev_sources);
        item6.Drawable = ContextCompat.getDrawable(getApplicationContext(),R.mipmap.dev_sources);
        item6.Activity = new DevSourceListActivity();

        CustomListItem[] cListItems  = new CustomListItem[]{item1,item3,item4,item6};

        lv.setAdapter(new MainListAdapter(this,cListItems));

        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = lv.getItemAtPosition(position);
                CustomListItem item = (CustomListItem)o;
                Intent intent = new Intent(getBaseContext(), item.Activity.getClass());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
