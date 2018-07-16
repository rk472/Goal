package com.smarttersstudio.goal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.smarttersstudio.goal.POJO.Post;
import com.smarttersstudio.goal.R;
import com.smarttersstudio.goal.ViewHolder.ForumViewHolder;

public class ForumAdapter extends FirebaseRecyclerAdapter<Post,ForumViewHolder> {
    Context c;
    public ForumAdapter(@NonNull FirebaseRecyclerOptions<Post> options,Context c) {
        super(options);
        this.c=c;
    }

    @Override
    protected void onBindViewHolder(@NonNull ForumViewHolder holder, int position, @NonNull Post model) {
        holder.setCommentButton(getRef(position).getKey(),model.getName(),model.getTime(),model.getTag(),model.getText());
        holder.setDp(model.getName());
        holder.setName(model.getName());
        holder.setTag(model.getTag());
        holder.setText(model.getText());
        holder.setTime(model.getTime());
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.forum_row,parent,false);
        return new ForumViewHolder(v);
    }
}
