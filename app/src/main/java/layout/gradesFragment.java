package layout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.agents.cop290.CourseListStudents;
import com.agents.cop290.LoginActivity;
import com.agents.cop290.R;
import com.agents.cop290.courseDetail;
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
public class gradesFragment extends Fragment {


    public gradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grades, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onActivityCreated(savedInstanceState);
        final String  courseCode = CourseListStudents.clickedcourseCode;
        final ListView gradeList;
        gradeList = (ListView)getActivity().findViewById(R.id.gradeList);
        final Context context=getActivity();


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = LoginActivity.mainURL + "courses/course.json/"+ courseCode+"/grades";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject response=new JSONObject(s);
                    JSONArray gradesArray = response.getJSONArray("grades");

                    String[] gradesStringArray = new String[gradesArray.length()];

                    for(int i=0;i<gradesArray.length();i++)
                    {
                        gradesStringArray[i]=gradesArray.getJSONObject(i).getString("name")+" : "+gradesArray.getJSONObject(i).getInt("weightage")+" : "+gradesArray.getJSONObject(i).getString("out_of")+" : "+gradesArray.getJSONObject(i).getString("score");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.simple_list,gradesStringArray);
                    gradeList.setAdapter(adapter);

                } catch (JSONException e) {
                    //c=TODO: redirect to something went wrong page
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
