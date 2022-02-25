package com.example.book4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrendingPDFAdapter extends RecyclerView.Adapter<TrendingPDFAdapter.RecycleViewHolder> {

    Context context;
    String data[];
    int img[];

    LayoutInflater layoutInflater;

    public TrendingPDFAdapter(Context context, String data[], int img[]){
        this.context = context;
        this.data = data;
        this.img = img;
    }

    @NonNull


    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.trendingpdf_layout, parent,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
            holder.textView.setText(data[position]);
            holder.imageView.setImageResource(img[position]);
    }

    @Override
    public int getItemCount() {

        return data.length;
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

         public RecycleViewHolder(@NonNull View itemView) {
             super(itemView);
             imageView = (ImageView) itemView.findViewById(R.id.pdfImg);
             textView = (TextView) itemView.findViewById(R.id.pdfName);

         }
     }
}
