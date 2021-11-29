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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity
{

    // Some Strings we'll need
    private String configFilePath = "";
    private String remoteJsonUrl = "https://raw.githubusercontent.com/JakeGuy11/VCast/main/server_data/dummy_config.json";

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

        // Test out the getFileContentFromURL
        String res = getFileContentFromURL(remoteJsonUrl);
        System.out.println("Printing response:");
        System.out.println(res);

        // Check if the user has a config - if they don't, download it
        if (!fileExists(getApplicationContext(), configFilePath))
        {
            // Download the file

        }
    }

    public static String getFileContentFromURL(String providedUrl)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection urlConnection;
        String returnStr = "";
        try
        {
            // Open the URL, make the call
            URL conUrl = new URL(providedUrl);
            urlConnection = (HttpURLConnection) conUrl.openConnection();
            int callResponse = urlConnection.getResponseCode();

            if (callResponse == 200)
            {
                // Everything went well, get the response
                InputStream responseStream = new BufferedInputStream(urlConnection.getInputStream());
                if (responseStream != null)
                {
                    // There's actually something in the response
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseStream));

                    // Iterate through the lines of the response, appending them to the string to return
                    String currentLine = "";
                    while ((currentLine = responseReader.readLine()) != null) returnStr += currentLine;
                }
                responseStream.close();
                return returnStr;
            }
        }
        catch (Exception e) { System.out.println(e); }
        return null;
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