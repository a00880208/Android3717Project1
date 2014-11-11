package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.ArrayList;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.VolleyManager;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.M;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;


public class ImageGridViewActivity extends Activity {


    //////////////////////////////////////
    // starting intent keys & variables //
    //////////////////////////////////////
    /** starting intent key to String[] of URIs of images to display */
    public static final String KEY_IMAGE_URIS =
            "tobycatapps.c3717.cst.bcit.ca.android3717project1.KEY_IMAGE_URIS";

    /** array of image URIs extracted from the starting intent */
    private String[] mImageURLs;


    ////////////////////
    // GUI references //
    ////////////////////
    /** reference to grid view that contains all the images */
    private static GridView mImageGridView;


    /////////////////////////////////////
    // class constants & instance data //
    /////////////////////////////////////
    /**
     * image shown to act as a placeholder while the real images are being
     * downloaded and decoded from the internet
     */
    private static final int R_ID_LOADING_IMAGE = R.drawable.ic_launcher;

    /**
     * image shown to act as a placeholder if the real image fails to be
     * downloaded and decoded from the internet
     */
    private static final int R_ID_ERROR_IMAGE = R.drawable.ic_launcher;

    /** application context */
    private Context mContext;


    //////////////////////////
    // life cycle callbacks //
    //////////////////////////
    /**
     * invoked when the Activity is being created.
     *
     * @param savedInstanceState contains data that is saved during the
     *   onSaveInstanceState system callback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid_view);

        mContext = getApplicationContext();

        mImageURLs = getIntent().getStringArrayExtra(KEY_IMAGE_URIS);

        initializeGUIReferences();
        configureGUIWidgets();
    }


    //////////////////////
    // system callbacks //
    //////////////////////
    /**
     * invoked by the android system when a Menu needs to be inflated (i.e.:
     *   when someone clicks on the overflow icon)
     *
     * @param menu reference to the one options menu for this Activity
     *
     * @return true if the callback was handled properly; false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_grid_view, menu);
        return true;
    }

    /**
     * invoked by the system when an OptionsMenu MenuItem is clicked.
     *
     * @param item reference to the MenuItem that was clicked
     *
     * @return true if the callback was properly handled; false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /////////////////////
    // support methods //
    /////////////////////
    /**
     * initializes all GUI references of this activity; initializes all
     * GUI references with corresponding views in the layout.
     */
    private void initializeGUIReferences() {
        mImageGridView = (GridView) findViewById(R.id.grid_view_images);
    }

    /**
     * Sets up the GUI; one time things that are needed to make the GUI behave
     *   as it should go here (i.e.: setting up GridViews and ListViews with
     *   their Adapters)
     */
    private void configureGUIWidgets() {

        // instantiate and set an adapter for mImageGridView
        ArrayListAdapter<Bitmap> arrayListAdapter =
                new ArrayListAdapter<Bitmap>(mContext) {

                    /**
                     * returns the View that is to be displayed in the GridView
                     *   or ListView
                     *
                     * @param position index of the item in our ArrayList
                     * @param convertView View that's being reused; may possibly
                     *   be null if there is no View object to reuse
                     * @param parent ViewGroup that returned View will
                     *   eventually be attached to
                     *
                     * @return a View that is to be displayed in the GridView or
                     *   ListView
                     */
                    @Override
                    public View getView(int position, View convertView,
                            ViewGroup parent) {

                        ImageView imageView;

                        if (convertView == null) {

                            // we're creating a new view from scratch;
                            // instantiate and initialize what's necessary to
                            // create a layout for our image for the GirdView
                            int length = mImageGridView.getColumnWidth();

                            imageView = new ImageView(mContext);
                            imageView.setLayoutParams(new GridView.LayoutParams(
                                    length, length));
                            imageView.setScaleType(
                                    ImageView.ScaleType.CENTER_CROP);

                        } else {

                            // we're using an old view
                            imageView = (ImageView) convertView;

                        }

                        // put the image on the tile...
                        imageView.setImageBitmap(getItem(position));
                        return imageView;
                    }
                };
        mImageGridView.setAdapter(arrayListAdapter);

        // add images to grid view (mImageGridView)
        Bitmap loadImage = BitmapFactory.decodeResource(
                getResources(), R_ID_LOADING_IMAGE);
        Bitmap errorImage = BitmapFactory.decodeResource(
                getResources(), R_ID_ERROR_IMAGE);
        for (String imageURL : mImageURLs) {
            imageURL = M.toThumbnail(imageURL, M.ImageSize.BIG_SQR);
            addImageToArrayAdapter(imageURL, loadImage, errorImage, arrayListAdapter);
        }

        // add a click listener to the grid view
        mImageGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

            /**
             * when a cell in the AdapterView is tapped, it dispatches an intent
             *   to start ImageSwipeViewActivity
             *
             * @param parent the AdapterView that holds the view that was tapped
             * @param view reference to the view that was clicked
             * @param position index of item that was clicked in the underlying
             *   array if this is an ArrayAdapter
             * @param id id of the item that was clicked in the underlying
             *   cursor if this is a CursorAdapter
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(mContext, ImageSwipeViewActivity.class);
                i.putExtra(ImageSwipeViewActivity.URL_LIST, mImageURLs);
                i.putExtra(ImageSwipeViewActivity.INDEX, position);
                startActivity(i);
            }
        });
    }

    /**
     * first adds a placeholder image into the passed adapter, then fetches the
     *   image at the URI over the internet. once the image has been loaded, and
     *   decoded, replace the placeholder image in the passed adapter with it,
     *   and invoke notifyDataSetChanged().
     *
     * @param imageUri URI to image on the internet.
     * @param loadingBitmap image shown while the image at imageUri is being
     *   downloaded and decoded.
     * @param errorBitmap image shown if Volley fails to download and decode the
     *   image at imageUri.
     * @param arrayListAdapter adapter that holds the images.
     */
    private void addImageToArrayAdapter(final String imageUri,
            final Bitmap loadingBitmap, final Bitmap errorBitmap,
            final ArrayListAdapter<Bitmap> arrayListAdapter) {

        // adds a placeholder image into the grid view until the real image
        // can be downloaded and decoded
        final ArrayList<Bitmap> adapterArrayList = arrayListAdapter.getArrayList();
        final int imageIndex = adapterArrayList.size();
        adapterArrayList.add(loadingBitmap);

        // get the image from the Internet; display it when it's ready
        ImageRequest request =
            new ImageRequest(imageUri, new Response.Listener<Bitmap>() {

                @Override
                public void onResponse(Bitmap bitmap) {
                    adapterArrayList.set(imageIndex, bitmap);
                    arrayListAdapter.notifyDataSetChanged();
                }
            }, 0, 0, null, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    adapterArrayList.set(imageIndex, errorBitmap);
                    arrayListAdapter.notifyDataSetChanged();
                    Log.e("ImageRequest.onErrorResponse",
                            volleyError.getMessage());
                }
            });
        VolleyManager.getRequestQueue(mContext).add(request);
    }
}
