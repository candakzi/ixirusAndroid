package com.oecbv.ixirus.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.oecbv.ixirus.ListItem;
import com.oecbv.ixirus.R;

import java.util.ArrayList;

public class MyDevPlanListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ListItem> data ;

    private static LayoutInflater inflater = null;

    public MyDevPlanListAdapter(Context context, ArrayList<ListItem>  data) {
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
        ViewHolder holder;
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_devplan_list_test, null);

        holder = new ViewHolder();
        TextView text = (TextView) vi.findViewById(R.id.text);
        //Button editBtn = (Button) vi.findViewById(R.id.btnEdit);
        Button deleteBtn = (Button) vi.findViewById(R.id.btnDelete);
        Button summaryButton = (Button) vi.findViewById(R.id.btnSummary);
        Button viewButton = (Button) vi.findViewById(R.id.btnView);

        holder.deleteBtn = deleteBtn;
        //holder.editBtn = editBtn;
        holder.summaryBtn = summaryButton;
        holder.viewBtn = viewButton;

        text.setText(data.get(position).Name);

//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
//            }
//        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
            }
        });

        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
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

    class ViewHolder {
        public Button deleteBtn;
        public Button editBtn;
        public Button summaryBtn;
        public Button viewBtn;

    }
}
