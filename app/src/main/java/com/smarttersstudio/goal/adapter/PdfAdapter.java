package com.smarttersstudio.goal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.smarttersstudio.goal.POJO.PDF;
import com.smarttersstudio.goal.R;
import com.smarttersstudio.goal.ViewHolder.PdfViewHolder;

public class PdfAdapter extends FirebaseRecyclerAdapter<PDF,PdfViewHolder> {
   Context c;
    public PdfAdapter(@NonNull FirebaseRecyclerOptions<PDF> options,Context c) {
        super(options);
        this.c=c;
    }


    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.pdf_row,parent,false);
        return new PdfViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull PdfViewHolder holder, int position, @NonNull PDF model) {
        holder.setDesc(model.getDesc());
        holder.setClick(model.getUrl());
    }
}
