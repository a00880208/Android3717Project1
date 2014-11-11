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
     * indicates an image size
     */
    public enum ImageSize {
        NORMAL(""),
        SMALL_SQR("s"),
        BIG_SQR("b"),
        SMALL_THUMB("t"),
        MEDIUM_THUMB("m"),
        LARGE_THUMB("l"),
        HUGE_THUMB("h");

        private final String imageSuffix;

        ImageSize(String imageSuffix) {
            this.imageSuffix = imageSuffix;
        }

        public String getUriSuffix() {
            return imageSuffix;
        }
    }

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

    /**
     * takes a uri to an image on imgur.com, and returns the uri to the
     * thumbnail of the image on imgur.com
     *
     * @param uri uri to an image on imgur.com to convert into the image's
     *            thumbnail uri
     * @return    uri to the thumbnail version of image at the passed uri
     */
    public static String toThumbnail(String uri, ImageSize thumbSize) {
        int start = uri.indexOf("i.imgur.com");
        int end = start + "i.imgur.com/XXXXXXX".length();
        String imageUrl = uri.substring(start, end);
        return "http://" + imageUrl + thumbSize.getUriSuffix() + ".jpg";
    }
}
