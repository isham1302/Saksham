package com.example.saksham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] topic;
    private int[] topicImage;

    public MainAdapter(Context c, String[] topic, int[] topicImage){
        context= c;
        this.topic= topic;
        this.topicImage= topicImage;
    }
    @Override
    public int getCount() {
        return topic.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view= inflater.inflate(R.layout.row_item,null);
        }
        ImageView imageView= view.findViewById(R.id.image_view);
        TextView textView= view.findViewById(R.id.text_view);

        imageView.setImageResource(topicImage[i]);
        textView.setText(topic[i]);
        return view;
    }
}
