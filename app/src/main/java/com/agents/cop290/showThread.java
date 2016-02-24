package com.agents.cop290;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.agents.cop290.adapters.AdapterComment;
import com.agents.cop290.adapters.comment;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class showThread extends AppCompatActivity {

    int threadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_thread);

        Intent preActivity = getIntent();

        threadId=preActivity.getIntExtra("threadId",0);

        final ListView listView = (ListView)findViewById(R.id.listViewShowThread);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = LoginActivity.mainURL + "threads/thread.json/"+ threadId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject response=new JSONObject(s);
                    JSONArray commentJSONArray = response.getJSONArray("comments");
                    ArrayList<comment> commentArrayList = new ArrayList<>();
                    for (int i = 0; i < commentJSONArray.length(); i++) {
                        commentArrayList.add(new comment(commentJSONArray.getJSONObject(i).getString("created_at"),commentJSONArray.getJSONObject(i).getString("description")));
                    }

                    AdapterComment adapter =new AdapterComment(getBaseContext(),R.layout.listview_showthread_row,commentArrayList);
                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    //TODO: redirect to something went wrong page
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
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

        requestQueue.add(stringRequest);

    }
}
