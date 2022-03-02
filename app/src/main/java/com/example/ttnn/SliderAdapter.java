package com.example.ttnn;

import android.content.Context;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
     public int[] img = {
             R.drawable.imgslider_main_1,
             R.drawable.imgslider_main_2,
             R.drawable.imgslider_main_3
     };

    public SliderAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.mainactivity_slider_layout, container, false);

        ImageView SliderImage = (ImageView) view.findViewById(R.id.MainActivity_ImageSlider);

        SliderImage.setScaleType(ImageView.ScaleType.FIT_XY);
        SliderImage.setImageResource(img[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView( (LinearLayout) object);
    }
}
