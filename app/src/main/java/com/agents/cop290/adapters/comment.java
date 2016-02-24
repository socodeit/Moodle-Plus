package com.agents.cop290.adapters;

/**
 * Created by Praveen Singh Rajput on 21-Feb-16.
 */
public class comment {
    private String user;
    private String Comment;

    public comment() {
    }

    public comment(String user, String comment) {

        this.user = user;
        Comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
