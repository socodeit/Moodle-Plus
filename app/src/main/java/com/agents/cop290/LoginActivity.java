package com.agents.cop290;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signin = (Button) findViewById(R.id.login);


        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameField = (EditText) findViewById(R.id.username);
                EditText passwordField = (EditText) findViewById(R.id.password);
                Editable editable = usernameField.getText();
                final String username = editable == null ? null : editable.toString();
                editable = passwordField.getText();
                final String password = editable == null ? null : editable.toString();
                //TODO : Adding exception for empty username and password
                String mainURL = "http://10.192.48.179:8000/";
                final String url = mainURL+"default/login.json?userid="+username+"&password="+password;

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String success = response.getString("success");

                            if(success.equals("true"))
                            {
                                Intent nextActivity = new Intent(LoginActivity.this,CourseListStudents.class);

                                Bundle userpass = new Bundle();
                                userpass.putString("username",username);
                                userpass.putString("password",password);

                                nextActivity.putExtras(userpass);

                                startActivity(nextActivity);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Authentication Failure..!!",Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e)
                        {
                            //TODO : handling error by adding new activity
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO : handling error by adding new activity
                        Toast.makeText(getApplicationContext(),url+error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
