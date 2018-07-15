package com.smarttersstudio.goal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarttersstudio.goal.ForumActivity;
import com.smarttersstudio.goal.R;
import com.smarttersstudio.goal.ViewHolder.CompanyViewHolder;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyViewHolder> {
    private String [] names;
    private Context c;
    public CompanyAdapter(String [] names, Context c) {
        this.names=names;
        this.c=c;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.company_row,parent,false);
        return new CompanyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, final int position) {
        holder.setName(names[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }
}
