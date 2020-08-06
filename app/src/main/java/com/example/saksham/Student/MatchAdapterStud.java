package com.example.saksham.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.saksham.R;
import com.example.saksham.Student.MatchObjectStud;
import com.example.saksham.Student.MatchViewHolderStud;

import java.util.List;

public class MatchAdapterStud extends RecyclerView.Adapter<MatchViewHolderStud> {
    private List<MatchObjectStud> matchesList;
    private Context context;

    public MatchAdapterStud(List<MatchObjectStud> matchesList, Context context) {
        this.matchesList = matchesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchViewHolderStud onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches,null,false);
        RecyclerView.LayoutParams lp= new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchViewHolderStud rcv= new MatchViewHolderStud((layoutView));
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolderStud holder, int position) {
        holder.mMatchId.setText(matchesList.get(position).getUserId());
        holder.mMatchName.setText(matchesList.get(position).getFirstName());
        Glide.with(context).load(matchesList.get(position).getProfileImageUrl()).into(holder.mMatchImage);
    }

    @Override
    public int getItemCount() {
        return this.matchesList.size() ;
    }
}
