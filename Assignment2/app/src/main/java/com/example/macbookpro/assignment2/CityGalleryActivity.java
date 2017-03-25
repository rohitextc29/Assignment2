package com.example.macbookpro.assignment2;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by rohityadav on 3/19/17.
 */
public class CityGalleryActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter adapter;
    int[] img;
    private static int currentpage=0;
    private static int numpages=0;
    private SharedPreferences city;
    private String city_name,famous_array,famous_array_desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);

        Bundle extras = getIntent().getExtras();
        city_name = extras.getString("city_name");
        famous_array = extras.getString("famous_array");
        famous_array_desc = extras.getString("famous_array_desc");

        // getSupportActionBar().setTitle(city_name+" famous place");

        city = getSharedPreferences(city_name,0);
        getSupportActionBar().setTitle(city_name+" famous place");


        String[] famous_array;

        Map<String, ?> allEntries =city.getAll();
        if(allEntries!=null && !allEntries.isEmpty()){
            int i=0;
            System.out.println("inside shared");
            List<String> title_list=new ArrayList<>();
            List<String> desc_list=new ArrayList<>();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                title_list.add(i,entry.getKey());
                //city_data.edit().putString(c[i],city_desc[i]).apply();
                //city_map.put(c[i],city_desc[i]);

                i++;
            }
            famous_array=new String[title_list.size()];
            title_list.toArray(famous_array);

        }
        else{
            famous_array=getTitle(city_name);
        }


        //img=new int[]{R.drawable.bengaluru,R.drawable.charminar,R.drawable.chennai,R.drawable.cubbonpark};

        img=getImages(famous_array);

        viewPager=(ViewPager)findViewById(R.id.pager);
        adapter=new ViewPagerAdapter(CityGalleryActivity.this,img);
        viewPager.setAdapter(adapter);



        //circleindicator code

        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentpage=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state== ViewPager.SCROLL_STATE_IDLE){
                    int pagecount=img.length;
                    if(currentpage==0){
                        viewPager.setCurrentItem(pagecount-1,false);
                    }else if(currentpage==pagecount-1){
                        viewPager.setCurrentItem(0,false);
                    }
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();

    }

    public String[] getTitle(String name){
        String[] title;
        switch (name){
            case "Mumbai":
                title=getResources().getStringArray(R.array.Mumbai_famous);
                break;
            case "Pune":
                title=getResources().getStringArray(R.array.Pune_famous);
                break;
            case "Kolkata":
                title=getResources().getStringArray(R.array.Kolkata_famous);
                break;
            case "Hyderabad":
                title=getResources().getStringArray(R.array.Hyderabad_famous);
                break;
            case "Bengaluru":
                title=getResources().getStringArray(R.array.Bengaluru_famous);
                break;
            case "Delhi":
                title=getResources().getStringArray(R.array.Delhi_famous);
                break;
            case "Jaipur":
                title=getResources().getStringArray(R.array.Jaipur_famous);
                break;
            case "Chennai":
                title=getResources().getStringArray(R.array.Chennai_famous);
                break;
            default:
                throw new IllegalArgumentException("Invalid array: " + name);
        }
        return title;
    }

    public int[] getImages(String[] title){

        int[] temp;

        List<Integer> temp_list = new ArrayList<>();

        Resources res = getResources();
        for(int i=0;i<title.length;i++){
            String mDrawableName = title[i].replaceAll("\\s","").toLowerCase();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            System.out.println("resID - - "+resID);
            if(resID!=0){
                temp_list.add(resID);
            }
        }
        temp=new int[temp_list.size()];
        //temp_list.toArray(temp);

        int i=0;
        for (Integer integer : temp_list) {
            temp[i] = integer.intValue();
            i++;
        }

        return temp;
    }

}
