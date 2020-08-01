package com.example.saksham.Student;

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

public class ArrayListNotification extends ArrayAdapter<CardsNotification> {
    Context context;

    public ArrayListNotification(Context context, int resourceId, List<CardsNotification> items) {
        super(context, resourceId, items);
    }

    public View getView(int position, View convertsView, ViewGroup parent) {
        CardsNotification card_items = getItem(position);
        if (convertsView == null) {
            convertsView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        TextView fname = (TextView) convertsView.findViewById(R.id.name);
        fname.setText(card_items.getFname());
        TextView lname= (TextView)convertsView.findViewById(R.id.lname);
        lname.setText(card_items.getLname());
        TextView status= (TextView)convertsView.findViewById(R.id.status);
        status.setText(card_items.getWorkStatus());
        ImageView pic= (ImageView)convertsView.findViewById(R.id.profileImage);
        Glide.with(getContext()).load(card_items.getProfileImageUrl()).into(pic);
        return convertsView;
    }
}
