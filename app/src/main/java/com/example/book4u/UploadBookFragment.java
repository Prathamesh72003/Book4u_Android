package com.example.book4u;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadBookFragment newInstance(String param1, String param2) {
        UploadBookFragment fragment = new UploadBookFragment();
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


    String[] years =  {"1st Year","2nd Year","3rd Year"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    String[] dept =  {"Computer","IT","Mechanical","Electrical","Civil","ENTC", "DDGM", "Metallurgy"};
    AutoCompleteTextView DeptautoCompleteTxt;
    ArrayAdapter<String> DeptadapterItems;

    String[] subs =  {"JP-2","CS","Python","Data mining","Project"};
    AutoCompleteTextView SubtautoCompleteTxt;
    ArrayAdapter<String> SubtadapterItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_pdf, container, false);

        autoCompleteTxt = view.findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<String>(getContext(),R.layout.list_item,years);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        DeptautoCompleteTxt = view.findViewById(R.id.dept_autocomplete);

        DeptadapterItems = new ArrayAdapter<String>(getContext(),R.layout.list_item,dept);
        DeptautoCompleteTxt.setAdapter(DeptadapterItems);

        DeptautoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        SubtautoCompleteTxt = view.findViewById(R.id.sub_autocomplete);

        SubtadapterItems = new ArrayAdapter<String>(getContext(),R.layout.list_item,dept);
        SubtautoCompleteTxt.setAdapter(SubtadapterItems);

        SubtautoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}