package com.example.capstone2.pcbanggo;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jeff on 2016-11-11.
 */

public class SeatCursorAdapter extends CursorAdapter {

    String KEY_PCROOM = "name";
    String KEY_ROW = "row";
    String KEY_COL = "col";
    String KEY_SEATMAP = "seat_map";
    String KEY_CANSEAT = "can_seat";
    String KEY_ORIENT = "seat_orient";

    public SeatCursorAdapter(Context context, Cursor c) { super(context, c); }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate( R.layout.main_row_layout, viewGroup, false);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final int row = cursor.getInt(cursor.getColumnIndex(KEY_ROW));
        final int col = cursor.getInt(cursor.getColumnIndex(KEY_COL));

        int[][] seat_map = new int[col][row];
        int[][] map_orient = new int[col][row];
        int[][] can_seat = new int[col][row];

        String[] seats = cursor.getString(cursor.getColumnIndex(KEY_SEATMAP)).split(" ");
        for(int j=0;j<col;j++){
            for(int k=0;k<row;k++){
                seat_map[j][k] = Integer.parseInt(seats[j*row+k]);
                System.out.print(seat_map[j][k]+" ");
            }
            System.out.println();
        }

        seats = cursor.getString(cursor.getColumnIndex(KEY_ORIENT)).split(" ");
        for(int j=0;j<col;j++){
            for(int k=0;k<row;k++){
                map_orient[j][k] = Integer.parseInt(seats[j*row+k]);
            }
        }

        seats = cursor.getString(cursor.getColumnIndex(KEY_CANSEAT)).split(" ");
        for(int j=0;j<col;j++){
            for(int k=0;k<row;k++){
                can_seat[j][k] = Integer.parseInt(seats[j*row+k]);
            }
        }

        ImageView img = (ImageView) view.findViewById(R.id.imageView1);
        img.setImageDrawable(context.getDrawable(R.drawable.pc_1));

        TextView pc_name, seat_info;
        pc_name = (TextView) view.findViewById(R.id.main_row_text);
        pc_name.setText(cursor.getString(cursor.getColumnIndex(KEY_PCROOM)));
        seat_info = (TextView) view.findViewById(R.id.seat);
        seat_info.setText("남은 좌석: "+seats[0]);

    }
}