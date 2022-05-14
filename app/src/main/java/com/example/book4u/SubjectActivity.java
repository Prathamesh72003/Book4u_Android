package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class SubjectActivity extends AppCompatActivity {
    int img[] = {R.drawable.java_book, R.drawable.os_book, R.drawable.os_book, R.drawable.java_book,R.drawable.java_book, R.drawable.os_book, R.drawable.os_book, R.drawable.java_book};
    String heading[] = {"Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II","Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II"};
    String num[] = {"50", "70", "0", "101", "50", "70", "0", "101"};

    SubjectAdapter subjectAdapter;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    TextView dep_name;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        dep_name = (TextView) findViewById(R.id.depName);
        int cat_id = getIntent().getIntExtra("cat_id",0);

        if (cat_id == 1){
            dep_name.setText("Computer");
        }else if(cat_id == 2){
            dep_name.setText("ENTC");
        }else if(cat_id == 3){
            dep_name.setText("Metullargy");
        }else if(cat_id == 4){
            dep_name.setText("DDGM");
        }else if(cat_id == 5){
            dep_name.setText("Civil");
        }else if(cat_id == 6){
            dep_name.setText("Mechanical");
        }else if(cat_id == 7){
            dep_name.setText("IT");
        }else if(cat_id == 8){
            dep_name.setText("Electrical");
        }
        recyclerView = (RecyclerView) findViewById(R.id.subjectRecyclerView);
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonArrayRequest fetch = new JsonArrayRequest(Request.Method.GET, getString(R.string.baseUrl) + "get_department_subject?id="+cat_id+"&page=0", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String[] subname = new String[response.length()];
                String[] subimg = new String[response.length()];
                String[] subcount = new String[response.length()];
                String[] subid = new String[response.length()];
                try {
                    // ArrayList<TrendingPDFModel> pdfHold = new ArrayList<>();


                    Log.d("res", response.toString());
                    for (int i=0; i<response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        String name = obj.getString("name");
                        String img_url = obj.getString("img_url");
                        String count = obj.getString("count");
                        String ids = obj.getString("id");
                        subname[i] = name.toString();
                        subimg[i] = img_url.toString();
                        subcount[i] = count.toString();
                        subid[i] = ids.toString();
                        Log.d("res", "name: "+name);
                        Log.d("res", "image: "+img_url);
                        Log.d("res", "count: "+count);

//                        Log.d("res", "array: "+arrdata.toString());
//                        TrendingPDFModel ob1 = new TrendingPDFModel();
//                        ob1.setImgName(img_url);
//                        ob1.setPdfName(name);
//                        pdfHolder.add(ob1);

                    }
                    subjectAdapter = new SubjectAdapter(getApplicationContext(), subimg, subname, subcount,subid);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    recyclerView.setAdapter(subjectAdapter);


                    for (int i = 0; i < subname.length; i++) {
                        Log.d("res", "array name: "+subname[i]);
                        Log.d("res", "array image: "+subimg[i]);
                        Log.d("res", "array count: "+subcount[i]);
                    }

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