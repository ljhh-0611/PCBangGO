package com.example.capstone2.pcbanggo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by skscp on 2016-11-16.
 */


public class MyView extends View {
    PCroomDBHelper p_helper;
    final static String seatSelect = "SELECT * FROM pc_seat";
    String KEY_PCROOM = "name";
    String KEY_ROW = "row";
    String KEY_COL = "col";
    String KEY_SEATMAP = "seat_map";
    String KEY_CANSEAT = "can_seat";
    String KEY_ORIENT = "seat_orient";
    public MyView(Context context, AttributeSet attrs) {
        super(context,attrs); // 부모의 인자값이 있는 생성자를 호출한다
        p_helper = new PCroomDBHelper(context);
    }

    @Override
    protected void onDraw(Canvas canvas) { // 화면을 그려주는 작업
        Paint paint = new Paint(); // 화면에 그려줄 도구를 셋팅하는 객체
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK); // 색상을 지정

        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK);
        paint2.setTextSize(50);
        setBackgroundColor(Color.WHITE); // 배경색을 지정

        SQLiteDatabase PCroomdb = p_helper.getWritableDatabase();
        Cursor cursor = PCroomdb.rawQuery(seatSelect,null);

        cursor.moveToFirst();

        final int row = cursor.getInt(cursor.getColumnIndex(KEY_ROW));
        final int col = cursor.getInt(cursor.getColumnIndex(KEY_COL));

        int[][] seat_map = new int[col][row];
        System.out.println(row+col);
        String[] seats = cursor.getString(cursor.getColumnIndex(KEY_SEATMAP)).split(" ");
        for (int j = 0; j < col; j++) {
            for (int k = 0; k < row; k++) {
                seat_map[j][k] = Integer.parseInt(seats[j * row + k]);
                System.out.print(seat_map[j][k] + " "); //데이터 확인차 출력
            }
            System.out.println(); //데이터 확인차 출력
        }


        String[] can_seats = cursor.getString(cursor.getColumnIndex(KEY_CANSEAT)).split(" ");
        int[] can_seat = new int[can_seats.length];
        for(int i=0;i<can_seats.length;i++){
            can_seat[i] = Integer.parseInt(can_seats[i]);
        }

        int x0 = 150 , x1 = 250;
        int y0 = 150 , y1 = 250;
        int i,j;
        for(i = 0; i < col ; i++) {
            for(j = 0 ; j < row; j++)
                if(seat_map[i][j] != 0){
                    if (can_seat[seat_map[i][j] - 1] != 0) {
                        paint.setColor(Color.RED);
                    }
                    else{
                        paint.setColor(Color.BLUE);
                    }
                    canvas.drawRect(x0+i*100, y0+j*100, x1+i*100, y1+j*100, paint);
                    canvas.drawText(seats[i * 6 + j],x0+i*100+25, y0+j*100+65, paint2);
                }
        }
    }
}


