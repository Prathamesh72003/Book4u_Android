package com.example.book4u;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.SearchViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    View view;

    ArrayList<String> id =  new ArrayList<String>();
    ArrayList<String> img_url =  new ArrayList<String>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String>  sub =  new ArrayList<String>();

    public searchAdapter(Context context, ArrayList<String> pdfId, ArrayList<String> Img, ArrayList<String> name, ArrayList<String> sub) {
        this.context = context;
        this.id = pdfId;
        this.img_url = Img;
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
    public void onBindViewHolder(@NonNull SearchViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso
                .get()
                .load(img_url.get(position))
                .into(holder.pdfimg);
        holder.pdfname.setText(name.get(position));
        holder.pdfsub.setText(sub.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pdfId", id.get(position));
                intent.putExtra("imgName", img_url.get(position));
                intent.putExtra("pdfName", name.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return img_url  == null ? 0 : img_url.size();
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