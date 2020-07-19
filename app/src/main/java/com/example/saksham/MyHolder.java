package com.example.saksham;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView mImageView;
    TextView mTitle,mDescp;
    ItemClickListener itemClickListener;
     MyHolder(@NonNull View itemView) {
        super(itemView);
        this.mImageView= itemView.findViewById(R.id.iv);
        this.mTitle= itemView.findViewById(R.id.title_tv);
        this.mDescp= itemView.findViewById(R.id.descp_tv);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
         this.itemClickListener.onItemClickListener(view,getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic){
         this.itemClickListener= ic;
    }
}
