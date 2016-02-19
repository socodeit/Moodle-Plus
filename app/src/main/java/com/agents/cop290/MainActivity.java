package com.agents.cop290;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView a = (TextView) findViewById(R.id.quote);
        Random rand = new Random();
        int e = rand.nextInt(7);
        String[] q = getResources().getStringArray(R.array.quotes);
        a.setText(q[e]);
       int i=0;
        while(i<5000)
        {
            i++;
        }
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }


}
