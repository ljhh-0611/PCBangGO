package com.example.capstone2.pcbanggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.capstone2.pcbanggo.infoPC.Pcroom;

/**
 * Created by skscp on 2016-12-05.
 */

public class infoPCroom extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_pcroom);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView pcTitle = (TextView)findViewById(R.id.pcroom);

        pcTitle.setText(Pcroom);
        TextView location = (TextView)findViewById(R.id.location2);
        TextView pc = (TextView)findViewById(R.id.pc2);
        TextView phone = (TextView)findViewById(R.id.phone2);
        ImageView img = (ImageView) findViewById(R.id.image_pcroom);
        switch (Pcroom) {
            case "3POP":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_1));
                location.setText("경기도 수원시 장안구 화산로233번길 63 쓰리팝PC");
                pc.setText("CPU : ㅁㅁㅁ\nRAM : ㅁㅁㅁ\nGraphic : ㅁㅁㅁ");
                phone.setText("031-123-4567");
                break;
            case "Gallery":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_2));
                location.setText("경기도 수원시 장안구 율전동 408-16 갤러리PC");
                pc.setText("CPU : ㅁㅁㅁ\nRAM : ㅁㅁㅁ\nGraphic : ㅁㅁㅁ");
                phone.setText("031-123-4567");
                break;
            case "Max":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_3));
                location.setText("경기도 수원시 장안구 율전동 288-40 맥스피드 PC");
                pc.setText("CPU : i5-4690\nRAM : 8GB\nGraphic : GTX-760");
                phone.setText("031-123-4567");
                break;
            case "Choice":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_4));
                location.setText("경기도 수원시 장안구 서부로 2136 초이스PC");
                pc.setText("CPU : ㅁㅁㅁ\nRAM : ㅁㅁㅁ\nGraphic : ㅁㅁㅁ");
                phone.setText("031-123-4567");
                break;
            case "Red":
                img.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pc_5));
                location.setText("경기도 수원시 장안구 천천동 527-4 광장프라자 901 레드 PC");
                pc.setText("CPU : i5-6500\nRAM : 8GB\nGraphic : GTX-1060\n기타 : 32/40 인치 모니터, 기계식 키보드");
                phone.setText("010-7117-1082");
                break;
        }

    } // end of onCreate

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

 /*       if (id == R.id.pcroominfo) {
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
