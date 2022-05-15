package com.example.book4u;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyUploadsPdf#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyUploadsPdf extends Fragment {
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String SHARED_ID = "local_userid";

    String userid;
    SharedPreferences sharedpreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyUploadsPdf() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyUploadsPdf.
     */
    // TODO: Rename and change types and number of parameters
    public static MyUploadsPdf newInstance(String param1, String param2) {
        MyUploadsPdf fragment = new MyUploadsPdf();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_uploads_pdf, container, false);

        sharedpreferences =  this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(SHARED_ID, null);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                        status[i] = obj.getString("status");
                        status_color[i] = obj.getString("status_color");
                    }
                    recyclerView = (RecyclerView) view.findViewById(R.id.myUploadsPdfRcyl);
                    myUploadsPdfAdapter = new MyUploadsPdfAdapter(getContext(), id, pdfName, imgUrl, viewers, status);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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


        return view;
    }
}