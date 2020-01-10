package com.example.ixirus.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ixirus.ListItem;
import com.example.ixirus.R;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_devplan_list, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        ImageView editImage = (ImageView) vi.findViewById(R.id.editImage);
        ImageView deleteImage = (ImageView) vi.findViewById(R.id.deleteImage);
        editImage.setImageResource(R.mipmap.edit_button);
        deleteImage.setImageResource(R.mipmap.delete_button);

        text.setText(data.get(position).Name);
        return vi;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
