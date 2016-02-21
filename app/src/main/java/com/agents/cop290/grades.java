package com.agents.cop290;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class grades extends AppCompatActivity {
    String[] all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        //TODO : Adding exception for empty username and password
        TextView t = (TextView) findViewById(R.id.gradebar);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/CabinSketch-Regular.otf");
        t.setTypeface(tf);
        final String url =LoginActivity.mainURL+"/default/grades.json";

        JsonObjectRequest req=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray courses =response.getJSONArray("courses");
                    JSONArray grades =response.getJSONArray("grades");
                    all=new String[courses.length()];
                    for(int i=0;i<courses.length();i++) {

                        JSONObject n = courses.getJSONObject(i);
                        JSONObject m=grades.getJSONObject(i);
                        double d=m.getDouble("score")*m.getDouble("weightage")/m.getDouble("out_of");
                        d = Math.round(d*100)/100.0d;
                        all[i]=i+1+"   "+n.getString("code")+"  "+m.getString("name")+"  "+m.getDouble("score")+"/"+m.getDouble("out_of")+"  "+Double.toString(d);
                    }

                    ListView listView = (ListView) findViewById(R.id.grad);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_new,all);
                    listView.setAdapter(adapter);

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
        requestQueue.add(req);
    }
}
