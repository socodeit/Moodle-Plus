package com.agents.cop290;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class CourseListStudents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_students);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        //Getting bundle from the previous activity
        String username = extras.getString("username");
        String password = extras.getString("password");

       //receiving Info from server
        String url = "http://192.168.0.112:8000/default/login.json?userid="+username+"&password="+password;
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


    }



}
