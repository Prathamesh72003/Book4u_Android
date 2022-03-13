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
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookmarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarkFragment newInstance(String param1, String param2) {
        BookmarkFragment fragment = new BookmarkFragment();
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
    String totalReaders[] = {"50", "70", "0", "101", "50", "70", "0", "101"};

    String description[] = {"Java is a programming language and computing platform first released by Sun Microsystems in 1995. It has evolved from humble beginnings to power a large share of today’s digital world, by providing the reliable platform upon which many services and applications are built.",
            "An operating system is a software programme required to manage and operate a computing device like smartphones, tablets, computers, supercomputers, web servers, cars, network towers, smartwatches, etc. ",
            "A computer network is a set of computers sharing resources located on or provided by network nodes. The computers use common communication protocols over digital interconnections to communicate with each other.",
            "Advanced Java is everything that goes beyond Core Java – most importantly the APIs defined in Java Enterprise Edition, includes Servlet programming, Web Services, the Persistence API, etc. ",
            "Java is a programming language and computing platform first released by Sun Microsystems in 1995. It has evolved from humble beginnings to power a large share of today’s digital world, by providing the reliable platform upon which many services and applications are built.",
            "An operating system is a software programme required to manage and operate a computing device like smartphones, tablets, computers, supercomputers, web servers, cars, network towers, smartwatches, etc. ",
            "A computer network is a set of computers sharing resources located on or provided by network nodes. The computers use common communication protocols over digital interconnections to communicate with each other.",
            "Advanced Java is everything that goes beyond Core Java – most importantly the APIs defined in Java Enterprise Edition, includes Servlet programming, Web Services, the Persistence API, etc. "};
    BookMarkAdapter bookMarkAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.bookmarkPDFsRecyclerView);
        bookMarkAdapter = new BookMarkAdapter(getContext(), img, heading, totalReaders, description);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), linearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(bookMarkAdapter);
        return view;
    }
}