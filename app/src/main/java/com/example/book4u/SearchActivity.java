package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    searchAdapter searchAdapter;
    TextView searchText;

    ArrayList<String>  pdfId = new ArrayList<>();

    ArrayList<String>  pdfImg = new ArrayList<>();

    ArrayList<String> pdfName = new ArrayList<>();

    ArrayList<String> subject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        String search = getIntent().getStringExtra("searchTerm");
        searchText = (TextView) findViewById(R.id.searchTerm);
        searchText.setText(search);

        getSearchResult(search);
    }

    public void onSuccess(ArrayList<String> pdfId, ArrayList<String> pdfImg, ArrayList<String> pdfName, ArrayList<String> subject){
        Log.d("search", pdfName.toString());
        recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        searchAdapter = new searchAdapter(getApplicationContext(), pdfId, pdfImg, pdfName, subject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(searchAdapter);
    }

    public void getSearchResult(String search){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest fetch = new JsonArrayRequest(Request.Method.GET, getString(R.string.baseUrl)+"search?search="+search+"&page=0", null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response){
                        Log.d("search", response.toString());
                        try{
                            for(int i=0; i<response.length(); i++){

                                JSONObject obj = response.getJSONObject(i);
                                String id = obj.getString("id");
                                String name = obj.getString("name");
                                String img_url = obj.getString("img_url");
                                String description = obj.getString("description");
                                pdfId.add(id);
                                pdfImg.add(img_url);
                                pdfName.add(name);
                                subject.add(description);
                            }
                            Log.d("search", pdfName.toString());
                            onSuccess(pdfId, pdfImg, pdfName, subject);
                        }catch (Exception e){
                            Log.d("search", e.toString());
                        }


                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError v){
                Log.d("search", v.toString());
            }
        }

        );
        requestQueue.add(fetch);
    }
}