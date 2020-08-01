package com.example.saksham.Writer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saksham.R;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<Cards> {
    Context context;

    public arrayAdapter(Context context, int resourceId, List<Cards> items) {
        super(context, resourceId, items);
    }


    public View getView(int position, View convertsView, ViewGroup parent) {
        Cards card_items = getItem(position);
        if (convertsView == null) {
            convertsView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView fname = (TextView) convertsView.findViewById(R.id.name);
        fname.setText(card_items.getFname());
        TextView lname = (TextView) convertsView.findViewById(R.id.lname);
        lname.setText(card_items.getLname());
        TextView examName = (TextView) convertsView.findViewById(R.id.exam_name);
        examName.setText(card_items.getExamName());
        TextView examMed = (TextView) convertsView.findViewById(R.id.exam_medium);
        examMed.setText(card_items.getCourse());
        TextView date = (TextView) convertsView.findViewById(R.id.exam_date);
        date.setText(card_items.getMediumOfPaper());
        TextView add = (TextView) convertsView.findViewById(R.id.exam_add);
        add.setText(card_items.getCollege_School_Name());
        TextView school_clg = (TextView) convertsView.findViewById(R.id.exam_hall);
        school_clg.setText(card_items.getAddress());

        /*ImageView pic= (ImageView)convertsView.findViewById(R.id.profileImage);
        Glide.with(getContext()).load(card_items.getProfileImageUrl()).into(pic);*/
        return convertsView;
    }
}

