package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Alex on 11/12/2014.
 */
public class dbAccess {

    public static void newUser(Context context,
            String username, String password,
            final Response.Listener<Boolean> userOnResponse)
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
                        userOnResponse.onResponse(true);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERIC failed to create user", error.toString()+":"+error.getMessage());
                        userOnResponse.onResponse(false);
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

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, url, payload,
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

        String url = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/image?apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx&q={\"imgurid\":\""+imageIdF+"\"}";

        JSONArray payload;
        try {
            payload = new JSONArray("[]");
        } catch (Exception e) {
            payload = null;
        }

        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.PUT, url, payload,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("deletion successful", response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("deletion failed", error.toString());
                    }
                });

        VolleyManager.addRequest(context, request);
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

    private static class MyJsonObjectRequest extends JsonRequest<JSONObject> {

        public MyJsonObjectRequest(int method, String url, JSONArray jsonPayload,
                Response.Listener<JSONObject> response,
                Response.ErrorListener errorListener) {

            super(method, url,
                    (jsonPayload == null) ? null : jsonPayload.toString(),
                    response, errorListener);

        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString =
                        new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(new JSONObject(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }
    }
}
