package com.agents.cop290.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.agents.cop290.R;

import java.util.ArrayList;

/**
 * Created by Praveen Singh Rajput on 21-Feb-16.
 */
public class AdapterComment extends ArrayAdapter<com.agents.cop290.adapters.comment>{

    //Array list for class comment
    private ArrayList<com.agents.cop290.adapters.comment> comments;

    // COnstructor for Adaptor
    public AdapterComment(Context context, int layoutResourceId, ArrayList<comment> data) {
        super(context, layoutResourceId, data);
        this.comments=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_showthread_row, parent, false);
        }

        comment i= comments.get(position);

        if (i != null) {

            TextView comment_user= (TextView)row.findViewById(R.id.row_showThread_user);
            TextView comment_Comment= (TextView)row.findViewById(R.id.row_showThread_comment);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if(comment_user!=null)
            {
                comment_user.setText(String.valueOf(i.getUser()));
            }
            if(comment_Comment!=null)
            {
                comment_Comment.setText(i.getComment());
            }
        }

        return row;
    }

}


