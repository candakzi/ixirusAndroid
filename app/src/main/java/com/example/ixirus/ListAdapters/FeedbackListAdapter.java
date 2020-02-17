package com.example.ixirus.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ixirus.FeedbackListItem;
import com.example.ixirus.ListItem;
import com.example.ixirus.R;

import java.util.ArrayList;

public class FeedbackListAdapter extends BaseAdapter {
    Context context;
    ArrayList<FeedbackListItem> data;

    private static LayoutInflater inflater = null;

    public FeedbackListAdapter(Context context, ArrayList<FeedbackListItem> data) {
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
            vi = inflater.inflate(R.layout.row_feedback_list, null);

        TextView emailText = (TextView) vi.findViewById(R.id.emailText);
        TextView waitingText = (TextView) vi.findViewById(R.id.waitingText);
        TextView messageText = (TextView) vi.findViewById(R.id.messageText);
        TextView responseText = (TextView) vi.findViewById(R.id.responseText);

        emailText.setText(data.get(position).Email);
        if (data.get(position).Response.equals("-"))
            waitingText.setText("Waiting");
        messageText.setText(data.get(position).Message);
        responseText.setText(data.get(position).Response);

        return vi;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
