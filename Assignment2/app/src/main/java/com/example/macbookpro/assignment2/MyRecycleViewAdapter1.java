package com.example.macbookpro.assignment2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rohityadav on 3/19/17.
 */

public class MyRecycleViewAdapter1 extends Adapter<MyRecycleViewAdapter1.MyViewHolder>{
    private List<DataObject> cityList;
    private Context context;

    public MyRecycleViewAdapter1(List<DataObject> cityList, Context context){
        this.cityList=cityList;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView label;
        private TextView desc;
        private ImageView famous_pic;

        public MyViewHolder(View view) {
            super(view);
            label = (TextView) view.findViewById(R.id.textView);
            desc = (TextView) itemView.findViewById(R.id.textView2);
            famous_pic = (ImageView) itemView.findViewById(R.id.famous_pic);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.famous_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataObject data = cityList.get(position);
        holder.label.setText(data.getmText1());
        holder.desc.setText(data.getmText2());
        Resources res = context.getResources();
        String mDrawableName = data.getPic_text();
        int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());
        System.out.println("resID - - "+resID);
        if(resID!=0){
            Drawable drawable = res.getDrawable(resID );
            holder.famous_pic.setImageDrawable(drawable );
        }
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
}
