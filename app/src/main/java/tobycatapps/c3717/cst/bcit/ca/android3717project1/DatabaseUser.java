package tobycatapps.c3717.cst.bcit.ca.android3717project1;

/**
 * Database user is a java class that holds all the information found in a user document in
 * MongoDB. It's purpose is to simplify converting from a Java object to a JSON object by
 * ensuring that all data going into one JSON object is related.
 *
 * The class is a container class, so its methods are strictly accessors and mutators.
 */
public class DatabaseUser
{
    private String id;
    private String userHandle;
    private String pin;

    public void setId(String s)
    {
        id = s;
    }

    public String getId()
    {
        return id;
    }

    public void setUserHandle(String s)
    {
        userHandle = s;
    }

    public String getUserHandle()
    {
        return userHandle;
    }

    public void setPin(String s)
    {
        pin = s;
    }

    public String getPin()
    {
        return pin;
    }
}
