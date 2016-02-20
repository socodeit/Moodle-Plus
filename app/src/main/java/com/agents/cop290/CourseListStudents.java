package com.agents.cop290;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CourseListStudents extends AppCompatActivity {


    String[] array_courses;
    String Cookie="";



    private static CourseListStudents _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;



    String tab[] ={"Courses","Notifications","Grades","Threads","Log Out"};
    int icon[]={R.drawable.co,R.drawable.notifications,R.drawable.ic_grade,R.drawable.ic_thread,R.drawable.logout};
    Toolbar bar;
    RecyclerView rec;
    RecyclerView.Adapter adp;
    RecyclerView.LayoutManager mang;
    DrawerLayout drawer;
    ActionBarDrawerToggle togg;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_students);

//        _instance = this;
//        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        _requestQueue = Volley.newRequestQueue(this);
        rec =(RecyclerView) findViewById(R.id.recview);
        bar =(Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(bar);
        rec.setHasFixedSize(true);
        adp= new MyAdapter(tab,icon,"ajay","ajaymahicha@gmail.com",R.drawable.iitd2);
        rec.setAdapter(adp);


        final GestureDetector mGestureDetector = new GestureDetector(CourseListStudents.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        rec.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    drawer.closeDrawers();
                    Toast.makeText(CourseListStudents.this, "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                   switch(recyclerView.getChildAdapterPosition(child))
                   {
                       case 1:  Intent nextActivity = new Intent(CourseListStudents.this,CourseListStudents.class);
                           startActivity(nextActivity);
                           break;
                       case 2:
                            nextActivity = new Intent(CourseListStudents.this,notifications.class);
                           startActivity(nextActivity);
                           break;
                       case 3: nextActivity =new Intent(CourseListStudents.this,grades.class);
                           startActivity(nextActivity);
                           break;
                       case 4 : nextActivity =new Intent(CourseListStudents.this,threads.class);
                           startActivity(nextActivity);
                           break;
                       case 5 : nextActivity =new Intent(CourseListStudents.this,grades.class);
                           startActivity(nextActivity);
                           break;

                   }


                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        mang =new LinearLayoutManager(this);
        rec.setLayoutManager(mang);
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
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _requestQueue = Volley.newRequestQueue(this);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        //Getting bundle from the previous activity
        String username = extras.getString("username");
        String password = extras.getString("password");

        //managing cookies


        //receiving Info from server
        String mainUrl="http://10.192.48.179:8000/";
        String url = mainUrl+"default/login.json?userid=" + username + "&password=" + password;
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //accessing first name, last name and faculty/student
                    JSONObject user = response.getJSONObject("user");
                    String first_name = user.getString("first_name");
                    String last_name = user.getString("last_name");
                    Double type = user.getDouble("type_");
                    String position;
                    if (type == 1) {
                        position = "Faculty";
                    } else {
                        position = "Student";
                    }

                    //adding Position, first name and last name to welcome message
                    final TextView welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
                    welcomeMsg.setText("Welcome" + " " + position + " " + first_name + " " + last_name);
                    //Toast.makeText(getApplicationContext(),Cookie,Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });
//        {
//            @Override
//            protected Response parseNetworkResponse(NetworkResponse response) {
//                Map headers = response.headers;
//                Cookie = (String) headers.get("Set-Cookie");
//                return super.parseNetworkResponse(response);
//            }
//        };
//        _requestQueue.add(myReq);

        //receiving info about courses
        String url2 = mainUrl+"courses/list.json";
        JsonObjectRequest myReq2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject course_response) {
                try {
                    //accessing course_info, current sem
                    Double current_sem = course_response.getDouble("current_sem");
                    JSONArray course_list = course_response.getJSONArray("courses");

                    //adding all course names to listview's string array

                    array_courses = new String[course_list.length()];

                    for (int i = 0; i < course_list.length(); i++) {
                        JSONObject course_object = course_list.getJSONObject(i);
                        String course_name = course_object.optString("code").toString() + " : " + course_object.optString("name").toString();
                        array_courses[i] = course_name;
                    }
                   // Toast.makeText(getApplicationContext(), "Nothing to show",Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });
//        {
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                Map headers = new HashMap();
//                if(!Cookie.equals(""))
//                    headers.put("Cookie", Cookie);
//                return headers;
//            }
//        };
//        _requestQueue.add(myReq2);


        //creating the listview of list of courses

        listView = (ListView) findViewById(R.id.course_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_course_list_students, array_courses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
                String[] abcd = item.split(":");
                String courseCODE = abcd[0];
                Intent i = new Intent(CourseListStudents.this, courseDetail.class);
                i.putExtra("COURSECODE", courseCODE);
                startActivity(i);
            }
        });

    }


//







    }




