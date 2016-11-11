package com.example.capstone2.pcbanggo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<ListViewItem> lv = new ArrayList<>();
    ListView listView;
    Timer refresh;
    String[] PCrooms = {"3POP","Arachne","NET","Rainbow","ANDSOON"};

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

        final MainListAdapter adapter;

        adapter = new MainListAdapter(getApplicationContext(),R.layout.main_row_layout,lv) ;


        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(adapter);

        for (int i = 0; i<PCrooms.length; i++) {
            lv.add(new ListViewItem(PCrooms[i],i,"남은 좌석 : \n" +
                    "최대 연속 좌석 : "));
        }
/*
        lv.add(new ListViewItem("쓰리팝PC",R.drawable.pc_1,"남은 좌석 : \n" +
                "최대 연속 좌석"));
        lv.add(new ListViewItem("아라크네PC",R.drawable.pc_2,"남은 좌석 : \n" +
                "최대 연속 좌석"));
        lv.add(new ListViewItem("맥스피드PC",R.drawable.pc_3,"남은 좌석 : \n" +
                "최대 연속 좌석"));
        lv.add(new ListViewItem("초이스PC",R.drawable.pc_4,"남은 좌석 : \n" +
                "최대 연속 좌석"));
*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),infoPC.class);

                intent.putExtra("title",lv.get(position).titleStr);
                intent.putExtra("img",lv.get(position).iconDrawable);
                startActivity(intent);
            }
        }) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                String[] people = {"전체 목록 보기", "1자리", "2자리", "3자리", "4자리", "5자리 이상"};
                builder.setTitle("필요 연속 좌석")
                        .setItems(people, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // 검색 후 리스트 생성
                                lv.clear();
                                for (int i = 0; i<PCrooms.length; i++) {
                                    if(PCrooms[i].length()>(which*2)) //여기에 검색 기능을 추가해야함-지금은 아무 기능도 없습니다.
                                        lv.add(new ListViewItem(PCrooms[i],i,"남은 좌석 : \n" +
                                                "최대 연속 좌석 : "));
                                }
                                ((BaseAdapter)adapter).notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("취소", null);
                builder.create();
                builder.show();
            }
        });


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
                        ((MainListAdapter)listView.getAdapter()).notifyDataSetChanged();
                    }
                });
            }
        },0,1000);
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
}
