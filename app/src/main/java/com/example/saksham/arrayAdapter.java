package com.example.saksham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<Adapter> {
    Context context;
    public arrayAdapter(Context context, int resourceId, List<Adapter> items){
        super(context,resourceId,items);
    }


    public View getView(int position, View convertsView, ViewGroup parent){
        Adapter card_items= getItem(position);
        if (convertsView== null){
            convertsView= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }
        TextView fname= (TextView)convertsView.findViewById(R.id.f_name);
        TextView lname= (TextView)convertsView.findViewById(R.id.l_name);
        TextView examname = (TextView)convertsView.findViewById(R.id.exam_name);
        TextView paper = (TextView)convertsView.findViewById(R.id.paper);
        TextView date = (TextView)convertsView.findViewById(R.id.date);
        TextView venu = (TextView)convertsView.findViewById(R.id.venu);
        TextView school_clg = (TextView)convertsView.findViewById(R.id.sch_clg_name);
        TextView course = (TextView)convertsView.findViewById(R.id.course_obtained);
        ImageView pic= (ImageView)convertsView.findViewById(R.id.ProfileImage);
        fname.setText(card_items.getFname());
        lname.setText(card_items.getLname());
        examname.setText(card_items.getExamname());
        paper.setText(card_items.getMedium_paper());
        date.setText(card_items.getDateOfExam());
        venu.setText(card_items.getVenu());
        school_clg.setText(card_items.getCollege_School_Name());
        course.setText(card_items.getCourse());
        Glide.with(getContext()).load(card_items.getProfileImageUrl()).into(pic);
        return convertsView;
    }

}
