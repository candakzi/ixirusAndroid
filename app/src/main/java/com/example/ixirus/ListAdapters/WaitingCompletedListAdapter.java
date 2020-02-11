package com.example.ixirus.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ixirus.ListItem;
import com.example.ixirus.R;

import java.util.ArrayList;

public class WaitingCompletedListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ListItem>  data ;

    private static LayoutInflater inflater = null;

    public WaitingCompletedListAdapter(Context context, ArrayList<ListItem>  data) {
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_waiting_completed_actions_list, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        Button completedBtn = (Button) vi.findViewById(R.id.btnMarkCompleted);
        Button unCompletedBtn = (Button) vi.findViewById(R.id.btnMarkUncompleted);

        if(((ListView) parent).getTag().toString().equals("listViewWaiting")) {
            completedBtn.setVisibility(View.VISIBLE);
            unCompletedBtn.setVisibility(View.GONE);

        }
        else {
            completedBtn.setVisibility(View.GONE);
            unCompletedBtn.setVisibility(View.VISIBLE);
        }

        text.setText(data.get(position).Name);

        completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
            }
        });

        unCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
            }
        });

        return vi;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
