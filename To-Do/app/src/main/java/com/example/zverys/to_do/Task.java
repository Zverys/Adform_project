package com.example.zverys.to_do;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Linas on 12/03/2018.
 */

public class Task {
    public Task(String name, String desciption, String category, Date date) {
        this.name = name;
        this.desciption = desciption;
        this.category = category;
        this.date = date;
        //list.add(new Task(name, desciption, category,date));
    }

    String name;
    String desciption;
    String category;
    Date date;
    /*private List<Task> list = new ArrayList<>();

    public List<Task> getList() {
        return list;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
