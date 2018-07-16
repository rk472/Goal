package com.smarttersstudio.goal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.smarttersstudio.goal.POJO.Comments;
import com.smarttersstudio.goal.R;
import com.smarttersstudio.goal.ViewHolder.CommentsViewHolder;

public class CommentsAdapter extends FirebaseRecyclerAdapter<Comments,CommentsViewHolder> {
    Context c;


    public CommentsAdapter(@NonNull FirebaseRecyclerOptions<Comments> options,Context c) {
        super(options);
        this.c=c;
    }


    @Override
    protected void onBindViewHolder(@NonNull CommentsViewHolder holder, int position, @NonNull Comments model) {
        holder.setDp(model.getUid());
        holder.setName(model.getUid());
        holder.setText(model.getText());
        holder.setTime(model.getTime());
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(c).inflate(R.layout.comment_row,parent,false);
        return new CommentsViewHolder(v);
    }
}
