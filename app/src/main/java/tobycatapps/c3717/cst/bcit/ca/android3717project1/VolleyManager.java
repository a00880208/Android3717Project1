package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * contains functions used to interface with the Volley library.
 *
 * Created by Eric on 2014-09-18.
 */
public class VolleyManager {

//    /**
//     * length of Volley request queue where the frequency of adding an
//     *   expendable request degrades to MIN_EXPENDABLE_FREQUENCY.
//     */
//    private static final int MIN_EXPENDABLE_FREQUENCY_AT_REQUEST_QUEUE_LENGTH = 10;
//
//    /**
//     * number of expendable requests to enqueue per 10 requests
//     */
//    private static final int MIN_EXPENDABLE_FREQUENCY = 1;
//
    /** a Volley request queue that Volley must complete. */
    private static RequestQueue mRequestQueue;
//
//
//    ///////////////
//    // interface //
//    ///////////////
//    public static void addImportantRequest(Context c, Request request) {
//        getRequestQueue(c).add(request);
//    }
//
//    public static void addExpendableRequest(Context c, Request request) {
//
//    }
//
//
//    /////////////////////
//    // support methods //
//    /////////////////////
//    /**
//     * returns the singleton instance of Volley's RequestQueue.
//     * @return instance of Volley's RequestQueue
//     */
//    private static RequestQueue getRequestQueue(Context c) {
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(c);
//        }
//
//        return mRequestQueue;
//    }
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
