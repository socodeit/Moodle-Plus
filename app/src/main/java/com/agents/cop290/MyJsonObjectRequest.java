package com.agents.cop290;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Praveen Singh Rajput on 20-Feb-16.
 */
public class MyJsonObjectRequest extends com.android.volley.toolbox.JsonObjectRequest {

    private final JSONObject _params;

    /**
     * @param method
     * @param url
     * @param params
     *            A {@link HashMap} to post with the request. Null is allowed
     *            and indicates no parameters will be posted along with request.
     * @param listener
     * @param errorListener
     */
    public MyJsonObjectRequest(int method, String url, JSONObject params, Response.Listener<JSONObject> listener,
                         Response.ErrorListener errorListener) {
        super(method, url,params, listener, errorListener);

        _params = params;
    }

//    @Override
//    protected Map<String, String> getParams() {
//        return new ObjectMapper().readValue(<JSON_OBJECT>, HashMap.class);
//    }

    /* (non-Javadoc)
     * @see com.android.volley.toolbox.StringRequest#parseNetworkResponse(com.android.volley.NetworkResponse)
     */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        // since we don't know which of the two underlying network vehicles
        // will Volley use, we have to handle and store session cookies manually
        CourseListStudents.get().checkSessionCookie(response.headers);

        return super.parseNetworkResponse(response);
    }

    /* (non-Javadoc)
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        CourseListStudents.get().addSessionCookie(headers);

        return headers;
    }
}
