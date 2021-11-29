package com.jakeguy11.VCast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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

        String[][] members = {{"Polka", "https://vignette.wikia.nocookie.net/virtualyoutuber/images/3/33/Omaru_Polka_Portrait.png/revision/latest?cb=20200807015145"}, {"Towa", "https://static.wikia.nocookie.net/virtualyoutuber/images/3/35/Tokoyami_Towa_-_Portrait.png/revision/latest?cb=20200126194247"}, {"Suisei", "https://static.wikia.nocookie.net/virtualyoutuber/images/8/8b/Hoshimachi_Suisei_2019_Portrait.png/revision/latest?cb=20191205132210"}, {"Mori", "https://static.wikia.nocookie.net/virtualyoutuber/images/3/39/Mori_Calliope_Portrait.png/revision/latest?cb=20200910193007"}};

        for(String[] currentMem : members)
        {
            // Create the view, edit the properties
            android.view.View viewToAdd = inflater.inflate(R.layout.entry, null);
            ((ImageView) viewToAdd.findViewById(R.id.sample_image)).setImageDrawable(loadImageFromWeb(currentMem[1]));
            ((TextView) viewToAdd.findViewById(R.id.sample_text)).setText(currentMem[0]);

            // Add the view
            ((LinearLayout) findViewById(R.id.live_content_container)).addView(viewToAdd);
        }
    }

    // Turn a URL into a drawable image
    public static Drawable loadImageFromWeb(String url)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try
        {
            InputStream loadStream = (InputStream) new URL(url).getContent();
            System.out.println("Loaded image!");
            return Drawable.createFromStream(loadStream, null);
        }
        catch (Exception e) { System.out.println("Image not loaded: " + e); return null; }
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