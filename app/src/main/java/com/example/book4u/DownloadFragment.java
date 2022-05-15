package com.example.book4u;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DownloadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DownloadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DownloadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DownloadFragment newInstance(String param1, String param2) {
        DownloadFragment fragment = new DownloadFragment();
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

    int img[] = {R.drawable.java_book, R.drawable.os_book, R.drawable.os_book, R.drawable.java_book,R.drawable.java_book, R.drawable.os_book, R.drawable.os_book, R.drawable.java_book};
    String heading[] = {"Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II","Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II"};
    String totalDownloads[] = {"50", "70", "0", "101", "50", "70", "0", "101"};
    String dates[] = {"05-03-2022", "23-11-2021","05-03-2022", "23-11-2021","05-03-2022", "23-11-2021","05-03-2022", "23-11-2021"};

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DownloadAdapter downloadAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/.book4u";
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "path: "+ path);

        View view = inflater.inflate(R.layout.fragment_download, container, false);

        if(directory.canRead() && files!=null) {
            Log.d("Files", "Size: "+ files.length);
//            String[] id = new String[files.length];
//            String[] img = new String[files.length];
            String[] name = new String[files.length];
//            String[] subject_name = new String[files.length];
            String[] local_path = new String[files.length];

            for (int i = 0; i < files.length; i++) {
                Log.d("Files", "FileName:" + files[i]);
                name[i] = files[i].getName();
                local_path[i] = files[i].getAbsolutePath();
            }

            recyclerView = (RecyclerView) view.findViewById(R.id.downloadPDFsRecyclerView);
            downloadAdapter = new DownloadAdapter(getContext(), name, local_path);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), linearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(downloadAdapter);
        }

        return view;
    }
}