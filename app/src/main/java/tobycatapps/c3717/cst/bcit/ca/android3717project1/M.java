package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.content.Context;
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
     * when entering a number programmatically to specify dimensions, it is
     * interpreted as pixels, which is bad. this function can be given the
     * desired dp, and it converts it into pixels.
     * @param c application context
     * @param dp amount of density-independent pixels to be converted to pixels
     * @return number of pixels needed to match the specified
     *   density-independent pixels
     */
    public static int dp(Context c, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                c.getResources().getDisplayMetrics());
    }
}
