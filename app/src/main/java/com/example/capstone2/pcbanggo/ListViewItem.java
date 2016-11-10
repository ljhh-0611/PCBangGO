package com.example.capstone2.pcbanggo;

/**
 * Created by skscp on 2016-11-10.
 */

public class ListViewItem {
    int iconDrawable;
    String titleStr="";
    String descStr="";

    public ListViewItem(String title, int icon, String desc){
        super();
        this.titleStr = title;
        this.descStr = desc;
        this.iconDrawable = icon;
    }
    public void setIcon(int icon) {
        iconDrawable = icon;
    }
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setDesc(String desc) {
        descStr = desc;
    }

    public int getIcon() {
        return this.iconDrawable;
    }
    public String getTitle() {
        return this.titleStr;
    }
    public String getDesc() {
        return this.descStr;
    }
}
