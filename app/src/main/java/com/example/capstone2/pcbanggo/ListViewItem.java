package com.example.capstone2.pcbanggo;

/**
 * Created by skscp on 2016-11-03.
 */

public class ListViewItem {
    int iconDrawable;
    String titleStr = "";
    String descStr = "";

    public ListViewItem(String title, int icon, String desc) {
        super();
        this.titleStr = title;
        this.descStr = desc;
        this.iconDrawable = icon;
    }
}