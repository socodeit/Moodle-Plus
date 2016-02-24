package layout;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.agents.cop290.CourseListStudents;
import com.agents.cop290.LoginActivity;
import com.agents.cop290.R;
import com.agents.cop290.adapters.AdapterThread;
import com.agents.cop290.adapters.thread;
import com.agents.cop290.showThread;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class threadsFragment extends Fragment {


    public threadsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_threads, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onActivityCreated(savedInstanceState);
        final String  courseCode = CourseListStudents.clickedcourseCode;
        final ListView listView = (ListView)getActivity().findViewById(R.id.threadTitleListView);

        boolean ans = listView==null;

        final RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        final Context context=getActivity();

        String url = LoginActivity.mainURL+"courses/course.json/"+ courseCode+"/threads";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject response=new JSONObject(s);
                    JSONArray threadsJSONArray = response.getJSONArray("course_threads");
                    ArrayList<thread> threadArrayList = new ArrayList<>();
                    Log.d("threadsJSONArray",Integer.toString(threadsJSONArray.getJSONObject(0).getInt("id")));
                    for(int i=0;i<threadsJSONArray.length();i++)
                    {
                        threadArrayList.add(new thread(threadsJSONArray.getJSONObject(i).getInt("id"),threadsJSONArray.getJSONObject(i).getString("title"),threadsJSONArray.getJSONObject(i).getString("description")));
                    }

                    AdapterThread adapter=new AdapterThread(context,R.layout.listview_thread_row,threadArrayList);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String clickedThreadText=parent.getItemAtPosition(position).toString();

                            String[] splitedText = clickedThreadText.split(":");

                            int threadId = Integer.parseInt(splitedText[0]);

                            Intent nextActivity = new Intent(getActivity(),showThread.class);
                            nextActivity.putExtra("threadId",threadId);
                            startActivity(nextActivity);

                        }
                    });

                } catch (JSONException e) {
                    //TODO: redirect to something went wrong page
                    Log.e("JSON Error",e.toString());
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

        //Posting thread

        Button postButton = (Button)getActivity().findViewById(R.id.post_button);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url2=threadURL(courseCode);
                //TODO Exception for null url;

                StringRequest stringRequest1=new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Toast.makeText(getContext(),"Thread Post Successful",Toast.LENGTH_LONG).show();

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

                requestQueue.add(stringRequest1);
            }
        });

    }

    public String threadURL(String courseCode)
    {
        EditText title = (EditText)getActivity().findViewById(R.id.threadTitle);
        EditText desc = (EditText)getActivity().findViewById(R.id.threadDesc);

        Editable editable = title.getText();
        final String Title = editable == null ? null : editable.toString();
        editable = desc.getText();
        final String Desc = editable == null ? null : editable.toString();

        //TODO : null string for empty title and description

        String url=LoginActivity.mainURL + "/threads/new.json?title="+Title+"&description="+Desc+"&course_code="+courseCode;
        return url;
    }

}
