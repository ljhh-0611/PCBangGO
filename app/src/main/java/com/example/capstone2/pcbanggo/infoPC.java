package com.example.capstone2.pcbanggo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

    asyncPHP task;
    String result;

    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_pc);

        TextView pcTitle = (TextView)findViewById(R.id.textView1);
        //ImageView iv = (ImageView)findViewById(R.id.imageView1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        Pcroom =intent.getStringExtra("title");
        pcTitle.setText(Pcroom);

        ImageView img = (ImageView) findViewById(R.id.imageView1);
        switch (Pcroom) {
            case "3POP":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_1));
                break;
            case "Arachne":
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
                        //TODO - 사진 업데이트 해서 다시 뿌려주기
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

    private class asyncPHP extends AsyncTask<Void,Void,Void> {//param, progress, result

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

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