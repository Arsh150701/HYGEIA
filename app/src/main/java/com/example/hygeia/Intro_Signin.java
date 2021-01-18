package com.example.hygeia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class Intro_Signin extends AppCompatActivity {
    ViewPager viewPager;
    WormDotsIndicator dot;
    ViewAdapter viewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro__signin);
        viewPager= findViewById(R.id.view_pager);
        dot= findViewById(R.id.dot);
        viewAdapter= new ViewAdapter(Intro_Signin.this);
        viewPager.setAdapter(viewAdapter);
        dot.setViewPager(viewPager);
    }

}
