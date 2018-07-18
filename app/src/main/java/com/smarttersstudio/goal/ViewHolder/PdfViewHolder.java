package com.smarttersstudio.goal.ViewHolder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.smarttersstudio.goal.R;

public class PdfViewHolder extends RecyclerView.ViewHolder {
    View v;
    TextView descView;
    public PdfViewHolder(View itemView) {
        super(itemView);
        v=itemView;
        descView=v.findViewById(R.id.pdf_desc);

    }
    public void setDesc(String desc){
        descView.setText(desc);
    }
    public void setClick(final String uri){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(uri), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);
            }
        });
    }
}
