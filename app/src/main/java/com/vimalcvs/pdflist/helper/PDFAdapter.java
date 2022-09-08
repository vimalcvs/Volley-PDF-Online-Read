package com.vimalcvs.pdflist.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vimalcvs.pdflist.R;
import com.vimalcvs.pdflist.ui.PdfViewActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.MovieHolder> {

    private final Context context;
    private final List<PDFModel> list;


    public PDFAdapter(Context context, List<PDFModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        PDFModel PDFModel =list.get(position);

        holder.read_time.setText(PDFModel.getPDFRead());
        holder.pdf_name.setText(PDFModel.getPDFName());

        Glide.with(context)
                .load(PDFModel.getPDFImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.img_url);


        holder.list_relative.setOnClickListener(view -> {
            Intent intent=new Intent(context, PdfViewActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("pdf_name", PDFModel.getPDFName());
            bundle.putString("pdf_url", PDFModel.getPDFUrl());
            bundle.putString("img_url", PDFModel.getPDFImage());
            bundle.putString("read_time", PDFModel.getPDFRead());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder{


        MaterialCardView list_relative;
        ImageView img_url;
        TextView pdf_name, read_time;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            img_url = itemView.findViewById(R.id.iv_icon);
            pdf_name = itemView.findViewById(R.id.tv_name);
            read_time = itemView.findViewById(R.id.tv_date);
            list_relative = itemView.findViewById(R.id.list_relative);
        }
    }
}
