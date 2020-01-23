package com.example.ixirus.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ixirus.BottomSheetListView;
import com.example.ixirus.ListItem;
import com.example.ixirus.ListItemSources;
import com.example.ixirus.R;

import java.util.ArrayList;

public class SourceListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ListItemSources>  data ;

    private static LayoutInflater inflater = null;

    public SourceListAdapter(Context context, ArrayList<ListItemSources>  data) {
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
            vi = inflater.inflate(R.layout.row_sources_devplan, null);
        TextView text =  vi.findViewById(R.id.text);
        holder = new ViewHolder();
        TextView textDescription =  vi.findViewById(R.id.textDescription);
        ImageView sourceIcon =  vi.findViewById(R.id.imageView3);
        Button previewButton = vi.findViewById(R.id.buttonPreview);
        Button selectButton = vi.findViewById(R.id.buttonSelectSource);

        holder.previewButton = previewButton;
        holder.chooseButton = selectButton;

        text.setText(data.get(position).Name);
        textDescription.setText(data.get(position).Description);
        sourceIcon.setImageDrawable(data.get(position).Drawable);

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
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
        public Button previewButton;
        public Button chooseButton;
    }
}
