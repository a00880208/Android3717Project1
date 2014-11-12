package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * contains functions used to interface with the Volley library.
 *
 * Created by Eric on 2014-09-18.
 */
public class VolleyManager {

//    /**
//     * queue that holds prioritized Volley requests to be added to mRequestQueue
//     */
//    private static final PriorityBlockingQueue<Request> mPriorityRequestQueue =
//            new PriorityBlockingQueue<Request>();

    /** a Volley request queue that Volley must complete. */
    private static RequestQueue mRequestQueue;


    ///////////////
    // interface //
    ///////////////
//    public static void addImportantRequest(Context c, Request request) {
//        getRequestQueue(c).add(request);
//        request.
//    }
//
//    public static void addExpendableRequest(Context c, Request request) {
//
//    }


    /////////////////////
    // support methods //
    /////////////////////
    /**
     * returns the singleton instance of Volley's RequestQueue.
     *
     * @return instance of Volley's RequestQueue
     */
    public static RequestQueue getRequestQueue(Context c) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(c);
        }

        return mRequestQueue;
    }
}
