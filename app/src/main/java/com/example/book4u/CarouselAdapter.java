package com.example.book4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {

    View view;
    Context context;
    String data[];
    int img[];

    public CarouselAdapter(Context context, String data[], int img[]) {
        this.context = context;
        this.data = data;
        this.img = img;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(data[position % data.length]);
        holder.iv.setImageResource(img[position % data.length]);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length * 100000;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView);
            iv = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
