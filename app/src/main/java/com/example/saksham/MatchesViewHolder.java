package com.example.saksham;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView mMatchId;
    public MatchesViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId= (TextView) itemView.findViewById(R.id.MatchId);
    }

    @Override
    public void onClick(View view) {

    }
}
