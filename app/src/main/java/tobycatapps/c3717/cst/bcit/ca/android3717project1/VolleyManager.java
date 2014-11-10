package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Eric on 2014-09-18.
 */
public class VolleyManager {

    private static RequestQueue mRequestQueue;

    /**
     * returns the singleton instance of Volley's RequestQueue.
     * @return instance of Volley's RequestQueue
     */
    public static RequestQueue getRequestQueue(Context c) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(c);
        }

        return mRequestQueue;
    }
}
