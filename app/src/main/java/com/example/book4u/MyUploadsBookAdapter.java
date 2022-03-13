package com.example.book4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyUploadsBookAdapter extends RecyclerView.Adapter<MyUploadsBookAdapter.MyUploadsBookViewHolder> {

    Context context;
    View view;
    LayoutInflater layoutInflater;
    int img[];
    String[] book_name;

    public MyUploadsBookAdapter(Context context, int[] img, String[] book_name) {
        this.context = context;
        this.img = img;
        this.book_name = book_name;
    }

    @NonNull
    @Override
    public MyUploadsBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.mybook_layout, parent, false);
        return new MyUploadsBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyUploadsBookViewHolder holder, int position) {
        holder.imageView.setImageResource(img[position]);
        holder.textView.setText(book_name[position]);
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public class MyUploadsBookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyUploadsBookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.bookImg);
            textView = (TextView) itemView.findViewById(R.id.book_name);
        }
    }
}