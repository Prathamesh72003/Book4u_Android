package com.example.book4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MyUploadsPdfAdapter extends RecyclerView.Adapter<MyUploadsPdfAdapter.MyUploadsPdfViewHolder> {

    View view;
    Context context;
    LayoutInflater layoutInflater;
    int img[];
    String[] pdf_name;

    public MyUploadsPdfAdapter(Context context, int[] img, String[] pdf_name) {
        this.context = context;
        this.img = img;
        this.pdf_name = pdf_name;
    }

    @NonNull
    @Override
    public MyUploadsPdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.mypdf_layout, parent, false);
        return new MyUploadsPdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyUploadsPdfViewHolder holder, int position) {
        holder.imageView.setImageResource(img[position]);
        holder.textView.setText(pdf_name[position]);
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public class MyUploadsPdfViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyUploadsPdfViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.pdfImg);
            textView = (TextView) itemView.findViewById(R.id.Pdf_name);
        }
    }
}