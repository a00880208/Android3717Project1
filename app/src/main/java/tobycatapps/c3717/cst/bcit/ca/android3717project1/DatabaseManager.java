package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * The DatabaseManager class contains many convenience functions for making use of MongoLabs RESTful
 * API. Most of these functions are helper functions used to construct URLs, which will be used in
 * calls to the REST API.
 */
public class DatabaseManager

{
    //Returns the name of our database
    public String getDatabaseName()
    {
        return "petbitsDB";
    }

    //returns the API key of our database
    public String getApiKey()
    {
        return "vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx";
    }

    //a helper function to help build our URL for REST API calls
    public String docApiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }

    //a helper function to help build the base URL for REST API calls
    public String getBaseUrl()
    {
        return "https://api.mongolab.com/api/1/databases" + getDatabaseName() + "/collections";
    }

    //a helper function to help build the base URL for REST API calls on the user collection
    public String userRequest()
    {
        return "/user";
    }

    //a helper function to help build the base URL for REST API calls on the image collection
    public String imageRequest()
    {
        return "/image";
    }

    //returns a URL that has the REST API return all collections in the petBits database
    public String getAllCollectionsURL()
    {
        return getBaseUrl()+docApiKeyUrl();
    }

    //returns a URL that has the REST API return all user documents in the user collection
    public String getAllUserDocumentsURL()
    {
        return getBaseUrl()+userRequest()+docApiKeyUrl();
    }

    //returns a URL that has the REST API return all image documents in the image collection
    public String getAllImageDocumentsURL()
    {
        return getBaseUrl()+imageRequest()+docApiKeyUrl();
    }

    //converts a Java DatabaseUser object into JSON data (String)
    public String createUser(DatabaseUser user)
    {
         return String.format(
                 "{\"user\" : " +
                         "{\"userHandle\": \"%s\", " + "\"pin\": \"%s\"}",
                            user.getUserHandle(), user.getPin());
    }

    //adds a user to the Database

}
