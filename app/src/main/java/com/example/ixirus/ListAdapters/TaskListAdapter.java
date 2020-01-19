package com.example.ixirus.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ixirus.ListItemTasks;
import com.example.ixirus.R;

import java.util.ArrayList;

public class TaskListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ListItemTasks>  data ;

    private static LayoutInflater inflater = null;

    public TaskListAdapter(Context context, ArrayList<ListItemTasks>  data) {
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
        MyDevPlanListAdapter.ViewHolder holder;
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_small_with_delete, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        holder = new MyDevPlanListAdapter.ViewHolder();
        text.setText(data.get(position).Name);
        ImageView deleteImage = (ImageView) vi.findViewById(R.id.deleteImage);
        deleteImage.setImageResource(R.mipmap.delete_button);

        holder.deleteImage = deleteImage;

        deleteImage.setOnClickListener(new View.OnClickListener() {
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
