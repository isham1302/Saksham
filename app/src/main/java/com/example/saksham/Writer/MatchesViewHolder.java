package com.example.saksham.Writer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saksham.Writer.Chat.Chat;
import com.example.saksham.R;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchId,mMatchName;
    public ImageView mMatchImage;
    public MatchesViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId= (TextView) itemView.findViewById(R.id.MatchId);
        mMatchName= (TextView) itemView.findViewById(R.id.MatchName);
        mMatchImage= (ImageView) itemView.findViewById(R.id.MatchImage);
    }

    @Override
    public void onClick(View view) {
        Intent intent= new Intent(view.getContext(), Chat.class);
        Bundle bundle= new Bundle();
        bundle.putString("matchId",mMatchId.getText().toString());
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}
