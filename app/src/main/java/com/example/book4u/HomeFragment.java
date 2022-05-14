package com.example.book4u;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {
    String user;
    String dep_id;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment(String userId, String dep) {
        user = userId;
        dep_id = dep;
        Log.d("TAGf", "HomeFragment: "+user);
        Log.d("TAGf", "HomeFragment: "+dep_id);
    }

    public HomeFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    //for carousel
    RecyclerView recyclerView;
    CarouselAdapter carouselAdapter;
    LinearLayoutManager linearLayoutManager;

    //arrays for carousel
    String[] carouselStatements = {
            "Read pdfs anytime \nanywhere with ease",
            "Variety of pdfs on single \ntouch",
            "Reach out huge \ncommunity and grow together"
    };
    int[] carouselImage = {R.raw.carousel1, R.raw.onboarding2, R.raw.onboarding3};

    //for trendingPDFS
    ImageView imageView;
    TrendingPDFAdapter trendingPDFAdapter;
    LinearLayoutManager HorizontalLayout;
    RecyclerView.LayoutManager layoutManager;
    TrendingPDFAdapter.OnClickListenerInterface onClickListenerInterface;

    EditText searchText;

    //departments

    GridLayout gridLayout;
    String dep;
    public ArrayList<TrendingPDFModel> pdfHolder = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_home, container,false);

        //for carousel
        recyclerView = (RecyclerView) view.findViewById(R.id.carousel);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        carouselAdapter = new CarouselAdapter(getContext(),carouselStatements,carouselImage);
        recyclerView.setAdapter(carouselAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() < (carouselAdapter.getItemCount() - 1)){
                    linearLayoutManager.smoothScrollToPosition(recyclerView,new RecyclerView.State(), linearLayoutManager.findLastVisibleItemPosition() + 1);
                } else if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() < (carouselAdapter.getItemCount() - 1)){
                    linearLayoutManager.smoothScrollToPosition(recyclerView,new RecyclerView.State(), 0);
                }
            }
        },0,2000);

        //for trendingPDFS

        callIntent();
        //Backend Volly implementation
        recyclerView = (RecyclerView) view.findViewById(R.id.trendingPDFs);
        pdfInfoList();



        //search bar
        final EditText searchbar = view.findViewById(R.id.searchBar);
        searchbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchbar.getRight() - searchbar.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        searchText = (EditText) view.findViewById(R.id.searchBar);
                        if(searchText == null){
                            Toast.makeText(getContext(), "Type something in searchfield", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(getContext(), SearchActivity.class);
                            intent.putExtra("searchTerm", searchText.getText().toString());
                            startActivity(intent);
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        //departments section
        gridLayout = (GridLayout) view.findViewById(R.id.deptIcons);
        int childCount = gridLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            int a = i;

            LinearLayout l_conatiner = (LinearLayout) gridLayout.getChildAt(i);
            if (i == 0){
                dep = "Computer";
            }else if(i == 1){
                dep = "ENTC";
            }else if(i == 2){
                dep = "Metallurgy";
            }else if(i == 3){
                dep = "DDGM";
            }else if(i == 4){
                dep = "Civil";
            }else if(i == 5){
                dep = "Mechanical";
            }else if(i == 6){
                dep = "IT";
            }else if(i == 7){
                dep = "Electrical";
            }
            l_conatiner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), SubjectActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    private void callIntent() {
        onClickListenerInterface = new TrendingPDFAdapter.OnClickListenerInterface() {
            @Override
            public void listener(View v, int position) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                startActivity(intent);
            }
        };
    }

    public ArrayList<TrendingPDFModel> onSuccess(ArrayList<TrendingPDFModel> detailsMovies) {
        Log.d("onSucess", detailsMovies.toString());
        pdfHolder = detailsMovies;
        trendingPDFAdapter = new TrendingPDFAdapter(getContext(), detailsMovies);
        Log.d("pdfInfoList", pdfHolder.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(trendingPDFAdapter);
        return null;
    }

    public void pdfInfoList() {
//        Toast.makeText(getContext(), ""+dep_id, Toast.LENGTH_SHORT).show();
        //Fetching Trending pdf from mongo
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest fetch = new JsonArrayRequest(Request.Method.GET, getString(R.string.baseUrl) + "get_recommendations?department_id="+dep_id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // ArrayList<TrendingPDFModel> pdfHold = new ArrayList<>();

                    Log.d("response", response.toString());
                    for (int i=0; i<response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        String id = obj.getString("id");
                        String name = obj.getString("name");
                        String img_url = obj.getString("img_url");
                        TrendingPDFModel ob1 = new TrendingPDFModel();
                        ob1.setId(id);
                        ob1.setImgName(img_url);
                        ob1.setPdfName(name);
                        pdfHolder.add(ob1);

                    }
                    for (int i=0; i<pdfHolder.size(); i++) {
                        Log.d("pdfHolder", pdfHolder.get(i).getImgName().toString());
                    }
                    onSuccess(pdfHolder);
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




}