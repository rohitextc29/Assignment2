package com.example.macbookpro.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Animation animFadein;
    ImageView img;
    Button go_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.img);
        go_next = (Button) findViewById(R.id.go_next);

        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        img.setAnimation(animFadein);

        go_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        animFadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                go_next.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                go_next.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
