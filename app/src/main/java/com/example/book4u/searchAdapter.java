package com.example.book4u;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.SearchViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    View view;

    int Img[];
    String name[];
    String sub[];

    public searchAdapter(Context context, int[] Img, String[] name, String[] sub) {
        this.context = context;
        this.Img = Img;
        this.name = name;
        this.sub = sub;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.search_layout, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.pdfimg.setImageResource(Img[position]);
        holder.pdfname.setText(name[position]);
        holder.pdfsub.setText(sub[position]);
    }

    @Override
    public int getItemCount() {
        return Img.length;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView pdfimg;
        TextView pdfname,pdfsub;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfimg = (ImageView) itemView.findViewById(R.id.SearchImg);
            pdfname = (TextView) itemView.findViewById(R.id.Search_name);
            pdfsub = (TextView) itemView.findViewById(R.id.subject);
        }
    }
}
