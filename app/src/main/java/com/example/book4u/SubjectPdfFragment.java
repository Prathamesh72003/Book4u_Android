package com.example.book4u;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectPdfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectPdfFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SubjectPdfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubjectPdfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubjectPdfFragment newInstance(String param1, String param2) {
        SubjectPdfFragment fragment = new SubjectPdfFragment();
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


//    int[] pdfImg = {R.drawable.java_book, R.drawable.c_book, R.drawable.os_book, R.drawable.cpp};
//
//    String[] pdfName = {
//            "Java Programming",
//            "Programming in C",
//            "Operating System",
//            "CPP"
//    };
//
//    String[] subject = {
//            "Java Programming",
//            "Programming in C",
//            "Operating System",
//            "CPP"
//    };
    RecyclerView recyclerView;
    subjectPdfAdapter subjectPdfAdapter1;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject_pdf, container, false);

        String id = this.getArguments().getString("subids");;
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        JsonArrayRequest fetch = new JsonArrayRequest(Request.Method.GET, getString(R.string.baseUrl) + "get_subject_pdf?id=61e5477b4e2fe8b523010395&page=0", null, new Response.Listener<JSONArray>() {
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
                    recyclerView = (RecyclerView) view.findViewById(R.id.SubPdfRecycler);
                    subjectPdfAdapter1 = new subjectPdfAdapter(getActivity(), subimg, pdfname,dis,pdf_id);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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

        return view;
    }
}