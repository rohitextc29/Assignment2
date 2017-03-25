package com.example.macbookpro.assignment2;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Admin on 17-Feb-17.
 */

public class AddNewFamousActivity extends AppCompatActivity {

    private EditText title,desc;
    private Button picture,save;
    private ImageView photo;
    private String city_name;
    private String destination_file_path="";

    static final int TAKE_PHOTO_CODE = 1;
    private static final int REQUEST_CODE = 0x11;
    private SharedPreferences city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_famous_layout);



        Bundle extras = getIntent().getExtras();
        city_name = extras.getString("city_name");

        city = getSharedPreferences(city_name,0);

       /* final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/edwisor/"+city_name+"/";
        File newdir = new File(dir);
        if(newdir.exists()){
            newdir.mkdirs();
        }*/


        title = (EditText)findViewById(R.id.title);
        desc = (EditText)findViewById(R.id.desc);
        picture = (Button) findViewById(R.id.picture);
        save = (Button)findViewById(R.id.save);
        photo = (ImageView) findViewById(R.id.photo);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().toString().trim().equals("") && !desc.getText().toString().trim().equals("")){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        //use checkSelfPermission()
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},
                                    REQUEST_CODE);
                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant
                        }
                        else{
                            readyforCamera();
                        }
                    } else {
                        //simply use the required feature
                        //as the user has already granted permission to them during installation
                        readyforCamera();
                    }

                    //takephoto(dir+title.getText().toString().replaceAll("\\s","").trim().toLowerCase()+".jpg");
                }
                else{
                    Toast.makeText(AddNewFamousActivity.this,"Enter full information.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().toString().trim().equals("") && !desc.getText().toString().trim().equals("")){
                    city.edit().putString(title.getText().toString().trim(),desc.getText().toString().trim()).apply();
                    finish();
                }
                else{
                    Toast.makeText(AddNewFamousActivity.this,"Enter full information.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        getSupportActionBar().setTitle("Add New Place");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                // save file
                try {
                    readyforCamera();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getApplicationContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readyforCamera(){
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile(city_name,title.getText().toString().trim());
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(camera_intent,TAKE_PHOTO_CODE);
    }

    public File getFile(String city_name,String place){
        destination_file_path="";
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/edwisor/"+city_name);
        if(!folder.exists()){
            folder.mkdir();
        }
        File image_file = new File(folder,place+".jpg");
        destination_file_path=image_file.getPath();
        System.out.println("destination_file_path -- "+destination_file_path);
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //String path = "sdcard/camera_app/cam_image.jpg";
        photo.setImageDrawable(Drawable.createFromPath(destination_file_path));

    }

}
