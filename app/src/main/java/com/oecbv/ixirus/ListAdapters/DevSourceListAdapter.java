package com.oecbv.ixirus.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oecbv.ixirus.ListItemSources;
import com.oecbv.ixirus.R;

import java.util.ArrayList;

public class DevSourceListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ListItemSources>  data ;

    private static LayoutInflater inflater = null;

    public DevSourceListAdapter(Context context, ArrayList<ListItemSources>  data) {
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
            vi = inflater.inflate(R.layout.row_devsources, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        TextView textDescription = (TextView) vi.findViewById(R.id.textDescription);
        ImageView sourceIcon = (ImageView) vi.findViewById(R.id.imageView3);

        text.setText(data.get(position).Name);
        textDescription.setText(data.get(position).Description);
        sourceIcon.setImageDrawable(data.get(position).Drawable);
        return vi;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
