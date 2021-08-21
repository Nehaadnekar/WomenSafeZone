package com.neha.womensafezone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.Deque;

public class ChoiceActivity extends AppCompatActivity {
    Deque<Integer> integer= new ArrayDeque<>(3);
    ImageView imageViewVibrate, imageViewLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        final MediaPlayer mediaPlayer= MediaPlayer.create(this, R.raw.police);
        try{
            this.getSupportActionBar().hide();
        }catch(NullPointerException e){

        }

        imageViewVibrate= findViewById(R.id.imageViewVibrate);
        imageViewLocation= findViewById(R.id.imageViewLocation);


        imageViewVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();

            }
        });

        imageViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent map = new Intent(ChoiceActivity.this, LocationTracker.class);
                startActivity(map);
            }
        });

        BottomNavigationView bottomNavigationMenu= (BottomNavigationView) findViewById(R.id.navigation_bar);

        integer.push(R.id.help);

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.period:
                        Intent pic = new Intent(ChoiceActivity.this, PeriodTracker.class);
                        startActivity(pic);
                        break;

                    case R.id.help:
                        break;
                    case R.id.location:
                        Intent map = new Intent(ChoiceActivity.this, MapsActivity2.class);
                        startActivity(map);
                        break;
                    default:
                        return true;
                }

                return true;
            }


        });

    }

}