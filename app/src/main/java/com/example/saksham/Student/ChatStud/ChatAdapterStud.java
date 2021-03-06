package com.example.saksham.Student.ChatStud;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saksham.R;

import java.util.List;

public class ChatAdapterStud extends RecyclerView.Adapter<ChatViewHolderStud> {
    private List<ChatObjectStud> chatList;
    private Context context;

    public ChatAdapterStud(List<ChatObjectStud> matchesList, Context context){
        this.chatList= matchesList;
        this.context= context;
    }

    @NonNull
    @Override
    public ChatViewHolderStud onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,null,false);
        RecyclerView.LayoutParams lp= new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatViewHolderStud rev= new ChatViewHolderStud(layoutView);
        return rev;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolderStud holder, int position) {
        holder.mMessage.setText(chatList.get(position).getMessage());
        if (chatList.get(position).getCurrentUser()){
            holder.mMessage.setGravity(Gravity.END);
            holder.mMessage.setTextColor(Color.parseColor("#404040"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#F4F4F4"));
        }else {
            holder.mMessage.setGravity(Gravity.START);
            holder.mMessage.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4CB"));
        }
    }

    @Override
    public int getItemCount() {
        return this.chatList.size();
    }
}
