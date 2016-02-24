package com.agents.cop290;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {
    //cookie stores cookies
    public static String cookie;
    public static String mainURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Sign-in Button
        Button signin = (Button) findViewById(R.id.login);

        //RequestQueue of Volley
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //extracting data from entry fields
                EditText usernameField = (EditText) findViewById(R.id.username);
                EditText passwordField = (EditText) findViewById(R.id.password);
                Editable editable = usernameField.getText();
                final String username = editable == null ? null : editable.toString();
                editable = passwordField.getText();
                final String password = editable == null ? null : editable.toString();

//                Progress Dialog Box

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();


                //Sending GET request to server for login
                //TODO : Adding exception for empty username and password
                mainURL = "http://192.168.105.120:8000/";
                final String url = mainURL+"default/login.json?userid="+username+"&password="+password;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject response_json = new JSONObject(response);
                                    String success = response_json.getString("success");
                                    JSONObject user_json = response_json.getJSONObject("user");
                                    if(success.equals("true")) {
                                        String first_name = user_json.getString("first_name");
                                        String last_name = user_json.getString("last_name");
                                        Double type = user_json.getDouble("type_");
                                        String position;
                                        if (type == 1) {
                                            position = "Faculty";
                                        } else {
                                            position = "Student";
                                        }
                                        //Toast.makeText(getApplicationContext(), first_name + " " + position, Toast.LENGTH_LONG).show();

                                        //Creating new intent for next Activity
                                        Intent nextActivity = new Intent(LoginActivity.this,CourseListStudents.class);

                                        Bundle userDetails = new Bundle();
                                        userDetails.putString("first_name",user_json.getString("first_name"));
                                        userDetails.putString("last_name",user_json.getString("last_name"));
                                        userDetails.putString("id",user_json.getString("id"));
                                        userDetails.putString("entry_no",user_json.getString("entry_no"));
                                        userDetails.putInt("type_", user_json.getInt("type_"));
                                        userDetails.putString("email",user_json.getString("email"));
                                        nextActivity.putExtras(userDetails);
                                        startActivity(nextActivity);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                       progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG);
                    }
                })
                {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        // since we don't know which of the two underlying network vehicles
                        // will Volley use, we have to handle and store session cookies manually
                        cookie=response.headers.get("Set-Cookie");
                        //Log.i("header",cookie);
                        String parsed;
                        try {
                            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        } catch (UnsupportedEncodingException e) {
                            parsed = new String(response.data);
                        }
                        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));


                    }
                };
                requestQueue.add(stringRequest);

            }
        });
    }
}
