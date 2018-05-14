package com.example.zverys.to_do;

/**
 * Created by Paulius on 2018-05-07.
 */

public class Category {
    private String title, descript; // category name and description

    public Category(String title, String descript){
        this.title=title;
        this.descript=descript;
    }

    public String getTitle(){
        return  title;
    }

    public  String getDescript(){
        return descript;
    }
}
