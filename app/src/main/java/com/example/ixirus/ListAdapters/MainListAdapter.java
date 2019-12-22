package com.example.ixirus.ListAdapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ixirus.CustomListItem;
import com.example.ixirus.R;

public class MainListAdapter extends BaseAdapter {

    Context context;
    CustomListItem[] data2 ;

    private static LayoutInflater inflater = null;
    private int finalHeight;

    public MainListAdapter(Context context,CustomListItem[] data2) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data2 = data2;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data2.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data2[position];
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
            vi = inflater.inflate(R.layout.row_big, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        ImageView image = (ImageView)vi. findViewById(R.id.imageView3);
        ImageView arrowImage = (ImageView)vi. findViewById(R.id.imageViewArrow);

        text.setText(data2[position].Name);
        image.setImageDrawable(data2[position].Drawable);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (((Activity)context).getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,((Activity)context).getResources().getDisplayMetrics());
        }


        int finalHeight = height-(actionBarHeight+100); /// 100 dplik uzunluk ekleniyor
        
        text.getLayoutParams().height = finalHeight/5;
        image.getLayoutParams().height = finalHeight/5;
        arrowImage.getLayoutParams().height = finalHeight/5;

        return vi;
    }
}
