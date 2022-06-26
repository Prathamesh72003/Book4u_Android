package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyUploadedPdfs extends AppCompatActivity {
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String SHARED_ID = "local_userid";

    String userid;
    SharedPreferences sharedpreferences;

    int[] pdfImg = {R.drawable.java_book, R.drawable.c_book, R.drawable.os_book, R.drawable.cpp};

    String[] pdfName = {
            "Java Programming",
            "Programming in C",
            "Operating System",
            "CPP"
    };
    RecyclerView recyclerView;
    MyUploadsPdfAdapter myUploadsPdfAdapter;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploaded_pdfs);

        sharedpreferences =  this.getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(SHARED_ID, null);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String user_id = userid;
        JsonArrayRequest fetch = new JsonArrayRequest(Request.Method.GET, getString(R.string.baseUrl) + "get_users_pdf?id="+user_id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String[] id = new String[response.length()];
                String[] pdfName = new String[response.length()];
                String[] imgUrl = new String[response.length()];
                String[] pdfUrl = new String[response.length()];
                String[] viewers = new String[response.length()];
                String[] status = new String[response.length()];
                String[] status_color = new String[response.length()];


                try{
                    for(int i=0; i<response.length(); i++){
                        JSONObject obj = response.getJSONObject(i);
                        id[i] = obj.getString("id");
                        pdfName[i] = obj.getString("name");
                        imgUrl[i] = obj.getString("img_url");
                        pdfUrl[i] = obj.getString("pdf_url");
                        viewers[i] = obj.getString("viewers");
                        status[i] = obj.getString("status_msg");
                        status_color[i] = obj.getString("status_color");
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.myUploadsPdfRcyl);
                    myUploadsPdfAdapter = new MyUploadsPdfAdapter(getApplicationContext(), id, pdfName, imgUrl, viewers, status);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(myUploadsPdfAdapter);
                }catch (Exception e){
                    Log.d("MyUploadsPdf", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyUploadsPdf", error.toString());
            }
        });
        requestQueue.add(fetch);


    }
}