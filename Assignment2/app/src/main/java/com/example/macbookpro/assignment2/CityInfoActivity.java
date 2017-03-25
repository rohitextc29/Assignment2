package com.example.macbookpro.assignment2;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rohityadav on 2/14/17.
 */

public class CityInfoActivity extends AppCompatActivity {

    private ImageView city_intro_img;
    private TextView city_desc;
    private String city_name;
    private String desc;
    private Button gallery,famous;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_info_layout);
        city_intro_img = (ImageView)findViewById(R.id.city_intro_img);
        city_desc=  (TextView)findViewById(R.id.city_desc);
        gallery = (Button)findViewById(R.id.gallery);
        famous = (Button)findViewById(R.id.famous);

        Bundle extras = getIntent().getExtras();
        city_name = extras.getString("city_name");
        desc = extras.getString("city_desc");

        getSupportActionBar().setTitle(city_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        city_desc.setText(desc);
        Resources res = getResources();
        String mDrawableName = city_name.toLowerCase();
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        System.out.println("resID - - "+resID);
        if(resID!=0){
            Drawable drawable = res.getDrawable(resID );
            city_intro_img.setImageDrawable(drawable );
        }
        else{
            String photoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/edwisor/"+mDrawableName+".jpg";
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap b = BitmapFactory.decodeFile(photoPath, options);
            city_intro_img.setImageBitmap(b);

        }

        famous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CityInfoActivity.this,FamousPlaceActivity1.class);
                intent.putExtra("city_name",city_name);
                intent.putExtra("famous_array",city_name+"_famous");
                intent.putExtra("famous_array_desc",city_name+"_famous_desc");

                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CityInfoActivity.this,CityGalleryActivity.class);
                intent.putExtra("city_name",city_name);
                intent.putExtra("famous_array",city_name+"_famous");
                intent.putExtra("famous_array_desc",city_name+"_famous_desc");
                startActivity(intent);
            }
        });




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
}
