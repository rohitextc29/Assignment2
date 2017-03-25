package com.example.macbookpro.assignment2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;

/**
 * Created by rohityadav on 2/12/17.
 */

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button submit;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        submit = (Button)findViewById(R.id.submit);

        getSupportActionBar().setTitle("City Bazaar");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate authenticate_asycn = new authenticate();
                authenticate_asycn.execute(username.getText().toString().trim(),password.getText().toString().trim());
            }
        });
    }

    class authenticate extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            String username_txt = strings[0];
            String password_txt = strings[1];

            if(username_txt.equals("user123@example.com") && password_txt.equals("user@1234")){
                return "yes";
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new SpotsDialog(LoginActivity.this, R.style.custom_loading);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s!=null){
                Toast.makeText(LoginActivity.this,"Welcome user123",Toast.LENGTH_LONG).show();
                dialog.dismiss();
                Intent intent = new Intent(LoginActivity.this,CityListActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this,"Please enter correct credentials.",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

            super.onPostExecute(s);
        }
    }

}
