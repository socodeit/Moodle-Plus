package com.agents.cop290;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseListStudents extends AppCompatActivity {

    //initialising array for courses
    String[] array_courses;

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
                try {
                    //accessing first name, last name and faculty/student
                    JSONObject user = response.getJSONObject("user");
                    String first_name = user.getString("first_name");
                    String last_name = user.getString("last_name");
                    Double type = user.getDouble("type_");
                    String position;
                    if (type == 1){
                        position = "Faculty";
                    }
                    else{
                        position = "Student";
                    }

                    //adding Position, first name and last name to welcome message
                    final TextView welcomeMsg = (TextView)findViewById(R.id.welcomeMsg);
                    welcomeMsg.setText("Welcome" + " " + position + " " + first_name + " " + last_name);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        //receiving info about courses
        String url2 = "http://192.168.0.112:8000/courses/list.json";
        JsonObjectRequest myReq2 = new JsonObjectRequest(Request.Method.GET, url2,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject course_response) {
                try {
                    //accessing course_info, current sem
                    Double current_sem = course_response.getDouble("current_sem");
                    JSONArray course_list = course_response.getJSONArray("courses");

                    //adding all course names to listview's string array

                    array_courses = new String[course_list.length()];

                    for(int i =0; i<course_list.length(); i++){
                        JSONObject course_object = course_list.getJSONObject(i);
                        String course_name = course_object.optString("code").toString() + " : " +course_object.optString("name").toString();
                        array_courses[i] = course_name;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //creating the listview of list of courses
        ListView listView;
        listView = (ListView) findViewById(R.id.course_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_course_list_students, array_courses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }



}
