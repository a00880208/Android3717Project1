package tobycatapps.c3717.cst.bcit.ca.android3717project1;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Eric & Alex on 2014-09-18.
 */
public class VolleyManager {

    private final Context mContext;
    private final RequestQueue mRequestQueue;

    /**
     * make sure we only instantiate the volleyManager once! and pass it around
     */
    public VolleyManager(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }
}


