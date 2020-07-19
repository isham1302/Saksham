package com.example.saksham;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder> {
    private static final String TAG = "AdapterNotification";

    List<String> studentList;

    public AdapterNotification(List<String> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.row,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(studentList.get(position));
        holder.imageView.setImageResource(R.drawable.woman);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView,txtView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.iv);
            textView= itemView.findViewById(R.id.title_tv);
            txtView= itemView.findViewById(R.id.descp_tv);
        }
    }
}
