package com.example.capstone2.pcbanggo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jeff on 2016-11-02.
 */

public class MainListAdapter extends BaseAdapter{

    Context context;
    int layout;
    ArrayList<ListViewItem> lv;
    LayoutInflater inf;
    public MainListAdapter(Context context, int layout, ArrayList<ListViewItem> lv) {
        this.context = context;
        this.layout = layout;
        this.lv = lv;
        inf = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return lv.size();
    }
    @Override
    public Object getItem(int position) {
        return lv.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = inf.inflate(layout, null);
        }

        ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
        TextView PcName = (TextView)convertView.findViewById(R.id.main_row_text);
        TextView SeatInfo = (TextView)convertView.findViewById(R.id.seat);

        ListViewItem m = lv.get(position);
        iv.setImageResource(m.iconDrawable);
        PcName.setText(m.titleStr);
        SeatInfo.setText(m.descStr);

        return convertView;
    }
}
