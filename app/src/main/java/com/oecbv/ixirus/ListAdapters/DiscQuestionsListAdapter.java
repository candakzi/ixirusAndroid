package com.oecbv.ixirus.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oecbv.ixirus.ListItemDiscQuestions;
import com.oecbv.ixirus.R;

import java.util.ArrayList;

public class DiscQuestionsListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ListItemDiscQuestions>  data ;

    private static LayoutInflater inflater = null;

    public DiscQuestionsListAdapter(Context context, ArrayList<ListItemDiscQuestions>  data) {
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
