package com.example.macbookpro.assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rohityadav on 2/16/17.
 */

public class FamousPlaceActivity1 extends AppCompatActivity {

    private String city_name,famous_array,famous_array_desc;
    private RecyclerView recyclerView;
    private MyRecycleViewAdapter1 mAdapter;
    private static String LOG_TAG = "CardViewActivity";
    private Button add_famous;

    private SharedPreferences city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.famous_layout);

        Bundle extras = getIntent().getExtras();
        city_name = extras.getString("city_name");
        famous_array = extras.getString("famous_array");
        famous_array_desc = extras.getString("famous_array_desc");

        getSupportActionBar().setTitle(city_name+" famous place");

        city = getSharedPreferences(city_name,0);

        String[] famous_array;
        String[] famous_desc_array;

        Map<String, ?> allEntries =city.getAll();
        if(allEntries!=null && !allEntries.isEmpty()){
            int i=0;
            System.out.println("inside shared");
            List<String> title_list=new ArrayList<>();
            List<String> desc_list=new ArrayList<>();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                title_list.add(i,entry.getKey());
                desc_list.add(i,entry.getValue().toString());
                //city_data.edit().putString(c[i],city_desc[i]).apply();
                //city_map.put(c[i],city_desc[i]);

                i++;
            }
            famous_array=new String[title_list.size()];
            title_list.toArray(famous_array);

            famous_desc_array=new String[desc_list.size()];
            desc_list.toArray(famous_desc_array);
        }
        else{
            famous_array=getTitle(city_name);
            famous_desc_array=getTitleDesc(city_name);
        }

        System.out.println("famous_array-----"+famous_array);
        /*int resId = getApplicationContext().getResources().getIdentifier(famous_array, "string-array", getPackageName());
        String[] foo_array = getResources().getStringArray(resId);
        System.out.println(foo_array);*/


        //String[] famous_array=getResources().getStringArray(R.array.Mumbai_famous);
        System.out.println(famous_array.length);

        //String[] famous_desc_array=getResources().getStringArray(R.array.Mumbai_famous_desc);
        System.out.println(famous_desc_array.length);


        add_famous=(Button)findViewById(R.id.add_famous);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mAdapter = new MyRecycleViewAdapter1(getDataSet(famous_array,famous_desc_array),getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        add_famous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FamousPlaceActivity1.this,AddNewFamousActivity.class);
                intent.putExtra("city_name",city_name);

                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //String[] city_name={"Mumbai","Pune","Kolkata","Hyderabad","Bengaluru","Delhi","Jaipur","Chennai"};

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

    public String[] getTitleDesc(String name){
        String[] title_desc;
        switch (name){
            case "Mumbai":
                title_desc=getResources().getStringArray(R.array.Mumbai_famous_desc);
                break;
            case "Pune":
                title_desc=getResources().getStringArray(R.array.Pune_famous_desc);
                break;
            case "Kolkata":
                title_desc=getResources().getStringArray(R.array.Kolkata_famous_desc);
                break;
            case "Hyderabad":
                title_desc=getResources().getStringArray(R.array.Hyderabad_famous_desc);
                break;
            case "Bengaluru":
                title_desc=getResources().getStringArray(R.array.Bengaluru_famous_desc);
                break;
            case "Delhi":
                title_desc=getResources().getStringArray(R.array.Delhi_famous_desc);
                break;
            case "Jaipur":
                title_desc=getResources().getStringArray(R.array.Jaipur_famous_desc);
                break;
            case "Chennai":
                title_desc=getResources().getStringArray(R.array.Chennai_famous_desc);
                break;
            default:
                throw new IllegalArgumentException("Invalid array: " + name);
        }
        return title_desc;
    }

    private ArrayList<DataObject> getDataSet(String[] title, String[] desc) {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < title.length; index++) {
            DataObject obj = new DataObject(title[index],
                    desc[index],title[index].replaceAll("\\s","").toLowerCase());
            results.add(index, obj);
            city.edit().putString(title[index],desc[index]).apply();
        }
        return results;
    }



}
