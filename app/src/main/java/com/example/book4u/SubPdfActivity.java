package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class SubPdfActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    subjectPdfAdapter subjectPdfAdapter1;
    LinearLayoutManager linearLayoutManager;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_pdf);

        tv = (TextView) findViewById(R.id.SubName);

        String id = getIntent().getStringExtra("subids");
        String name = getIntent().getStringExtra("subName");
        tv.setText(name);
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest fetch = new JsonArrayRequest(Request.Method.GET, getString(R.string.baseUrl) + "get_subject_pdf?id="+id+"&page=0", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String[] pdfname = new String[response.length()];
                String[] subimg = new String[response.length()];
                String[] dis = new String[response.length()];
                String[] pdf_id = new String[response.length()];
                try {
                    // ArrayList<TrendingPDFModel> pdfHold = new ArrayList<>();


                    Log.d("res", response.toString());
                    for (int i=0; i<response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        String name = obj.getString("name");
                        String img_url = obj.getString("img_url");
                        String des = obj.getString("description");
                        String pdf = obj.getString("_id");
                        pdfname[i] = name.toString();
                        subimg[i] = img_url.toString();
                        dis[i] = des.toString();
                        pdf_id[i] = pdf.toString();
                        Log.d("res", "name: "+name);
                        Log.d("res", "image: "+img_url);
                        Log.d("res", "count: "+des);

//                        Log.d("res", "array: "+arrdata.toString());
//                        TrendingPDFModel ob1 = new TrendingPDFModel();
//                        ob1.setImgName(img_url);
//                        ob1.setPdfName(name);
//                        pdfHolder.add(ob1);

                    }
                    recyclerView = (RecyclerView) findViewById(R.id.SubPdfRecycler);
                    subjectPdfAdapter1 = new subjectPdfAdapter(getApplicationContext(), subimg, pdfname,dis,pdf_id);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(subjectPdfAdapter1);


//                    for (int i = 0; i < subname.length; i++) {
//                        Log.d("res", "array name: "+subname[i]);
//                        Log.d("res", "array image: "+subimg[i]);
//                        Log.d("res", "array count: "+subcount[i]);
//                    }

//                    for (int i=0; i<pdfHolder.size(); i++) {
//                        Log.d("pdfHolder", pdfHolder.get(i).getImgName().toString());
//                    }
//                    onSuccess(pdfHolder);
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

        rq.add(fetch);
    }
}