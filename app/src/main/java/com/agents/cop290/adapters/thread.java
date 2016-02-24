package com.agents.cop290.adapters;

/**
 * Created by Praveen Singh Rajput on 21-Feb-16.
 */
public class thread {
    private int id;
    private String title;
    private String desc;

    public thread() {
    }

    public thread(int id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return id+":"+title+":"+desc;
    }
}
