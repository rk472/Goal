package com.smarttersstudio.goal.ViewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.smarttersstudio.goal.ForumActivity;
import com.smarttersstudio.goal.R;

public class CompanyViewHolder extends RecyclerView.ViewHolder {
    private View v;
    private TextView nameText;
    public CompanyViewHolder(View itemView) {
        super(itemView);
        v=itemView;
        nameText=v.findViewById(R.id.company_name);
    }
    public void setName(final String name){
        nameText.setText(name);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), ForumActivity.class);
                i.putExtra("company",name);
                v.getContext().startActivity(i);
            }
        });
    }
}
