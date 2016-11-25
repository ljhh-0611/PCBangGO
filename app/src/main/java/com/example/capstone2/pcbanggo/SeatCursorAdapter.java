package com.example.capstone2.pcbanggo;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jeff on 2016-11-11.
 */

public class SeatCursorAdapter extends CursorAdapter {

    String TABLE_NAME = "pc_seat";
    String KEY_PCROOM = "name";
    String KEY_ROW = "row";
    String KEY_COL = "col";
    String KEY_SEATMAP = "seat_map";
    String KEY_CANSEAT = "can_seat";
    String KEY_ORIENT = "seat_orient";
    int which;

    public SeatCursorAdapter(Context context, Cursor c, int which) { super(context, c, which); this.which = which; }

    int find_seat(int[][] seat_map, int[][] map_orient, int[] can_seat, int j, int k, int row, int col, int count) {
        if (j>=col||k>=row||seat_map[j][k]==0)
            return count;
        if (can_seat[seat_map[j][k]-1]!=0)
            return count;
        if (map_orient[j][k]==1)
            count = find_seat(seat_map, map_orient, can_seat, j, k + 1, row, col, count + 1);
        else if (map_orient[j][k]==2)
            count = find_seat(seat_map, map_orient, can_seat, j+1, k, row, col, count+1);

        return count;
    }

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
        //int[][] can_seat = new int[col][row];

        int maxl = 0;
        int count = 0;

        String[] seats = cursor.getString(cursor.getColumnIndex(KEY_SEATMAP)).split(" ");
        for (int j = 0; j < col; j++) {
            for (int k = 0; k < row; k++) {
                seat_map[j][k] = Integer.parseInt(seats[j * row + k]);
//                System.out.print(seat_map[j][k] + " "); //데이터 확인차 출력
            }
//            System.out.println(); //데이터 확인차 출력
        }

        seats = cursor.getString(cursor.getColumnIndex(KEY_ORIENT)).split(" ");
        for (int j = 0; j < col; j++) {
            for (int k = 0; k < row; k++) {
                map_orient[j][k] = Integer.parseInt(seats[j * row + k]);
            }
        }

        seats = cursor.getString(cursor.getColumnIndex(KEY_CANSEAT)).split(" ");
        int[] can_seat = new int[seats.length];
        for (int j = 0; j < seats.length; j++) {
            can_seat[j] = Integer.parseInt(seats[j]);
        }

        for (int j = 0; j < col; j++) {
            for (int k = 0; k < row; k++) {
                if (seat_map[j][k] == 0)
                    continue;
                int max = find_seat(seat_map, map_orient, can_seat, j, k, row, col, 0);
                if (max > maxl)
                    maxl = max;
            }
        }
        int i=0 ;
        while (i<can_seat.length) {
            if (can_seat[i] == 0) {
                count++;
            }
            i++;
        }

        if (maxl >= which) {
            String pcroom = cursor.getString(cursor.getColumnIndex(KEY_PCROOM));

            ImageView img = (ImageView) view.findViewById(R.id.imageView1);
            switch (pcroom) {
                case "3POP":
                    img.setImageDrawable(context.getDrawable(R.drawable.pc_1));
                    break;
                case "Arachne":
                    img.setImageDrawable(context.getDrawable(R.drawable.pc_2));
                    break;
                case "Max":
                    img.setImageDrawable(context.getDrawable(R.drawable.pc_3));
                    break;
                case "Choice":
                    img.setImageDrawable(context.getDrawable(R.drawable.pc_4));
                    break;
                case "Red":
                    img.setImageDrawable(context.getDrawable(R.drawable.pc_4));
                    break;
            }

            TextView pc_name, seat_info;
            pc_name = (TextView) view.findViewById(R.id.main_row_text);
            pc_name.setText(pcroom);
            seat_info = (TextView) view.findViewById(R.id.seat);
            seat_info.setText("남은 좌석: " + count + "\n" + "최대 연속 좌석 : " + maxl);
        }
        else {
            LinearLayout none = (LinearLayout)view.findViewById(R.id.text_box);
            none.setVisibility(View.GONE);
            ImageView img = (ImageView) view.findViewById(R.id.imageView1);
            img.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
    }
}
