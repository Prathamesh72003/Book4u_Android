package com.example.book4u;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyUploadsPdf#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyUploadsPdf extends Fragment {

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

        recyclerView = (RecyclerView) view.findViewById(R.id.myUploadsPdfRcyl);
        myUploadsPdfAdapter = new MyUploadsPdfAdapter(getContext(), pdfImg, pdfName);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(myUploadsPdfAdapter);
        return view;
    }
}