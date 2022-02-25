package com.example.book4u;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
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
            "Variety of pdfs on \nsingle touch",
            "Reach out a huge \ncommunity and grow \ntogether"
    };
    int[] carouselImage = {R.drawable.cpp, R.drawable.python, R.drawable.typescript};

    //for trendingPDFS
    ImageView imageView;
    TrendingPDFAdapter trendingPDFAdapter;
    LinearLayoutManager HorizontalLayout;
    RecyclerView.LayoutManager layoutManager;

    //arrays for trendingPDFS
    int[] pdfImg = {R.drawable.java_book, R.drawable.c_book, R.drawable.os_book};

    String[] pdfName = {
            "Java Programming",
            "Programming in C",
            "Operating System"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container,false);

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
        recyclerView = (RecyclerView) view.findViewById(R.id.trendingPDFs);
        trendingPDFAdapter = new TrendingPDFAdapter(getContext(), pdfName, pdfImg);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(trendingPDFAdapter);

        return view;
    }

}