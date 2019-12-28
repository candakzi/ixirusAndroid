package com.example.ixirus.ui.DevPlan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ixirus.ListAdapters.GenericListAdapter;
import com.example.ixirus.ListItem;
import com.example.ixirus.R;
import java.util.ArrayList;

public class CreateDevPlanActivity5 extends AppCompatActivity {
    private ListView lv1;
    private ListView lv2;
    private ListView lv3;
    private ListView lv4;
    private ListView lv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dev_plan5);
        lv1 = findViewById(R.id.listView);
        lv1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv1.setClickable(true);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

            }
        });

        lv2 = findViewById(R.id.listView2);
        lv2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv2.setClickable(true);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

            }
        });

        lv3 = findViewById(R.id.listView3);
        lv3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv3.setClickable(true);
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

            }
        });

        lv4 = findViewById(R.id.listView4);
        lv4.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv4.setClickable(true);
        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

            }
        });

        lv5 = findViewById(R.id.listView5);
        lv5.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv5.setClickable(true);
        lv5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

            }
        });

        ImageView imageView = findViewById(R.id.buttonBack);
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
        loadListItems();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateDevPlanActivity6.class);
                startActivity(intent);
//                SharedPreferences settings = getBaseContext().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
//                settings.edit().clear().commit();
            }
        });


    }

    public void loadListItems()
    {
        ArrayList<ListItem> arr = new ArrayList<ListItem>();
        String[] answers = new String[]{getResources().getString(R.string.answer_1),getResources().getString(R.string.answer_2),getResources().getString(R.string.answer_3)};
        for (int i=0; i < answers.length; i++) {
            ListItem item =  new ListItem();
            item.Id =  i+1;
            item.Name = answers[i];
            arr.add(item);
        }
        AnswersListAdapter adapter = new AnswersListAdapter(getBaseContext(),arr);
        lv1.setAdapter(adapter);
        lv2.setAdapter(adapter);
        lv3.setAdapter(adapter);
        lv4.setAdapter(adapter);
        lv5.setAdapter(adapter);

    }
}

 class AnswersListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ListItem>  data ;

    private static LayoutInflater inflater = null;

    public AnswersListAdapter(Context context, ArrayList<ListItem>  data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_small_answers, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(data.get(position).Name);
        return vi;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
