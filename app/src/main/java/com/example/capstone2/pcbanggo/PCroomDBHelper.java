package com.example.capstone2.pcbanggo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeff on 2016-11-09.
 */

public class PCroomDBHelper extends SQLiteOpenHelper {

    String TABLE_NAME = "pcroomlist";
    String KEY_PCROOM = "pc_room";


    public PCroomDBHelper(Context context){super(context,"pcroomlist.db",null,1);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format(
                "CREATE TABLE %s ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "%s TEXT);", TABLE_NAME, KEY_PCROOM );
        sqLiteDatabase.execSQL( query );

        String[] PCrooms = {"3POP","Arachne","NET","Rainbow","ANDSOON"};

        for (String pcroom_name: PCrooms) {
            String insert = String.format(
                    "INSERT INTO %s VALUES (null, '%s');" , TABLE_NAME, pcroom_name );
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
