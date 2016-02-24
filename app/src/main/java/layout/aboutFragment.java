package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class aboutFragment extends Fragment {


    public aboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final String  courseCode = "cop290";
        //TODO: showing "getting data...."
        final View view= inflater.inflate(R.layout.fragment_about, container, false);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        final TextView courseTitle = (TextView) view.findViewById(R.id.courseTitle);
        final TextView courseDesc = (TextView) view.findViewById(R.id.courseDesc);
        final TextView courseCredit = (TextView) view.findViewById(R.id.courseCredit);
        final TextView courseLTP = (TextView) view.findViewById(R.id.courseLTP);

        String url = LoginActivity.mainURL + "courses/list.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject response=new JSONObject(s);

                    JSONArray coursesList = response.getJSONArray("courses");

                    for(int i=0;i<coursesList.length();i++)
                    {
                        if(coursesList.getJSONObject(i).getString("code").equals(courseCode)){
                            courseTitle.setText(coursesList.getJSONObject(i).getString("name"));
                            courseDesc.setText(coursesList.getJSONObject(i).getString("description"));
                            courseCredit.setText("CREDITS:"+coursesList.getJSONObject(i).getInt("credits"));
                            courseLTP.setText("L-T-P:"+coursesList.getJSONObject(i).getString("l_t_p"));
                        }
                    }

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


        return view;
    }

}
