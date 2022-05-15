package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String SHARED_ID = "local_userid";

    String userid;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        com.google.android.material.textfield.TextInputEditText fname = findViewById(R.id.fname);
        com.google.android.material.textfield.TextInputEditText lname = findViewById(R.id.lname);
        com.google.android.material.textfield.TextInputEditText email = findViewById(R.id.email);
        RadioGroup gender = (RadioGroup) findViewById(R.id.gender);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(SHARED_ID, null);

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(fname.getText().toString(), lname.getText().toString(), email.getText().toString(), gender.getCheckedRadioButtonId());
            }
        });

    }

    public void updateProfile(String fname, String lname, String email, int id){
        RadioButton selected = findViewById(id);

        JSONObject params = new JSONObject();
        try{
            params.put("id", userid);
            params.put("first_name", fname);
            params.put("last_name", lname);
            params.put("email", email);
            params.put("gender", selected.getText().toString());
        }catch (Exception e){
            Log.d("feedback", e.toString());
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest fetch = new JsonObjectRequest(Request.Method.POST, getString(R.string.baseUrl) + "edit_profile", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Chnages updated", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(fetch);
    }
}