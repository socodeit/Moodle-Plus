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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class CourseListStudents extends AppCompatActivity {
//
//    //initialising array for courses
//
//
//    String tab[] ={"course","notification","grades","logout"};
//    int icon[]={R.drawable.iitd1,R.drawable.iitd2,R.drawable.iitd4,R.drawable.iitd3};
//    Toolbar bar;
//    RecyclerView rec;
//    RecyclerView.Adapter adp;
//    RecyclerView.LayoutManager mang;
//    DrawerLayout drawer;
//    ActionBarDrawerToggle togg;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_course_list_students);
//            rec =(RecyclerView) findViewById(R.id.RecyclerView);
//            rec.setHasFixedSize(true);
//            adp= new MyAdapter(tab,icon,"ajay","ajaymahicha@gmail.com",R.drawable.iitd3);
//            rec.setAdapter(adp);
//            mang =new LinearLayoutManager(this);
//            rec.setLayoutManager(mang);
//            bar =(Toolbar) findViewById(R.id.toobar);
//            setSupportActionBar(bar);
//            drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
//            togg=new ActionBarDrawerToggle(this,drawer,bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close) {
//                @Override
//                public void onDrawerOpened(View drawer) {
//                    super.onDrawerOpened(drawer);
//                }
//
//                @Override
//                public void onDrawerClosed(View drawerView) {
//                    super.onDrawerClosed(drawerView);
//                    // Code here will execute once drawer is closed
//                }
//
//            };
//            drawer.setDrawerListener(togg); // Drawer Listener set to the Drawer toggle
//            togg.syncState();               // Finally we set the drawer toggle sync State
//        }
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds items to the action bar if it is present.
//            //  getMenuInflater().inflate(R.menu.menu_main, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            // Handle action bar item clicks here. The action bar will
//            // automatically handle clicks on the Home/Up button, so long
//            // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId();
//
//            //noinspection SimplifiableIfStatement
//            if (id == R.id.action_settings) {
//                return true;
//            }
//
//            return super.onOptionsItemSelected(item);
//        }

    String[] array_courses;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_students);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        //receiving info about courses
        String url2 = LoginActivity.mainURL + "courses/list.json";

        Toast.makeText(getApplicationContext(),url2,Toast.LENGTH_LONG).show();



        StringRequest myReq2 = new StringRequest(Request.Method.GET,url2,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //accessing course_info, current sem
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
                    Toast.makeText(getApplicationContext(), "Nothing to show",Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();

                if (headers == null
                        || headers.equals(Collections.emptyMap())) {
                    headers = new HashMap<String, String>();
                }
                headers.put("Cookie",LoginActivity.cookie);

                return headers;

            }
        };
        requestQueue.add(myReq2);


        //creating the listview of list of courses

//        listView = (ListView) findViewById(R.id.course_list);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_course_list_students, array_courses);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
//                String[] abcd = item.split(":");
//                String courseCODE = abcd[0];
//                Intent i = new Intent(CourseListStudents.this, courseDetail.class);
//                i.putExtra("COURSECODE", courseCODE);
//                startActivity(i);
//            }
//        });

    }
}



