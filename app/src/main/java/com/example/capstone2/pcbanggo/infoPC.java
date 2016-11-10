package com.example.capstone2.pcbanggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by skscp on 2016-11-03.
 */

public class infoPC extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_pc);

        TextView pcTitle = (TextView)findViewById(R.id.textView1);
        ImageView iv = (ImageView)findViewById(R.id.imageView1);
        ImageView iv2 = (ImageView)findViewById(R.id.infopc);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        pcTitle.setText(intent.getStringExtra("title"));
        iv.setImageResource(intent.getIntExtra("img", 0));
      //  iv2.setImageResource(R.drawable.infopc2); // pc방 자리 정보
    } // end of onCreate
}