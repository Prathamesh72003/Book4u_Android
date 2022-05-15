package com.example.book4u;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadHolder> {

    View view;
    Context context;
    LayoutInflater layoutInflater;

    String[] pdf_name, local_path;

    public DownloadAdapter(Context context, String[] pdf_name, String[] local_path){
        this.context = context;
        this.local_path = local_path;
        this.pdf_name = pdf_name;
    }
    @NonNull
    @Override

    public DownloadAdapter.DownloadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.downloadpdf_layout, parent,false);

        return new DownloadHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.DownloadHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.pdf_name.setText(pdf_name[position]);

        holder.readNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(local_path[position]),"application/pdf");

                Log.d("path", Environment.getExternalStorageDirectory().toString());
                Intent chooser = Intent.createChooser(browserIntent, "Open PDF");
                chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
                context.startActivity(chooser);

            }
        });
    }

    @Override
    public int getItemCount() {
        return pdf_name.length;
    }

    public class DownloadHolder extends RecyclerView.ViewHolder {

        Button readNowBtn;
        TextView pdf_name;
        public DownloadHolder(@NonNull View itemView) {
            super(itemView);
            readNowBtn = (Button) itemView.findViewById(R.id.readNowBtn);
            pdf_name = (TextView) itemView.findViewById(R.id.pdf_name);
        }
    }
}