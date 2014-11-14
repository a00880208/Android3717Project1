package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * contains functions used to interface with the Volley library.
 *
 * Created by Eric on 2014-09-18.
 */
public class VolleyManager {

    /** a Volley request queue that Volley must complete. */
    private static RequestQueue mRequestQueue;

    /**
     * returns the singleton instance of Volley's RequestQueue.
     *
     * @param c application context
     *
     * @return instance of Volley's RequestQueue
     */
    public static RequestQueue getRequestQueue(Context c) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(c);
        }

        return mRequestQueue;
    }

    /**
     * returns the singleton instance of Volley's RequestQueue.
     *
     * @param c application context
     * @param request request to enqueue into the VolleyRequestQueue
     */
    public static void addRequest(Context c, Request<?> request) {
        getRequestQueue(c).add(request);
    }

}
