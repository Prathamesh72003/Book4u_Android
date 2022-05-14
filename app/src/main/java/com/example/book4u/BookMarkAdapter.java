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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.BookMarkHolder> {
    View view;
    LayoutInflater layoutInflater;
    Context context;
    String img[] ;
    String heading[];
    String totalReaders[];
    String description[];
    String pdf_id[];
    private int pos;

    public BookMarkAdapter(Context context, String img[], String heading[], String[] totalReaders,  String[] description,String[] pdf_id) {
        this.context = context;
        this.img = img;
        this.heading = heading;
        this.totalReaders = totalReaders;
        this.description = description;
        this.pdf_id = pdf_id;
    }
    @NonNull
    @Override
    public BookMarkAdapter.BookMarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.bookmarkpdf_layout, parent, false);
        return new BookMarkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkAdapter.BookMarkHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.pdfName.setText(heading[position]);
        holder.totalReaders.setText(totalReaders[position]);
        holder.description.setText(description[position]);

        Picasso
                .get()
                .load(img[position]).into(holder.BookmarkImg);
        // pos = position;
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("imgName", img[position]);
                intent.putExtra("pdfName", heading[position]);
                intent.putExtra("pdfId", pdf_id[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return heading.length;
    }


    public class BookMarkHolder extends RecyclerView.ViewHolder {
        ImageView BookmarkImg;
        TextView pdfName;
        TextView totalReaders;
        TextView description;
        ConstraintLayout constraintLayout;

        public BookMarkHolder(@NonNull View itemView) {
            super(itemView);
            BookmarkImg = (ImageView) itemView.findViewById(R.id.pdfImg);
            pdfName = (TextView) itemView.findViewById(R.id.Pdf_name);
            totalReaders = (TextView) itemView.findViewById(R.id.total_readers);
            description = (TextView) itemView.findViewById(R.id.description);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.bookmarkDetailsBtn);
        }
    }
}