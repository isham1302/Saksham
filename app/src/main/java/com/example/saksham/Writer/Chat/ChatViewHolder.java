package com.example.saksham.Writer.Chat;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saksham.R;

public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMessage;
    public LinearLayout mContainer;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mMessage=itemView.findViewById(R.id.Message);
        mContainer= itemView.findViewById(R.id.container);
    }

    @Override
    public void onClick(View view) {

    }
}
