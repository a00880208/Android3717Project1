package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.activity.ImageGridViewActivity;

/**
 * Created by Alex on 11/12/2014.
 */
public class dbAccess {

    public static void newUser(String username, String password, Context context)
    {
        final String usernameF = username;
        final String passwordF = password;

        String url = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/user?apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx";

        JSONObject payload;
        try {
            payload = new JSONObject("{\"userHandle\":\""+usernameF+"\",\"pin\":\""+passwordF+"\"}");
        } catch (Exception e) {
            payload = null;
        }

        JsonObjectRequest sr = new JsonObjectRequest(url, payload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ERIC user created", response.toString());
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("ERIC failed to create user", error.toString()+":"+error.getMessage());
            }
        });
        VolleyManager.getRequestQueue(context).add(sr);
    }


    public static void uploadImage(String uploader, String imageId, Context context)
    {

        final String imageIdF = imageId;

        String url = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/image?apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx";

        JSONObject payload;
        try {
            payload = new JSONObject(
                    "{\"imgurid\":\""+imageIdF+"\"," +
                            "\"URL\":\"http://i.imgur.com/"+imageId+".jpg\"," +
                            "\"Uploader\":\""+uploader+"\"}");
        } catch (Exception e) {
            payload = null;
        }

        JsonObjectRequest sr = new JsonObjectRequest(url, payload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ERIC image uploaded", response.toString());
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("ERIC failed to upload image", error.toString()+":"+error.getMessage());
            }
        });
        VolleyManager.getRequestQueue(context).add(sr);

    }

    public static void deleteImage(String imageId, Context c)
    {
        final String imageIdF = imageId;
        final Context context = c;

        String urlp1 = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/image?q={\"imgurid\":\"";
        String urlp2 = "\"}&apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx";
        String url = urlp1 + imageIdF + urlp2;

        JSONObject payload;
        try {
            payload = new JSONObject(
                    "{\"imgurid\":\""+imageIdF+"\"," +
                            "\"URL\":\"http://i.imgur.com/"+imageId+".jpg\"," +
                            "\"Uploader\":\""+"\"}");
        } catch (Exception e) {
            payload = null;
        }

        JsonObjectRequest request = new JsonObjectRequest(url, payload,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("", response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("", error.toString());
                    }
                });

        VolleyManager.getRequestQueue(context).add(request);
    }

    public static void deleteImageFromImgur(String imageId, String deleteHash)
    {
        final String CLIENT_ID = "ab198a97e0e5954";
        try
        {
            URL url = new URL("https://api.imgur.com/3/image/"+imageId);
            HttpURLConnection connectToImgur = (HttpURLConnection) url.openConnection();
            connectToImgur.setDoOutput(true);
            connectToImgur.setRequestProperty("Authorization", "Client-ID " + CLIENT_ID);
            connectToImgur.setRequestProperty(
                    "Content-Type", "application/x-www-form-urlencoded" );
            connectToImgur.setRequestMethod("DELETE");
            connectToImgur.connect();
        }catch(MalformedURLException e) {
            Log.d("ALEX ERROR", "MALFORMED URL Line 89");
            e.printStackTrace();
        }catch(IOException e)
        {
            Log.d("ALEX ERROR", "MALFORMED URL Line 89");
            e.printStackTrace();
        }
    }

    public static void getUserImages(Context c, String uploaderName,
                                     final Response.Listener<ArrayList<String>> userResponseListener,
                                     Response.ErrorListener userErrorListener) {

        // construct query
        StringBuilder query = new StringBuilder();
        query.append("{");
        if (!uploaderName.isEmpty()) {
            query.append("\"Uploader\":\"");
            query.append(uploaderName);
        }
        query.append("\"}");
        String url = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/image?apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx&"
                + "q=" + query.toString();

        // execute query and start the gridview activity
        JsonArrayRequest request = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray data) {
                        ArrayList<String> imageUris = new ArrayList<String>();
                        for (int i = 0; i < data.length(); ++i) {
                            try {
                                JSONObject imageData = data.getJSONObject(i);
                                imageUris.add(imageData.getString("URL"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e.toString());
                            }
                        }
                        userResponseListener.onResponse(imageUris);
                    }
                },

                userErrorListener);

        VolleyManager.getRequestQueue(c).add(request);
    }

}
