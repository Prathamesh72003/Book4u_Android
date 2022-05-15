package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ImageView pdfDetailImageView;
    TextView pdfDetailName,subject,noOfViews,uploaderName,showDesc;
    ImageButton imageButton;
    MaterialButton downloadBtn;
    Button readNowBtn;
    String pdf_url;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String SHARED_ID = "local_userid";
    SharedPreferences sharedpreferences;
    String userid;
    String pdf_id;
    String pdf_name;

    int bookmark = R.drawable.solid_bookmark;
    int bookmark_border = R.drawable.border_bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        downloadBtn = (MaterialButton) findViewById(R.id.downloadBtn);
        readNowBtn = (Button) findViewById(R.id.readNowBtn);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(SHARED_ID, null);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdf_url + ""));
                request.setTitle("Downloading PDF");
                request.setMimeType("application/pdf");
                request.allowScanningByMediaScanner();
                request.setAllowedOverMetered(true);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/.book4u/"+pdf_name+".pdf");

                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

                Toast.makeText(getApplicationContext(), "PDF downloaded successfully", Toast.LENGTH_SHORT).show();

            }
        });

        readNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(pdf_url),"application/pdf");

                Log.d("path", Environment.getExternalStorageDirectory().toString());
                Intent chooser = Intent.createChooser(browserIntent, "Open PDF");
                chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
                startActivity(chooser);
            }
        });

        showPdfDetail();
        backFromDetails();
    }

    public void showPdfDetail() {
//        Toast.makeText(getApplicationContext(), "inside showPdfDetail", Toast.LENGTH_SHORT).show();
        //Fetching Trending pdf from mongo
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String user_id = userid;
        String[] id = {""};

        String url = getString(R.string.baseUrl)+"pdf_details?id="+getIntent().getStringExtra("pdfId");
//        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("pdfId"), Toast.LENGTH_SHORT).show();
        JsonObjectRequest fetch = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //id, name, user_name, pdf_url, img_url, subject_name, viewers, descriptions

                    pdf_id = response.getString("id");
                    id[0] = pdf_id;
                    String name = response.getString("name");
                    pdf_name = name;

                    String user_name = response.getString("user_name");
                    pdf_url = response.getString("pdf_url");
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

                    getBookmarkStatus(user_id, id[0]);

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

        ImageButton bookmarkBtn = findViewById(R.id.bookmarkBtn);
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_status = "";
                String current_status = bookmarkBtn.getTag().toString();
                if(current_status.equals("true")){
                    new_status = "false";
                }else{
                    new_status = "true";
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest fetch = new StringRequest(Request.Method.GET,
                        getString(R.string.baseUrl) + "set_bookmark?user_id=" + user_id + "&pdf_id=" + id[0] +"&state="+new_status,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ImageButton bookmarkBtn = findViewById(R.id.bookmarkBtn);
                                if (bookmarkBtn.getTag().toString().equals("true")) {
                                    bookmarkBtn.setImageResource(bookmark_border);
                                    bookmarkBtn.setTag("false");
                                }else{
                                    bookmarkBtn.setImageResource(bookmark);
                                    bookmarkBtn.setTag("true");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("detail", error.toString());
                    }
                });
                requestQueue.add(fetch);
            }
        });

    }

    public void getBookmarkStatus(String user_id, String pdf_id){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest fetch = new StringRequest(Request.Method.GET,
                getString(R.string.baseUrl) + "is_bookmark?user_id=" + user_id + "&pdf_id=" + pdf_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ImageButton bookmarkBtn = findViewById(R.id.bookmarkBtn);
                        if (response.equals("false")) {
                            bookmarkBtn.setImageResource(bookmark_border);
                            bookmarkBtn.setTag("false");
                        }else{
                            bookmarkBtn.setImageResource(bookmark);
                            bookmarkBtn.setTag("true");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("detail", error.toString());
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
