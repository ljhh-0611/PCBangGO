package com.example.capstone2.pcbanggo;

/**
 * Created by skscp on 2016-11-03.
 */

public class ListviewAdapter {
    int iconDrawable;
    String titleStr="";
    String descStr="";

    public ListviewAdapter(String title, int icon){
        super();
        this.titleStr = title;
        this.iconDrawable = icon;
    }
