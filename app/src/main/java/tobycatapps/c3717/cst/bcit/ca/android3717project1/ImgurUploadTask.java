package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Alex on 11/10/2014.
 */
public abstract class ImgurUploadTask extends AsyncTask<Void, Void, String>
{

    private final String CLIENT_ID = "ab198a97e0e5954";
    private static final String imgurUploadUrl = "https://api.imgur.com/3/";

    private Uri imageUri;
    private Activity activity;

    public ImgurUploadTask()
    {
    }

    public ImgurUploadTask(Uri imageUri, Activity a)
    {
        Log.d("ALEX", "ImageUploadTask");
        this.imageUri = imageUri;
        this.activity = a;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        Log.d("ALEX", "do in background");
        InputStream imageIn = null;
        InputStream responseIn = null;
        OutputStream imageOut;
        HttpURLConnection connectToImgur = null;

        //Open stream to image
        try
        {
            imageIn = activity.getContentResolver().openInputStream(imageUri);
        }
        catch (FileNotFoundException e)
        {
            Log.d("ALEX ERROR", "COULD NOT OPEN STREAM Line 58");
            e.printStackTrace();
        }

        //Open http connection
        try
        {
            connectToImgur = (HttpURLConnection) new URL(imgurUploadUrl).openConnection();
            connectToImgur.setDoOutput(true);
            //pass stuff into the connection
            connectToImgur.setRequestProperty("Authorization", "Client-ID " + CLIENT_ID);
            //Get imgur return
            imageOut = connectToImgur.getOutputStream();

            //Convert imgur return ( outstream) to instream
            putInputToOutput(imageIn, imageOut);

            //clean up outputstream
            imageOut.flush();
            imageOut.close();

            if(connectToImgur.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                responseIn = connectToImgur.getInputStream();
                return (parseData(responseIn));
            }
            else
            {
                Log.d("ALEX ERROR", "HTTP URL CONNECTION SHIT THE BED");
                Log.d("ALEX ERROR", connectToImgur.getResponseMessage());
            }
        }catch(MalformedURLException e)
        {
            Log.d("ALEX ERROR", "MALFORMED URL Line 89");
            e.printStackTrace();
        }catch(IOException e)
        {
            Log.d("ALEX ERROR", "IO EXCEPTION Line 93");
            e.printStackTrace();
        }catch (Exception e)
        {
            Log.d("ALEX ERROR", "EXCEPTION Line 97");
            e.printStackTrace();
        }

        return null;
    }

    private static void putInputToOutput(InputStream i, OutputStream o) throws IOException
    {
        Log.d("ALEX", "converting image to output Line 115");
        byte[] buffer = new byte[8192];
        int n;

        while(-1 != (n=i.read(buffer)))
        {
            o.write(buffer,0,n);
        }
    }

    private String parseData(InputStream is) throws Exception
    {
        Log.d("ALEX", "parse returned data");
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(is);
        while (scanner.hasNext()) {
            sb.append(scanner.next());
        }

        JSONObject imgurReturn = new JSONObject(sb.toString());
        String id = imgurReturn.getJSONObject("data").getString("id");
        String deletehash = imgurReturn.getJSONObject("data").getString("deletehash");

        ImgurData imgurD = new ImgurData();
        imgurD.id = id;
        imgurD.deleteHash = deletehash;

        return imgurD.id;
    }
}


