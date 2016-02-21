package com.agents.cop290;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CourseListStudents extends AppCompatActivity {

  //initialising array for courses


    String tab[] = {"Profile", "Courses", "Notifications", "Grades", "Log Out"};
    int icon[] = {R.drawable.p, R.drawable.co, R.drawable.notifications, R.drawable.ic_grade, R.drawable.logout};
    Toolbar bar;
    RecyclerView rec;
    RecyclerView.Adapter adp;
    RecyclerView.LayoutManager mang;
    DrawerLayout drawer;
    ActionBarDrawerToggle togg;
    String name;
    String email;
    int type;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//            return super.onOptionsItemSelected(item);
//        }

    String[] array_courses;
    ListView listView;
    TextView welcome;
    Intent i;
    Bundle extras;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = getIntent();
        extras = i.getExtras();
        setContentView(R.layout.activity_course_list_students);
        setContentView(R.layout.activity_course_list_students);
        name = extras.getString("first_name") + " " + extras.getString("last_name");
        email = extras.getString("email");
        type = extras.getInt("type_");

        //Set Welcome Message Here
        welcome =  (TextView) findViewById(R.id.welcomeMsg);
        if(type == 1) {
            welcome.setText("WELCOME FACULTY " + name);
        }
        else{
            welcome.setText("WELCOME STUDENT " + name);
        }
        rec = (RecyclerView) findViewById(R.id.recview);
        rec.setHasFixedSize(true);
        adp = new MyAdapter(tab, icon, name, email, R.drawable.iitd3);
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
                    // Toast.makeText(courseDetail.this, "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    switch (recyclerView.getChildAdapterPosition(child)) {
                        case 1:
                            Intent nextActivity = new Intent(CourseListStudents.this, profile.class);
                            nextActivity.putExtras(extras);
                            startActivity(nextActivity);
                            break;
                        case 3:
                            nextActivity = new Intent(CourseListStudents.this, notifications.class);
                            startActivity(nextActivity);
                            break;
                        case 2:
                            nextActivity = new Intent(CourseListStudents.this, CourseListStudents.class);
                            nextActivity.putExtras(extras);
                            startActivity(nextActivity);
                            break;
                        case 4:
                            nextActivity = new Intent(CourseListStudents.this, grades.class);
                            startActivity(nextActivity);
                            break;
                        case 5:
                            LoginActivity.cookie = "";
                            nextActivity = new Intent(CourseListStudents.this, LoginActivity.class);
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
        mang = new LinearLayoutManager(this);
        rec.setLayoutManager(mang);
        bar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(bar);
        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        togg = new ActionBarDrawerToggle(this, drawer, bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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

        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        //receiving info about courses
        String url2 = LoginActivity.mainURL + "courses/list.json";


        StringRequest myReq2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //accessing course_info, current sem
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    //Convert String to JSON object
                    JSONObject course_response = new JSONObject(response);

                    Double current_sem = course_response.getDouble("current_sem");
                    JSONArray course_list = course_response.getJSONArray("courses");

                    //adding all course names to listview's string array

                    array_courses = new String[course_list.length()];

                    for (int i = 0; i < course_list.length(); i++) {
                        JSONObject course_object = course_list.getJSONObject(i);
                        String course_name = course_object.optString("code").toString() + " : " + course_object.optString("name").toString();
                        array_courses[i] = course_name;
                    }
                    run();


                    // String abc = array_courses[0];
                    //Toast.makeText(getApplicationContext(), abc, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();

                if (headers == null
                        || headers.equals(Collections.emptyMap())) {
                    headers = new HashMap<String, String>();
                }
                headers.put("Cookie", LoginActivity.cookie);

                return headers;

            }
        };
        requestQueue.add(myReq2);





    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            // getMenuInflater().inflate(R.menu.menu_main, menu);
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

    void run() {
        //creating the listview of list of courses

        listView = (ListView) findViewById(R.id.course_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list, array_courses);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String[] abcd = item.split(":");
                String courseCODE = abcd[0];
                //Toast.makeText(getApplicationContext(), courseCODE,Toast.LENGTH_LONG).show();
                Intent i = new Intent(CourseListStudents.this, courseDetail.class);
                i.putExtra("COURSECODE", courseCODE);
                i.putExtra("name", name);
                i.putExtra("email", email);
                startActivity(i);
            }
        });
    }

    }




