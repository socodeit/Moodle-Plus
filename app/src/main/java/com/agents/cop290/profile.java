package com.agents.cop290;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        TextView t = (TextView) findViewById(R.id.gradebar);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/CabinSketch-Regular.otf");
        t.setTypeface(tf);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        TextView a = (TextView) findViewById(R.id.name);
        tf = Typeface.createFromAsset(getAssets(),
                "fonts/GOTHIC.TTF");
        a.setTypeface(tf);
        a.setText(extras.getString("first_name") + " " + extras.getString("last_name"));
        a = (TextView) findViewById(R.id.eid);
        a.setTypeface(tf);
        a.setText(extras.getString("email"));
        a = (TextView) findViewById(R.id.eno);
        a.setTypeface(tf);
        a.setText(extras.getString("entry_no"));
        a = (TextView) findViewById(R.id.type);
        a.setTypeface(tf);
        ImageView im =(ImageView) findViewById(R.id.image);
        if(extras.getInt("type_")==1) {
            a.setText("Faculty");
            im.setImageResource(R.drawable.prof);
        }
        else
        {
            a.setText("Student");
            im.setImageResource(R.drawable.student);
        }
        a = (TextView) findViewById(R.id.uid);
        a.setTypeface(tf);
        a.setText(extras.getString("id"));




    }
}
