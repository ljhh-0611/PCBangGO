package com.example.capstone2.pcbanggo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    PCroomDBHelper pcroomDBHelper;
    SQLiteDatabase pcroomDB;
    Cursor seatCursor;
    final static String seatSelect = "SELECT * FROM pc_seat";
    SeatCursorAdapter seatAdapter;

    ListView listView;
    Timer refresh;

    asyncPHP task;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startActivity(new Intent(this, SplashActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.main_list_view);

        pcroomDBHelper = new PCroomDBHelper(this);
        pcroomDB = pcroomDBHelper.getWritableDatabase();
        seatCursor = pcroomDB.rawQuery(seatSelect,null);
        seatAdapter = new SeatCursorAdapter(this,seatCursor,0,false);


        listView.setAdapter(seatAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),infoPC.class);
                SQLiteCursor sqlCursor = (SQLiteCursor) parent.getItemAtPosition(position);
                intent.putExtra("title",sqlCursor.getString(sqlCursor.getColumnIndex("name")));
                startActivity(intent);
            }
        }) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View checkBoxView = View.inflate(view.getContext(), R.layout.sp_checkbox, null);
                final CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                String[] people = {"전체 목록 보기","1명","2명","3명","4명","5명","6명 이상"};
                builder.setTitle("필요 연속 좌석")
                        .setItems(people, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                listView.setAdapter(new SeatCursorAdapter(view.getContext(),seatCursor,which,checkBox.isChecked()));
                            }
                        });
                builder.setNegativeButton("취소", null);
                builder.setView(checkBoxView);
                builder.create();
                builder.show();

            }
        });
        pcroomDB.close();
    }

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
                        //((SeatCursorAdapter)listView.getAdapter()).notifyDataSetChanged();
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(this,"camera",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void update(String receive){

        String[] PCrooms = {"'3POP'","'Gallery'", "'Max'","'Choice'","'Red'"};
        String[] can_seats = {"'3POP'","'Gallery'", "'Max'","'Choice'","'Red'"};
        String[] cut_at = receive.split("@");
        pcroomDB = pcroomDBHelper.getWritableDatabase();
        for (int i=1;i < 5;i++) { // 쓰리팝 데이터 확인
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
                    //seat = (byte) (seat>>>1);
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
