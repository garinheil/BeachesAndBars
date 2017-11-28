package heil1gd.cps496.cmich.edu.finalproject;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by heilg on 11/20/2017.
 * <p>
 * Description: This class will be used to write to and read from a file in user storage.
 */

public class ReadWrite {
    public void writeToFile(String favType, ArrayList<Favorites> arr, Context context) {
        try {
            if(favType.equals("beach")) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("beachFavs.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(arr.toString());
                outputStreamWriter.close();
            } else if(favType.equals("bar")) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("barFavs.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(arr.toString());
                outputStreamWriter.close();
            }
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFile(String favType, Context context) {

        String ret = "";

        try {

            if(favType.equals("beach")) {
                InputStream inputStream = context.openFileInput("beachFavs.txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ( (receiveString = bufferedReader.readLine()) != null ) {
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            } else if(favType.equals("bar")) {
                InputStream inputStream = context.openFileInput("barFavs.txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ( (receiveString = bufferedReader.readLine()) != null ) {
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("file read", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("file read", "Can not read file: " + e.toString());
        }

        return ret;
    }
}

