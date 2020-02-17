package com.example.ixirus.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ixirus.FeedbackListItem;
import com.example.ixirus.MessagesListItem;
import com.example.ixirus.R;

import java.util.ArrayList;

public class MessagesListAdapter extends BaseAdapter {
    Context context;
    ArrayList<MessagesListItem> data;

    private static LayoutInflater inflater = null;

    public MessagesListAdapter(Context context, ArrayList<MessagesListItem> data) {
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
            vi = inflater.inflate(R.layout.row_messages_list, null);

        TextView messageText = (TextView) vi.findViewById(R.id.messageText);
        TextView dateText = (TextView) vi.findViewById(R.id.dateText);
        messageText.setText(data.get(position).Message);
        dateText.setText(data.get(position).Date);
        return vi;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
