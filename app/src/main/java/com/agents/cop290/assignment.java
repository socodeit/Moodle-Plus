package com.agents.cop290;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class assignment extends AppCompatActivity {
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        Intent i = getIntent();
        extras = i.getExtras();
       // Toast.makeText(this,i.getIntExtra("id",0)+"asaddsf",Toast.LENGTH_SHORT).show();
        final String url =LoginActivity.mainURL+"courses/assignment.json/"+i.getIntExtra("id",0);
        JsonObjectRequest requ=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject as =response.getJSONObject("assignment");
                    JSONObject reg =response.getJSONObject("registered");
                    JSONObject course =response.getJSONObject("course");
                  //Toast.makeText(getApplicationContext(),"kiol"+reg,Toast.LENGTH_LONG).show();
                      int cid=as.getInt("id");
                    TextView a = (TextView) findViewById(R.id.cid);
                    Typeface tf = Typeface.createFromAsset(getAssets(),
                            "fonts/GOTHIC.TTF");
                    a.setTypeface(tf);
                    a.setText(Integer.toString(cid));
                    a = (TextView) findViewById(R.id.asname);
                    a.setTypeface(tf);
                    a.setText(as.getString("name"));
                  //  Toast.makeText(getApplicationContext(),as.getString("name")+"asaddsf",Toast.LENGTH_SHORT).show();

                    a = (TextView) findViewById(R.id.pid);
                    a.setTypeface(tf);
                    a.setText(Integer.toString(reg.getInt("professor")));
                    a = (TextView) findViewById(R.id.sem);
                    int sem=reg.getInt("semester");
                    a.setTypeface(tf);
                    a.setText(Integer.toString(sem));
                    a = (TextView) findViewById(R.id.year);
                    String year=Integer.toString(reg.getInt("year_"));
                    a.setTypeface(tf);
                    a.setText(year);
                    a = (TextView) findViewById(R.id.course);
                    a.setTypeface(tf);
                    a.setText(course.getString("code"));
                    a = (TextView) findViewById(R.id.created);
                    a.setTypeface(tf);
                    a.setText(as.getString("created_at"));
                    a = (TextView) findViewById(R.id.dead);
                    a.setTypeface(tf);
                    a.setText(as.getString("deadline"));
                    a = (TextView) findViewById(R.id.late);
                    a.setTypeface(tf);
                    a.setText(Integer.toString(as.getInt("late_days_allowed")));
                    a = (TextView) findViewById(R.id.description);
                    a.setTypeface(tf);
                    a.setText((as.getString("description").replaceAll("<.*?>","")).replaceAll("&.*?;",""));

                }catch (JSONException e)
                {
                    //TODO : handling error by adding new activity
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO : handling error by adding new activity
                Toast.makeText(getApplicationContext(),url+error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
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
        requestQueue.add(requ);

    }
}
