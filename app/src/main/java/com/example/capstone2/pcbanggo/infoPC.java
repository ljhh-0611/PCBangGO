package com.example.capstone2.pcbanggo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by skscp on 2016-11-03.
 */

public class infoPC extends AppCompatActivity {

    public static String Pcroom = null;

    Timer refresh;

    View myinfo;

    asyncPHP task;
    String result;
    private int xDelta, yDelta;
    int[] rows = {22, 16, 9, 13, 13};
    int[] cols = {17, 9, 18, 10, 12};
    int row,col;

    PCroomDBHelper pcroomDBHelper;
    SQLiteDatabase pcroomDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_pc);
        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        Pcroom =intent.getStringExtra("title");
        switch (Pcroom) {
            case "3POP":
                row = rows[0];col=cols[0];break;
            case "Gallery":
                row = rows[1];col=cols[1];break;
            case "Max":
                row = rows[2];col=cols[2];break;
            case "Choice":
                row = rows[3];col=cols[3];break;
            case "Red":
                row = rows[4];col=cols[4];break;
        }
        pcroomDBHelper = new PCroomDBHelper(this);
        pcroomDB = pcroomDBHelper.getWritableDatabase();

        View info = (View) getLayoutInflater().inflate(R.layout.info_pc, null);
        myinfo = (View) info.findViewById(R.id.myView);
        final int x1 = 175 +col*75 - 40;
        final int y1 = 175 +row * 75 - 40;
        System.out.println("x1, y1" + x1 + " " + y1);
        myinfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) (event.getRawX()); // 화면상에서의 좌표
                final int Y = (int) (event.getRawY());
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        xDelta = (int) (X - myinfo.getTranslationX()); // delta : 그림상에서의 좌표
                        yDelta = (int) (Y - myinfo.getTranslationY());
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                            if ((X - xDelta<=0)&&(X-xDelta>=500-x1))
                            myinfo.setTranslationX(X - xDelta);
                            if (Y-yDelta<= 0&&(Y-yDelta>=800-y1))
                            myinfo.setTranslationY(Y - yDelta);

                        break;
                }
                return true;
            }
        });
        setContentView(info);

        TextView pcTitle = (TextView)findViewById(R.id.textView1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        pcTitle.setText(Pcroom);

        ImageView img = (ImageView) findViewById(R.id.imageView1);
        switch (Pcroom) {
            case "3POP":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_1));
                break;
            case "Gallery":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_2));
                break;
            case "Max":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_3));
                break;
            case "Choice":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_4));
                break;
            case "Red":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_5));
                break;
        }
        pcroomDB.close();
        pcroomDBHelper.close();
    } // end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
        refresh = new Timer();
        refresh.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        task = new asyncPHP();
                        task.execute();
                        myinfo.invalidate();
                    }
                });
            }
        },0,4000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        refresh.cancel();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.pcroominfo) {
            Intent intent = new Intent(getApplicationContext(),infoPCroom.class);
            intent.putExtra("title",Pcroom);
            startActivity(intent);
            return true;
        }
 /*       if (id == R.id.pc) {
            return true;
        }
        if (id == R.id.info) {
            return true;
        }
        if (id == R.id.phone) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    void update(String receive){

        pcroomDB = pcroomDBHelper.getWritableDatabase();

        String[] PCrooms = {"'3POP'","'Gallery'", "'Max'","'Choice'","'Red'"};
        String[] can_seats = {"'3POP'","'Gallery'", "'Max'","'Choice'","'Red'"};
        String[] cut_at = receive.split("@");
        for (int i=0;i < 5;i++) {
            String[] cut_colon = cut_at[i].split(":");
            int limit = Integer.parseInt(cut_colon[1]);
            if(limit < 0) limit &= 0xFF;
            StringBuilder buff = new StringBuilder();
            buff.append("'");
            int count = 0;
            int limit_col = limit/8+3;
            if (limit%8==0) limit_col--;
            for(int j=2;j<limit_col;j++) {
                byte seat = Byte.parseByte(cut_colon[j]);
                for(int k=7;k>=0;k--) {
                    if(count>=limit) break;
                    byte temp_seat = (byte) (seat>>>k);
                    buff.append((temp_seat & 1)+" ");
                    count ++;
                }
            }
            buff.deleteCharAt(buff.length()-1);
            buff.append("'");
            can_seats[i] = buff.toString();
            String query = String.format("UPDATE pc_seat SET can_seat =" +can_seats[i]+ " WHERE name = " + PCrooms[i]);
            pcroomDB.execSQL( query );
        }
        pcroomDB.close();
    }
    private class asyncPHP extends AsyncTask<Void,Void,Void> {//param, progress, result

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            update(result);
            //txt.setText(result);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            StringBuilder output = new StringBuilder();
            try {
                URL url = new URL("http://14.63.164.76/pcgo.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    int resCode = conn.getResponseCode();

                    if (resCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())) ;
                        String line = null;
                        while(true) {
                            line = reader.readLine();
                            if (line == null) {
                                break;
                            }
                            output.append(line + "\n");
                        }
                        reader.close();
                        conn.disconnect();
                    }
                    result = output.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}