package com.agents.cop290;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class notifications extends AppCompatActivity {
    String [] notification;
    String [] time;
    int[] sno;
    String [] to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        //TODO : Adding exception for empty username and password
        TextView t = (TextView) findViewById(R.id.notibar);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/CabinSketch-Regular.otf");
        t.setTypeface(tf);
        final String url =LoginActivity.mainURL+"/default/notifications.json";

        JsonObjectRequest req=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray notif =response.getJSONArray("notifications");
                    notification = new String[notif.length()];
                    time =new String[notif.length()];
                    to  =new String[notif.length()];
                    if(notif.length()==0)
                    {
                        Toast.makeText(getApplicationContext(),"No Notifications to Show.... Chill !!!!",Toast.LENGTH_LONG).show();
                    }
                    for(int i=0;i<notif.length();i++) {
                        JSONObject n = notif.getJSONObject(i);
                        notification[i]=n.getString("description");
                        time[i]=n.getString("created_at");
                        notification[i]=notification[i].replaceAll("<.*?>","");
                        to[i]=notification[i]+"\n                                                    "+time[i];
                    }
                    ListView listView = (ListView) findViewById(R.id.lv);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_new,to );
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
