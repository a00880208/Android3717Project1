package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Eric on 2014-09-18.
 */
public class AppController extends Application {

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(this);
        Log.e(toString(), "AppController started");
    }

    /**
     * returns the singleton instance of the AppController
     * @return instance of the AppController
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * returns the singleton instance of Volley's RequestQueue.
     * @return instance of Volley's RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mInstance);
        }

        return mRequestQueue;
    }
}
