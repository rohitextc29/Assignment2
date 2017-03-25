package com.example.macbookpro.assignment2;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by rohityadav on 3/19/17.
 */

public class ViewPagerAdapter extends PagerAdapter {

    int[] image;
    LayoutInflater inflater;
    Context context;

    public ViewPagerAdapter(Context context, int[] image){
        this.context=context;
        this.image=image;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==(RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView trailing;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.item,container,false);
        trailing=(ImageView)itemView.findViewById(R.id.trailing);
        trailing.setImageResource(image[position]);

        //add item.xml to viewpager
        ((ViewPager)container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((RelativeLayout)object);
    }
}
