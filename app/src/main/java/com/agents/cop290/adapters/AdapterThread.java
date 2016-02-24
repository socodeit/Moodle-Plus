package com.agents.cop290.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agents.cop290.R;

import java.util.ArrayList;

/**
 * Created by Praveen Singh Rajput on 21-Feb-16.
 */

public class AdapterThread extends ArrayAdapter<thread> {

    private ArrayList<thread> threads;

    public AdapterThread(Context context, int layoutResourceId, ArrayList<thread> data) {
        super(context, layoutResourceId, data);
        this.threads=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_thread_row, parent, false);
        }

        thread i= threads.get(position);

        if (i != null) {

            TextView thread_id= (TextView)row.findViewById(R.id.listid);
            TextView thread_title= (TextView)row.findViewById(R.id.listThreadTitle);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if(thread_id!=null)
            {
                thread_id.setText(String.valueOf(i.getId()));
            }
            if(thread_title!=null)
            {
                thread_title.setText(i.getTitle());
            }
        }

        return row;
    }

}

