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

    public PCroomDBHelper(Context context){super(context,"pc.db",null,10);}

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

        String[] PCrooms = {"'3POP'","'Arachne'", "'Max'","'Choice'","Red"};
        int[] rows = {17, 5, 9, 4, 13};
        int[] cols = {17, 2, 12, 2, 12};

        String[] seats = {"'1 2 3 0 4 5 6 7 8 0 9 10 0 0 0 0 0 0 11 14 0 17 18 19 12 15 0 20 21 22 13 16 0 0 0 0'",
                "'1 2 0 3 4 5 6 0 7 8'",
                "'73 72 71 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 70 69 68 67 66 65 0 0 0 64 63 62 61 60 59 0 0 0 0 0 0 0 0 0 0 0 0 58 57 56 55 54 53 0 0 0 52 51 50 49 48 47 0 0 0 0 0 0 0 0 0 0 0 0 46 45 44 43 42 0 0 0 0 41 40 39 38 37 0 0 0 0 0 0 0 0 0 0 0 0 0 36 35 34 33 32 31 0 0 0 0 30 29 28 27 26 0 0 0 0 0 0 0 0 0 0 0 0 22 0 17 11 0 1 2 3 4 23 0 18 12 0 0 0 0 0 24 0 19 13 0 0 5 6 7 25 0 20 14 0 0 8 9 10'",
                "'1 2 3 4 5 6 7 8'",
                "'10 9 8 7 6 0 5 4 3 2 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 11 12 13 14 15 0 53 54 0 69 0 0 0 20 19 18 17 16 0 52 55 0 68 0 0 0 0 0 0 0 0 0 51 56 0 67 0 0 0 21 22 23 24 25 0 50 57 0 66 70 0 0 30 29 28 27 26 0 49 58 0 65 71 0 79 0 0 0 0 0 0 48 59 0 64 72 0 78 31 32 33 34 35 0 47 60 0 63 73 0 77 0 39 38 37 36 0 46 61 0 62 74 0 76 0 0 0 0 0 0 0 0 0 0 75 0 0 40 41 42 43 44 45 0 0 0 0 0 0 0'"};

      String[] orient = {"'1 1 1 0 1 1 1 1 1 0 1 1 0 0 0 0 0 0 2 2 0 1 1 1 2 2 0 1 1 1 2 2 0 0 0 0'",
                "'1 1 0 1 1 1 1 0 1 1'",
                "'1 1 0 1 1 1 1 0 1 1'",
                "'1 1 1 1 1 1 1 1'",
                "'1 1 1 1 1 0 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 0 1 1 0 1 0 0 0 1 1 1 1 1 0 1 1 0 1 0 0 0 0 0 0 0 0 0 1 1 0 1 0 0 0 1 2 2 2 2 0 1 1 0 1 1 0 0 1 1 1 2 2 0 1 1 0 1 1 0 1 0 0 0 0 0 0 1 1 0 1 1 0 1 1 2 1 1 1 0 1 1 0 1 1 0 1 0 1 1 1 1 0 1 1 0 2 1 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 1 1 2 1 1 1 0 0 0 0 0 0 0'"};

        String data = "0:79:-127:-1:-108:79:93:57:9:33:-77:63:0:0:0:0:0:0:0:0:0:0:@1:80:-99:-17:-2:127:-2:95:-1:-3:127:-128:0:0:0:0:0:0:0:0:0:0:";

        String[] tmp1 = data.split("@");
        String[] tmp2;
        int key;
        int max_seat;
        int seat;
        int temp;
        int cur_seat=0;
        String[] can_seats1= new String[tmp1.length];

        for(int i=0;i<tmp1.length;i++){
            StringBuffer sf = new StringBuffer();
            cur_seat =0; // 현재 자리 번호

            tmp2 = tmp1[i].split(":");
            key = Integer.parseInt(tmp2[0]); // pc방 id
            max_seat=Integer.parseInt(tmp2[1]); // pc방의 최대 좌석

            for(int j=2;j<=2+(max_seat/8);j++){
                for(int k=0;k<8;k++){
                    cur_seat++;
                    seat = Integer.parseInt(tmp2[j]);
                    temp = (seat << (k+24)) >>> 31;
                    String t = String.valueOf(temp);

                    if(cur_seat <= max_seat)
                        sf.append(t).append(" ");
                    can_seats1[i] = sf.toString();

                }
            }
            System.out.println(can_seats1[i]);
        }

        String[] can_seats = {"'0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0'",
                "'0 1 0 1 1 1 0 0'",
                "'0 0 0 0 1 1 0 0'",
                "'1 0 0 0 1 1 1 0'",
                "'"+can_seats1[0]+"'"};

        for (int i=0;i<5;i++) {
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

