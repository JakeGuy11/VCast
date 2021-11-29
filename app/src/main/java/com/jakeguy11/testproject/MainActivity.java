package com.jakeguy11.testproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.json.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity
{

    // Do all the initialization stuff
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("Your Channels");
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString((int) R.color.pri_blue))));
        }

        // For testing below here
        // Inject the dummy entry
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ((LinearLayout) findViewById(R.id.live_content_container)).addView(inflater.inflate(R.layout.entry, null));
        ((LinearLayout) findViewById(R.id.live_content_container)).addView(inflater.inflate(R.layout.entry, null));
        ((LinearLayout) findViewById(R.id.live_content_container)).addView(inflater.inflate(R.layout.entry, null));
        ((LinearLayout) findViewById(R.id.live_content_container)).addView(inflater.inflate(R.layout.entry, null));
        ((LinearLayout) findViewById(R.id.live_content_container)).addView(inflater.inflate(R.layout.entry, null));
        ((LinearLayout) findViewById(R.id.live_content_container)).addView(inflater.inflate(R.layout.entry, null));
        ((LinearLayout) findViewById(R.id.live_content_container)).addView(inflater.inflate(R.layout.entry, null));
        ((LinearLayout) findViewById(R.id.live_content_container)).addView(inflater.inflate(R.layout.entry, null));
        ((LinearLayout) findViewById(R.id.live_content_container)).addView(inflater.inflate(R.layout.entry, null));
    }

    // Check if a file exists
    public boolean fileExists(Context context, String fileName)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    // Read the JSON to a String
    private String getDataFromFS(Context context, String fileName)
    {
        try
        {
            // Get the file we need to read
            FileInputStream fileInStream = context.openFileInput(fileName);
            InputStreamReader inStream = new InputStreamReader(fileInStream);
            BufferedReader fileReader = new BufferedReader(inStream);

            // Add each line to a stringbuilder
            StringBuilder builder = new StringBuilder();
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) { builder.append(currentLine); }

            // Return the built string
            return builder.toString();
        }
        // If there are any errors, return null
        catch (IOException e) { return null; }
    }

    // Fetch the JSON from the filesystem
    public boolean saveDataToFS(Context context, String fileName, String content)
    {
        try
        {
            // Get the file
            FileOutputStream outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            // If the thing to print isn't null, write it
            if (content != null) { outStream.write(content.getBytes(StandardCharsets.UTF_8)); }
            return true;
        }
        // If there are any errors, return false
        catch (IOException e) { return false; }
    }
}