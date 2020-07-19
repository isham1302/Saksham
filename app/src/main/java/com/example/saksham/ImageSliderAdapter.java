package com.example.saksham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<SliderViewHolder>{
    Context context;
    List<ImageSliderModal> imageSliderModalList;

    public ImageSliderAdapter(Context context, List<ImageSliderModal> imageSliderModalList) {
        this.context = context;
        this.imageSliderModalList = imageSliderModalList;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.sliderImageView.setImageResource(imageSliderModalList.get(position).getImages());
    }

    @Override
    public int getCount() {
        return imageSliderModalList.size();
    }
}

class SliderViewHolder extends SliderViewAdapter.ViewHolder {
   ImageView sliderImageView;
   public SliderViewHolder(View itemView) {
       super(itemView);
       sliderImageView= itemView.findViewById(R.id.image_slider);
   }
}
