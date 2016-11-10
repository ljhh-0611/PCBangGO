package com.example.capstone2.pcbanggo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 2016-11-02.
 */

public class MainListAdapter extends ArrayAdapter<String>{

    public MainListAdapter(Context context, ArrayList<String> objects) {
        super(context, R.layout.main_row_layout, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.main_row_layout,parent,false);

        String text = getItem(position);

        TextView textView = (TextView) view.findViewById(R.id.main_row_text);

        textView.setText(text);

        int layoutHeight = (view.findViewById(R.id.row_layout)).getHeight();

        TextView recentSeat = (TextView) view.findViewById(R.id.recent_seat);
        recentSeat.setText("Current Empty Seat: XX");

        TextView maxConsecSeat = (TextView) view.findViewById(R.id.max_consecutive_seat);
        maxConsecSeat.setText("Max-Consec. Seat: XX");

        return view;
    }
}
