package layout;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.agents.cop290.LoginActivity;
import com.agents.cop290.R;
import com.agents.cop290.assignment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class assignmentFragment extends Fragment {


    public assignmentFragment() {
        // Required empty public constructor
    }
    String coursename="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_assignment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onActivityCreated(savedInstanceState);
//        final String  courseCode = getArguments().getString("courseCode");
        final String  courseCode = "cop290";
        final ListView List = (ListView)getActivity().findViewById(R.id.assign);
        final Context context=getActivity().getBaseContext();


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = LoginActivity.mainURL + "courses/course.json/"+courseCode+"/assignments";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject response=new JSONObject(s);
                    JSONArray assignmentArray = response.getJSONArray("assignments");

                    String[] assignmentStringArray = new String[assignmentArray.length()];

                    for(int i=0;i<assignmentArray.length();i++)
                    {
                        assignmentStringArray[i]=assignmentArray.getJSONObject(i).getInt("id")+".  "+assignmentArray.getJSONObject(i).getString("name")+"\n Deadline:"+assignmentArray.getJSONObject(i).getString("deadline")+"       Late days allowed: "+assignmentArray.getJSONObject(i).getString("late_days_allowed");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.simple_new,assignmentStringArray);
                    List.setAdapter(adapter);
                    List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            int a= item.charAt(0)-48;
                            // Toast.makeText(context, Integer.toString(a), Toast.LENGTH_LONG).show();

                            Intent i = new Intent(context, assignment.class);
                            i.putExtra("id", a);
                            startActivity(i);
                        }
                    });

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
