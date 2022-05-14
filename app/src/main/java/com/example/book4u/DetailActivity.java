package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ImageView pdfDetailImageView;
    TextView pdfDetailName,subject,noOfViews,uploaderName,showDesc;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        pdfDetailImageView = (ImageView) findViewById(R.id.pdfDetailsImg);
//        pdfDetailName = (TextView) findViewById(R.id.title);
//        Picasso
//                .get()
//                .load(getIntent().getStringExtra("imgName"))
//                .into(pdfDetailImageView);
//        //pdfDetailImageView.setImageResource(getIntent().getIntExtra("imgName",0));
//        pdfDetailName.setText(getIntent().getStringExtra("pdfName"));
        showPdfDetail();
        backFromDetails();
    }

    public void showPdfDetail() {
//        Toast.makeText(getApplicationContext(), "inside showPdfDetail", Toast.LENGTH_SHORT).show();
        //Fetching Trending pdf from mongo
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String id = getString(R.string.baseUrl)+"pdf_details?id="+getIntent().getStringExtra("pdfId");
//        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("pdfId"), Toast.LENGTH_SHORT).show();
        JsonObjectRequest fetch = new JsonObjectRequest(Request.Method.GET, id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //id, name, user_name, pdf_url, img_url, subject_name, viewers, descriptions
                    String name = response.getString("name");
                    String user_name = response.getString("user_name");
                    String pdf_url = response.getString("pdf_url");
                    String img_url = response.getString("img_url");
                    String subject_name = response.getString("subject_name");
                    String viewers = response.getString("viewers");
                    String desc = response.getString("description");
                    Log.d("detail", name);
                    Log.d("responseDetail", response.toString());
                    subject = (TextView) findViewById(R.id.subject);
                    noOfViews = (TextView) findViewById(R.id.noOfViews);
                    uploaderName = (TextView) findViewById(R.id.uploaderName);
                    showDesc = (TextView) findViewById(R.id.showDesc);
                    pdfDetailImageView = (ImageView) findViewById(R.id.pdfDetailsImg);
                    pdfDetailName = (TextView) findViewById(R.id.title);


                    Picasso
                            .get()
                            .load(img_url)
                            .into(pdfDetailImageView);
                    //pdfDetailImageView.setImageResource(getIntent().getIntExtra("imgName",0));
                    pdfDetailName.setText(name);
                    subject.setText(subject_name);
                    noOfViews.setText(viewers);
                    uploaderName.setText(user_name);
                    showDesc.setText(desc);

                } catch (Exception e) {
                    Log.d("responseE", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("responseError", error.toString());
            }
        });

        requestQueue.add(fetch);

    }

    public void backFromDetails(){

        imageButton = (ImageButton) findViewById(R.id.backBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), BottomTabActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                DetailActivity.super.onBackPressed();
            }
        });
    }
}