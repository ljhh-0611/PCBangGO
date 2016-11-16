package com.example.capstone2.pcbanggo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeff on 2016-11-09.
 */

public class PCroomDBHelper extends SQLiteOpenHelper {

    String TABLE_NAME = "pc_seat";
    String KEY_PCROOM = "name";
    String KEY_ROW = "row";
    String KEY_COL = "col";
    String KEY_SEATMAP = "seat_map";
    String KEY_ORIENT = "seat_orient";
    String KEY_CANSEAT = "can_seat";

    public PCroomDBHelper(Context context){super(context,"pcbang.db",null,8);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format(
                "CREATE TABLE %s ("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +
                        "%s INTEGER, " +
                        "%s INTEGER, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT);", TABLE_NAME, KEY_PCROOM, KEY_ROW, KEY_COL, KEY_SEATMAP, KEY_ORIENT, KEY_CANSEAT );
        sqLiteDatabase.execSQL( query );

        String[] PCrooms = {"'3POP'","'Arachne'", "'Max'","'Choice'"};
        int[] rows = {6, 5, 5, 4};
        int[] cols = {6, 2, 2, 2};
        String[] seats = {"'1 2 3 0 4 5 6 7 8 0 9 10 0 0 0 0 0 0 11 14 0 17 18 19 12 15 0 20 21 22 13 16 0 0 0 0'",
                "'1 2 0 3 4 5 6 0 7 8'",
                "'1 2 0 3 4 5 6 0 7 8'",
                "'1 2 3 4 5 6 7 8'"};
        String[] orient = {"'1 1 1 0 1 1 1 1 1 0 1 1 0 0 0 0 0 0 2 2 0 1 1 1 2 2 0 1 1 1 2 2 0 0 0 0'",
                "'1 1 0 1 1 1 1 0 1 1'",
                "'1 1 0 1 1 1 1 0 1 1'",
                "'1 1 1 1 1 1 1 1'"};
        String[] can_seats = {"'1 1 0 0 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0'",
                "'0 1 0 1 1 1 0 0'",
                "'0 0 0 0 1 1 0 0'",
                "'1 0 0 0 1 1 1 0'"};

        for (int i=0;i<4;i++) {
            String insert = String.format(
                    "INSERT INTO %s VALUES (null, %s, %d, %d, %s, %s, %s);" ,TABLE_NAME , PCrooms[i], rows[i], cols[i],
                                                                seats[i], orient[i], can_seats[i]);
            /*String insert = "INSERT INTO pc_seat VALUES (null, '3POP', 6, 6, '1 2 3 0 4 5 6 7 8 0 9 10 0 0 0 0 0 0 11 14 0 17 18 19 12 15 0 20 21 22 13 16 0 0 0 0'," +
                    " '1 1 1 0 1 1 1 1 1 0 1 1 0 0 0 0 0 0 2 2 0 1 1 1 2 2 0 1 1 1 2 2 0 0 0 0', '0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0' );"; */
            sqLiteDatabase.execSQL(insert);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String query = String.format( "DROP TABLE IF EXISTS %s", TABLE_NAME );
        sqLiteDatabase.execSQL( query );
        onCreate( sqLiteDatabase );
    }
}

