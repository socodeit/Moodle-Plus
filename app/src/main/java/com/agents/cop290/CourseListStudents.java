package com.agents.cop290;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseListStudents extends AppCompatActivity {

    //initialising array for courses
    String[] array_courses;
    String tab[] ={"course","notification","grades","logout"};
    int icon[]={R.drawable.iitd1,R.drawable.iitd2,R.drawable.iitd4,R.drawable.iitd3};
    Toolbar bar;
    RecyclerView rec;
    RecyclerView.Adapter adp;
    RecyclerView.LayoutManager mang;
    DrawerLayout drawer;
    ActionBarDrawerToggle togg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_students);
            rec =(RecyclerView) findViewById(R.id.RecyclerView);
            rec.setHasFixedSize(true);
            adp= new MyAdapter(tab,icon,"ajay","ajaymahicha@gmail.com",R.drawable.iitd3);
            rec.setAdapter(adp);
            mang =new LinearLayoutManager(this);
            rec.setLayoutManager(mang);
            bar =(Toolbar) findViewById(R.id.toobar);
            setSupportActionBar(bar);
            drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
            togg=new ActionBarDrawerToggle(this,drawer,bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close) {
                @Override
                public void onDrawerOpened(View drawer) {
                    super.onDrawerOpened(drawer);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    // Code here will execute once drawer is closed
                }

            };
            drawer.setDrawerListener(togg); // Drawer Listener set to the Drawer toggle
            togg.syncState();               // Finally we set the drawer toggle sync State
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            //  getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }


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


    }




