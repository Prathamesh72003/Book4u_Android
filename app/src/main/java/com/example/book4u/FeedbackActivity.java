package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        EditText feedbackField = (EditText) findViewById(R.id.feedbackField);

        String user_id = "613f8323c9fad3ccf92f1c0a";

        Button feednackSubmitBtn = (Button) findViewById(R.id.feednackSubmitBtn);
        feednackSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.hsalf.smileyrating.SmileyRating smile_rating = findViewById(R.id.smile_rating);
                String rating = ""+smile_rating.getSelectedSmiley();
//                Toast.makeText(getApplicationContext(),  rating,Toast.LENGTH_SHORT).show();

                if(feedbackField.length() != 0 && !rating.equals("NONE")){
                    submitFeedback(user_id, feedbackField.getText()+"", rating);
                }else{
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void submitFeedback(String user_id, String feedback, String rating){
//        Toast.makeText(getApplicationContext(), user_id+ " "+feedback+" "+rating, Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject params = new JSONObject();
        try{
            params.put("user_id", user_id);
            params.put("feedback", feedback);
            params.put("rating", rating);
        }catch (Exception e){
            Log.d("feedback", e.toString());
        }
        JsonObjectRequest fetch = new JsonObjectRequest(Request.Method.POST, getString(R.string.baseUrl) + "post_feedback", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Feedback successfully submitted", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.d("Feedback", error.toString());
            }
        });
        requestQueue.add(fetch);
    }
}