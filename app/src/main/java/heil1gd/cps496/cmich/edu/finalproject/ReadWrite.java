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
    // Determines the favorite type and sends the data to be written
    public void writeToFile(String type, String data, Context context) {
        if(type.equals("beach")) {
            Log.d("In writeToFile", type);
            performWrite(type, data, context);
        } else if(type.equals("bar")) {
            Log.d("In writeToFile", type);
            performWrite(type, data, context);
        }
    }

    // Determines the favorite type and reads from the proper file
    public String readFromFile(String type, Context context) {
        String returnString = "";
        if(type.equals("beach")) {
            Log.d("In readFromFile", type);
            returnString = performRead(type, context);
        } else if(type.equals("bar")) {
            Log.d("In readFromFile", type);
            returnString = performRead(type, context);
        }

        return returnString;
    }

    // Performs the actual writing to file, given a favorite type and the data as well as context
    private void performWrite(String type, String data, Context context) {
        try {
            Log.d("Writing to file", type + "Favs.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(type + "Favs.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    // Performs the actual file reading from the proper file, given a favorite type a context
    private String performRead(String type, Context context) {
        String returnString = "";

        try {
            InputStream inputStream = context.openFileInput(type + "Favs.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                try {
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                returnString = stringBuilder.toString();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        return returnString;
    }
}

