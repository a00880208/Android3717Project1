package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.util.TypedValue;

/**
 * this class contains static functions that are useful anywhere.
 * this class should never be instantiated.
 *
 * Created by Eric on 2014-09-22.
 */
public class M {

    /**
     * private constructor, so it can never be instantiated.
     */
    private M() {}

    /**
     * when entering a number programatically to specify dimensions, it is
     * interpreted as pixels, which is bad. this function can be given the
     * desired dp, and it converts it into pixels.
     * @param dp
     * @return
     */
    public static int dp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                AppController.getInstance().getResources().getDisplayMetrics());
    }
}
