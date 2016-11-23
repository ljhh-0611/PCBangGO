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

    public PCroomDBHelper(Context context){super(context,"pcbang.db",null,11);}

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


            String receive = "0:79:-127:-1:-108:79:93:57:9:33:-77:63:0:0:0:0:0:0:0:0:0:0" +
                    ":@1:80:-99:-17:-2:127:-2:95:-1:-3:127:-128:0:0:0:0:0:0:0:0:0:0:" +
                "@2:80:-99:-17:-2:127:-2:95:-1:-3:127:-128:0:0:0:0:0:0:0:0:0:0:" +
                "@3:80:-99:-17:-2:127:-2:95:-1:-3:127:-128:0:0:0:0:0:0:0:0:0:0:";


        String[] PCrooms = {"'3POP'","'Arachne'", "'Max'","'Choice'"};
        int[] rows = {10, 16, 16, 16};
        int[] cols = {15, 9, 9, 9};
        String[] seats = {"'1 2 3 0 4 5 6 7 0 13 " +
                          "6 7 8 0 9 10 11 12 0 14 " +
                          "0 0 0 0 0 0 0 0 0 15 " +
                          "18 19 20 0 21 22 23 24 0 16 " +
                          "25 26 27 0 28 29 30 31 0 17 " +
                          "0 0 0 0 0 0 0 0 0 0 " +
                          "32 33 34 35 0 36 37 38 39 40 " +
                          "41 42 43 44 0 45 46 47 48 49 " +
                          "0 0 0 0 0 0 0 0 0 0 " +
                          "50 51 52 53 0 54 55 56 57 58 " +
                          "59 60 61 62 0 63 64 65 66 67 " +
                          "0 0 0 0 0 0 0 0 0 0 " +
                          "68 69 70 71 0 72 73 0 0 0 " +
                          "74 75 76 77 0 78 79 0 0 0 " +
                          "0 0 0 0 0 0 0 0 0 0'",
                ///////////////////////////////////////////////////////////
                            "'1 2 3 4 5 0 6 7 8 9 10 0 21 26 0 31 " +
                            "11 12 13 14 15 0 16 17 18 19 20 0 22 27 0 32 " +
                            "0 0 0 0 0 0 0 0 0 0 0 0 23 28 0 33 " +
                            "36 37 38 39 40 0 41 42 43 44 45 0 24 29 0 34 " +
                            "46 47 48 49 50 0 51 52 53 54 55 0 25 30 0 35 " +
                            "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 " +
                            "56 57 58 59 60 0 61 62 63 64 65 0 76 79 0 0 " +
                            "66 67 68 69 70 0 71 72 73 74 75 0 77 80 0 0 " +
                            "0 0 0 0 0 0 0 0 0 0 0 0 78 0 0 0'",
                ////////////////////////////////////////////////////////////
                "'1 2 3 4 5 0 6 7 8 9 10 0 21 26 0 31 " +
                        "11 12 13 14 15 0 16 17 18 19 20 0 22 27 0 32 " +
                        "0 0 0 0 0 0 0 0 0 0 0 0 23 28 0 33 " +
                        "36 37 38 39 40 0 41 42 43 44 45 0 24 29 0 34 " +
                        "46 47 48 49 50 0 51 52 53 54 55 0 25 30 0 35 " +
                        "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 " +
                        "56 57 58 59 60 0 61 62 63 64 65 0 76 79 0 0 " +
                        "66 67 68 69 70 0 71 72 73 74 75 0 77 80 0 0 " +
                        "0 0 0 0 0 0 0 0 0 0 0 0 78 0 0 0'",
                //////////////////////////////////////////////////////////
                "'1 2 3 4 5 0 6 7 8 9 10 0 21 26 0 31 " +
                        "11 12 13 14 15 0 16 17 18 19 20 0 22 27 0 32 " +
                        "0 0 0 0 0 0 0 0 0 0 0 0 23 28 0 33 " +
                        "36 37 38 39 40 0 41 42 43 44 45 0 24 29 0 34 " +
                        "46 47 48 49 50 0 51 52 53 54 55 0 25 30 0 35 " +
                        "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 " +
                        "56 57 58 59 60 0 61 62 63 64 65 0 76 79 0 0 " +
                        "66 67 68 69 70 0 71 72 73 74 75 0 77 80 0 0 " +
                        "0 0 0 0 0 0 0 0 0 0 0 0 78 0 0 0'"};
        String[] orient = {"'1 1 1 0 1 1 1 1 0 2 " +
                            "1 1 1 0 1 1 1 1 0 2 " +
                            "0 0 0 0 0 0 0 0 0 2 " +
                            "1 1 1 0 1 1 1 1 0 2 " +
                            "1 1 1 0 1 1 1 1 0 2 " +
                            "0 0 0 0 0 0 0 0 0 0 " +
                            "1 1 1 1 0 1 1 1 1 1 " +
                            "1 1 1 1 0 1 1 1 1 1 " +
                            "0 0 0 0 0 0 0 0 0 0 " +
                            "1 1 1 1 0 1 1 1 1 1 " +
                            "1 1 1 1 0 1 1 1 1 1 " +
                            "0 0 0 0 0 0 0 0 0 0 " +
                            "1 1 1 1 0 1 1 0 0 0 " +
                            "1 1 1 1 0 1 1 0 0 0 " +
                            "0 0 0 0 0 0 0 0 0 0'",
                ////////////////////////////////////////
                        "'1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "0 0 0 0 0 0 0 0 0 0 0 0 2 2 0 2 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 0 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 0 " +
                         "0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0'",
                ////////////////////////////////////////
                        "'1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "0 0 0 0 0 0 0 0 0 0 0 0 2 2 0 2 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 0 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 0 " +
                         "0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0'",
                //////////////////////////////////////////
                        "'1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "0 0 0 0 0 0 0 0 0 0 0 0 2 2 0 2 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 2 " +
                         "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 0 " +
                         "1 1 1 1 1 0 1 1 1 1 1 0 2 2 0 0 " +
                         "0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0'"};

        // can_seat 생성하기
        //**************************************************************
        String[] can_seats = {"'3POP'","'Arachne'", "'Max'","'Choice'"};
        String[] cut_at = receive.split("@");
        for (int i=0;i < 4;i++) {
            String[] cut_colon = cut_at[i].split(":");
            int limit = Integer.parseInt(cut_colon[1]);
            StringBuilder buff = new StringBuilder();
            buff.append("'");
            int count = 0;
            for(int j=2;j<limit/8+3;j++) {
                byte seat = Byte.parseByte(cut_colon[j]);
                for(int k=0;k<8;k++) {
                    if(count>=limit) break;
                    buff.append((seat & 1)+" ");
                    seat = (byte) (seat>>>1);
                    count ++;
                }
            }
            buff.deleteCharAt(buff.length()-1);
            buff.append("'");
            can_seats[i] = buff.toString();
        }
        //**************************************************************

        /*
        String[] can_seats = {"'1 1 0 0 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0'",
                "'0 1 0 1 1 1 0 0'",
                "'0 0 0 0 1 1 0 0'",
                "'1 0 0 0 1 1 1 0'"};
           */
        for (int i=0;i<4;i++) {
            String insert = String.format(
                    "INSERT INTO %s VALUES (null, %s, %d, %d, %s, %s, %s);" ,TABLE_NAME , PCrooms[i], rows[i], cols[i],
                                                                seats[i], orient[i], can_seats[i]);

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

