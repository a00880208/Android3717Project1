package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import java.util.ArrayList;

/**
 * DatabaseImage is a java class that holds all the information found in a image document in
 * MongoDB. It's purpose is to simplify converting from a Java object to a JSON object by
 * ensuring that all data going into one JSON object is related.
 *
 * DatabaseImage is a container class, so its methods are strictly accessors and mutators.
 */
public class DatabaseImage
{
    private ArrayList<String> tags;
    private String uploaderName;
    private String imageUrl;

    public void addTag(String s)
    {
        tags.add(s);
    }

    public boolean checkTag(String s)
    {
        if (tags.contains(s))
        {
            return true;
        }
        return false;
    }

    public void setUploaderName (String s)
    {
        uploaderName = s;
    }

    public String getUploaderName()
    {
        return uploaderName;
    }

    public void setImageUrl(String s)
    {
        imageUrl = s;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }
}
