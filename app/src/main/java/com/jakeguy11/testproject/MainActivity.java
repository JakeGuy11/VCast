package com.jakeguy11.testproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Do all the initialization stuff
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("VCast");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString((int) R.color.pri_blue))));
    }
}