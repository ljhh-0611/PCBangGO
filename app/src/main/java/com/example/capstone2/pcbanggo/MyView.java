package com.example.capstone2.pcbanggo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by skscp on 2016-11-16.
 */


public class MyView extends View {
    PCroomDBHelper p_helper;
    infoPC infoPC;
    final static String seatSelect = "SELECT * FROM pc_seat";
    String KEY_PCROOM = "name";
    String KEY_ROW = "row";
    String KEY_COL = "col";
    String KEY_SEATMAP = "seat_map";
    String KEY_CANSEAT = "can_seat";
    String KEY_ORIENT = "seat_orient";


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs); // 부모의 인자값이 있는 생성자를 호출한다
        p_helper = new PCroomDBHelper(context);
    }

    @Override
    protected void onDraw(Canvas canvas) { // 화면을 그려주는 작업
        Paint paint = new Paint(); // 화면에 그려줄 도구를 셋팅하는 객체
        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.BLACK); // 색상을 지정
        paint.setStrokeWidth(3);
        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK);
        paint2.setTextSize(40);
        setBackgroundColor(Color.WHITE); // 배경색을 지정

        SQLiteDatabase PCroomdb = p_helper.getWritableDatabase();
        Cursor cursor = PCroomdb.rawQuery(seatSelect, null);

        cursor.moveToFirst();

        String pcroom = infoPC.Pcroom;

        int id = 0;

        switch (pcroom) {
            case "3POP":
                id = 0;
                break;
            case "Arachne":
                id = 1;
                break;
            case "Max":
                id = 2;
                break;
            case "Choice":
                id = 3;
                break;
            case "Red":
                id = 4;
                canvas.drawRect(120,1100,200,1112,paint);
                paint.setColor(Color.parseColor("#64000000"));
                canvas.drawRect(925,600,1000,740,paint);
            default:
                break;
        }

        cursor.moveToPosition(id);

        final int row = cursor.getInt(cursor.getColumnIndex(KEY_ROW));
        final int col = cursor.getInt(cursor.getColumnIndex(KEY_COL));

        int[][] seat_map = new int[col][row];

        String[] seats = cursor.getString(cursor.getColumnIndex(KEY_SEATMAP)).split(" ");
        for (int j = 0; j < col; j++) {
            for (int k = 0; k < row; k++) {
                seat_map[j][k] = Integer.parseInt(seats[j * row + k]);
            }
        }


        String[] can_seats = cursor.getString(cursor.getColumnIndex(KEY_CANSEAT)).split(" ");
        int[] can_seat = new int[can_seats.length];
        for (int i = 0; i < can_seats.length; i++) {
            can_seat[i] = Integer.parseInt(can_seats[i]);
        }
        paint.setColor(Color.RED);

        int x0 = 100, x1 = 175;
        int y0 = 100, y1 = 175;

        int i, j;
        for (i = 0; i < col; i++) {
            for (j = 0; j < row; j++) {
                if (seat_map[i][j] != 0) {
                    if (can_seat[seat_map[i][j] - 1] != 0) { // 사용중인 자리
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        paint.setColor(Color.parseColor("#55C033"));
                        paint2.setColor(Color.WHITE);
//                        paint.setStro
                    } else { //사용중이 아닌 자리
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(Color.parseColor("#55C033"));
                        paint2.setColor(Color.parseColor("#B5000000"));
                    }
                    canvas.drawRect(x0 + i * 75, y0 + j * 75, x1 + i * 75, y1 + j * 75, paint);
                    canvas.drawText(seats[i * row + j], x0 + i * 75 + 20, y0 + j * 75 + 50, paint2);
                }
            }
        }
    }
}

