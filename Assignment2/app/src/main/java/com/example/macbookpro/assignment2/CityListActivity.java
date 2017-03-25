package com.example.macbookpro.assignment2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rohityadav on 2/12/17.
 */

public class CityListActivity extends AppCompatActivity {

    private List<String> city_list = new ArrayList<>();
    private GridView city_grid;
    private String[] city_name={"Mumbai","Pune","Kolkata","Hyderabad","Bengaluru","Delhi","Jaipur","Chennai"};
    private String[] city_desc={"Mumbai (formerly called Bombay) is a densely populated city on India’s west coast. A financial center, it's India's largest city. On the Mumbai Harbour waterfront stands the iconic Gateway of India stone arch, built by the British Raj in 1924. Offshore, nearby Elephanta Island holds ancient cave temples dedicated to the Hindu god Shiva. The city's also famous as the heart of the Bollywood film industry.",
    "Pune is a sprawling city in the western Indian state of Maharashtra. It was once the base of the Peshwas (prime ministers) of the Maratha Empire, which lasted from 1674 to 1818. It's known for the grand Aga Khan Palace, built in 1892 and now a memorial to Mahatma Gandhi, whose ashes are preserved in the garden. The 8th-century Pataleshwar Cave Temple is dedicated to the Hindu god Shiva.",
    "Kolkata (formerly Calcutta) is the capital of India's West Bengal state. Founded as an East India Company trading post, it was India's capital under the British Raj from 1773–1911. Today it’s known for its grand colonial architecture, art galleries and cultural festivals. It’s also home to Mother House, headquarters of the Missionaries of Charity, founded by Mother Teresa, whose tomb is on site.",
    "Hyderabad is the capital of southern India's Telangana state. A major center for the technology industry, it's home to many upscale restaurants and shops. Its historic sites include Golconda Fort, a former diamond-trading center that was once the Qutb Shahi dynastic capital. The Charminar, a 16th-century mosque whose 4 arches support towering minarets, is an old city landmark near the long-standing Laad Bazaar.",
    "Bengaluru (also called Bangalore) is the capital of India's southern Karnataka state. The center of India's high-tech industry, the city is also known for its parks and nightlife. By Cubbon Park, Vidhana Soudha is a Neo-Dravidian legislative building. Former royal residences include 19th-century Bangalore Palace, modeled after England’s Windsor Castle, and Tipu Sultan’s Summer Palace, an 18th-century teak structure.",
    "Delhi, India’s capital territory, is a massive metropolitan area in the country’s north. In Old Delhi, a neighborhood dating to the 1600s, stands the imposing Mughal-era Red Fort, a symbol of India, and the sprawling Jama Masjid mosque, whose courtyard accommodates 25,000 people. Nearby is Chandni Chowk, a vibrant bazaar filled with food carts, sweets shops and spice stalls.",
    "Jaipur is the capital of India’s Rajasthan state. It evokes the royal family that once ruled the region and that, in 1727, founded what is now called the Old City, or “Pink City” for its trademark building color. At the center of its stately street grid (notable in India) stands the opulent, colonnaded City Palace complex. With gardens, courtyards and museums, part of it is still a royal residence.",
    "Chennai, on the Bay of Bengal in eastern India, is the capital of the state of Tamil Nadu. The city is home to Fort St. George, built in 1644 and now a museum showcasing the city’s roots as a British military garrison and East India Company trading outpost, when it was called Madras. Religious sites include Kapaleeshwarar Temple, adorned with carved and painted gods, and St. Mary’s, a 17th-century Anglican church."};

    private HashMap<String,String> city_map = new HashMap<>();
    private String mypreference = "city_list";
    private SharedPreferences city_data;
    private HashMap<String,Boolean> for_add = new HashMap<>();
    private EditText editText,editText2;
    private Button save,cancel,camera;

