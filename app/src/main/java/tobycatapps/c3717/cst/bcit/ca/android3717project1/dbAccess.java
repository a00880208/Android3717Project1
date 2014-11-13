package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 11/12/2014.
 */
public class dbAccess {

    public static void newUser(String username, String password, Context context)
    {
        final String usernameF = username;
        final String passwordF = password;
        String url = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/user?apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx";

        StringRequest sr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ERIC user created", response);
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERIC", error + "");
                        Log.e("ERIC failed to create user", error.getMessage());
                    }
                }) {
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("userHandle", usernameF);
                params.put("pin", passwordF);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        VolleyManager.getRequestQueue(context).add(sr);
    }


    public void uploadImage(String imageId, String deleteHash, Context context)
    {

        final String imageIdF = imageId;
        final String deleteHashF = deleteHash;

        String url = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/images?apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx/";

        StringRequest sr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ERIC user created", response);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("ERIC failed to create user", error.getMessage());
            }
        }) {
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("imgurid", imageIdF);
                params.put("_deleteHash", deleteHashF);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return null;
            }
        };
        VolleyManager.getRequestQueue(context).add(sr);

    }

    public static void deleteImage(String imageId, Context c)
    {
        final String imageIdF = imageId;
        final Context context = c;

        String urlp1 = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/image?q={\"imgurid\":\"";
        String urlp2 = "\"}&apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx";
        String url = urlp1 + imageIdF + urlp2;

        JsonArrayRequest request = new JsonArrayRequest(
                url,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        for(int i = 0; i < jsonArray.length(); ++i) {
                            try {
                                JSONObject passData = jsonArray.getJSONObject(i);
                                String deleteHash = passData.getString("_deleteHash");
                                deleteImageFromImgur(imageIdF, deleteHash);
                            } catch (JSONException e) {
                                throw new RuntimeException(e.toString());
                            }
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        String errorStr = "";

                        if (volleyError.networkResponse != null) {
                            switch (volleyError.networkResponse.statusCode) {

                                case 404:
                                    errorStr = (context.getString(R.string.not_found));
                                    break;

                                case 400:
                                case 500:
                                    errorStr =(context.getString(R.string.server_error));
                                    break;

                                default:
                                    errorStr =(context.getString(R.string.unknown_error));
                                    break;
                            }
                        } else {
                            errorStr = (context.getString(R.string.no_internet));
                            Log.d("ERROR", errorStr);
                        }
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
}
