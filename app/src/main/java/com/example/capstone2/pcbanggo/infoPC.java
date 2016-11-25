package com.example.capstone2.pcbanggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by skscp on 2016-11-03.
 */

public class infoPC extends AppCompatActivity {

    public static String Pcroom = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_pc);

        TextView pcTitle = (TextView)findViewById(R.id.textView1);
        ImageView iv = (ImageView)findViewById(R.id.imageView1);
//        ImageView iv2 = (ImageView)findViewById(R.id.infopc);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        Pcroom =intent.getStringExtra("title");
        pcTitle.setText(Pcroom);
        iv.setImageResource(intent.getIntExtra("img", 0));

        //  iv2.setImageResource(R.drawable.infopc2); // pc방 자리 정보
    } // end of onCreate


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
        if (id == android.R.id.home) {
            finish();
            return true;
        }
/*
        if (id == R.id.location) {
            return true;
        }
        if (id == R.id.pc) {
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
}