    private int TAKE_PHOTO_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_layout);
        city_grid = (GridView)findViewById(R.id.city_grid);

        city_data = getSharedPreferences(mypreference,0);


        city_list=getCityList(city_name);
        System.out.println(city_list);

        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/edwisor/";
        File newdir = new File(dir);
        newdir.mkdirs();


        getSupportActionBar().setTitle("City List");

        city_grid.setAdapter(new AddCitiesAdapter());

        city_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                System.out.println("view.getTag()--"+view.getTag());
                if(view.getTag().toString().equals("add")){
                    final Dialog dialog = new Dialog(CityListActivity.this);
                    dialog.setTitle("Add Location");
                    dialog.setContentView(R.layout.add_new);
                    editText = (EditText)dialog.findViewById(R.id.editText);
                    editText2 = (EditText)dialog.findViewById(R.id.editText2);
                    camera = (Button)dialog.findViewById(R.id.camera);
                    save = (Button)dialog.findViewById(R.id.save);
                    cancel = (Button)dialog.findViewById(R.id.cancel);

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!editText.getText().toString().trim().equals("") && !editText2.getText().toString().trim().equals("")){
                                city_data.edit().putString(editText.getText().toString().trim(),editText2.getText().toString().trim()).apply();

                                city_list.remove(city_list.size()-1);

                                city_list.add(city_list.size()-1,editText.getText().toString().trim());
                                for_add.put(editText.getText().toString().trim(),false);

                                city_list.add(city_list.size(),"Add your local place");
                                for_add.put("Add your local place",true);

                                dialog.dismiss();
                                city_grid.setAdapter(new AddCitiesAdapter());
                            }
                            else {
                                Toast.makeText(CityListActivity.this,"Enter full information",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!editText.getText().toString().trim().equals("")){
                                takephoto(dir+editText.getText().toString().trim().toLowerCase()+".jpg");
                            }
                            else{
                                Toast.makeText(CityListActivity.this,"Enter full information",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    dialog.show();
                }
                else{
                    Intent intent = new Intent(CityListActivity.this,CityInfoActivity.class);
                    intent.putExtra("city_name",city_list.get(i));
                    intent.putExtra("city_desc",city_data.getString(city_list.get(i),"No description"));
                    startActivity(intent);
                }
            }
        });

        city_list.add(city_list.size(),"Add your local place");
        for_add.put("Add your local place",true);
        city_grid.setAdapter(new AddCitiesAdapter());

    }

    public void takephoto(String full_path){
        //String file = dir+count+".jpg";
        File newfile = new File(full_path);
        try {
            newfile.createNewFile();
        }
        catch (IOException e)
        {
        }

        Uri outputFileUri = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public List<String> getCityList(String[] c){
        Map<String, ?> allEntries =city_data.getAll();
        System.out.println("allEntries----"+allEntries);
        List<String> city = new ArrayList<>();
        if(allEntries!=null && !allEntries.isEmpty()){
            int i=0;
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                city.add(i,entry.getKey());
                //city_data.edit().putString(c[i],city_desc[i]).apply();
                //city_map.put(c[i],city_desc[i]);
                for_add.put(entry.getKey(),false);
                i++;
            }
        }
        else{
            for(int i=0;i<c.length;i++){

                city.add(i,c[i]);
                city_data.edit().putString(c[i],city_desc[i]).apply();
                //city_map.put(c[i],city_desc[i]);
                for_add.put(c[i],false);
            }

        }


        return city;

    }

    class AddCitiesAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return city_list.size();
        }

        @Override
        public Object getItem(int i) {
            return city_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

     /*   @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view==null){

                    view = getLayoutInflater().inflate(R.layout.add_local1,viewGroup,false);


                holder = new ViewHolder();
                holder.bindView(view);
                view.setTag(holder);
            }
            else{
                holder = (ViewHolder) view.getTag();
            }
            System.out.println("i= "+i+"--------"+city_list.get(i));
            System.out.println("i= "+i+"--------"+city_list.get(i)+" for_add.get(city_list.get(i)) "+for_add.get(city_list.get(i)));
            if(for_add.get(city_list.get(i))){
                holder.imageView.setVisibility(View.GONE);
            }
            else {
                holder.imageView.setVisibility(View.VISIBLE);
            }
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(CityListActivity.this,"Add clicked.",Toast.LENGTH_SHORT).show();
                }
            });
            holder.textView.setText(city_list.get(i));
                //holder.imageView.setBackgroundResource(R.drawable.add_location);





            return view;
        }
        */

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //ViewHolder holder = null;
            View rowView=convertView;

            // View view = super.getView(position, convertView, parent);
            try
            {
                //if(rowView==null)
                //{
                // 1. Create inflater
                LayoutInflater inflater = (LayoutInflater) CityListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // 2. Get rowView from inflater
                rowView = inflater.inflate(R.layout.add_local, parent, false);

                ImageView imageView = (ImageView)rowView.findViewById(R.id.imageView);
                TextView textView = (TextView)rowView.findViewById(R.id.textView);
                imageView.setBackgroundResource(R.drawable.add_location);

                /*Resources res = getResources();
                String mDrawableName = city_list.get(position).toLowerCase();
                if(!for_add.get(city_list.get(position))){
                    int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
                    Drawable drawable = res.getDrawable(resID );
                    imageView.setImageDrawable(drawable );
                }
                else{
                    imageView.setBackgroundResource(R.drawable.add_location);
                }*/



                if(for_add.get(city_list.get(position))){
                    imageView.setVisibility(View.VISIBLE);
                    rowView.setTag("add");
                }
                else{
                    imageView.setVisibility(View.GONE);
                    rowView.setTag("view");
                }

                textView.setText(city_list.get(position));



		   /*
		        else if(itemsArrayList.get(position).getType().equals("text"))
	        {
		        if(!itemsArrayList.get(position).getMessage().equals(""))
		        {


		        	// 3. Get the two text view from the rowView
			        TextView labelView = (TextView) rowView.findViewById(R.id.meterlabel);


			        // 4. Set the text for textView
			        labelView.setText(Html.fromHtml(itemsArrayList.get(position).getMessage()));

		        }
	        }*/

            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            // 5. retrn rowView
            return rowView;
        }

        /*@Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view==null){
                view = getLayoutInflater().inflate(R.layout.add_local,viewGroup,false);
                holder = new ViewHolder();
                holder.bindView(view);
                view.setTag(holder);
            }
            else{
                holder = (ViewHolder) view.getTag();
            }

            if(city_list.get(i).isadd()){
                System.out.println("visible image");
                holder.imageView.setVisibility(View.VISIBLE);
            }
            else{
                holder.imageView.setVisibility(View.GONE);
            }

            holder.textView.setText(city_list.get(i).getName());


            return view;
        }
        */
    }

    /*class ViewHolder{
        ImageView imageView;
        TextView textView;

        void bindView(View view){
            imageView = (ImageView)view.findViewById(R.id.imageView);
            textView = (TextView)view.findViewById(R.id.textView);
        }
    }
    */
}
