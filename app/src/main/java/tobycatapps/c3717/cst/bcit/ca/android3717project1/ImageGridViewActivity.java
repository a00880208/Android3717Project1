package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ImageGridViewActivity extends Activity {




    // references to GUI views
    /** reference to grid view that contains all the images */
    private static GridView mImageGridView;


    // starting intent keys and values
    /**
     * key in starting intent to value that holds an array of URIs of images to
     * display
     */
    public static final String KEY_IMAGE_URIS = "KEY_IMAGE_URIS";

    /** array of image URIs extracted from the starting intent */
    private String[] mImageURIs;


    // Uncategorized
    /** application context */
    private Context mContext;




    // -------------------------------------------------------------------------
    // Activity lifecycle callbacks
    // -------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid_view);  // set screen layout
        initializeGUIReferences();

        // initialize variables
        mContext = getApplicationContext();

        // extract information from starting intent
        Intent i = getIntent();
        mImageURIs = i.getStringArrayExtra(KEY_IMAGE_URIS);

        // Set up GUI
        configureGUIWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_grid_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    // -------------------------------------------------------------------------
    // Support methods
    // -------------------------------------------------------------------------
    /**
     * initializes all GUI references of this activity; initializes all
     * GUI references with corresponding views in the layout.
     */
    private void initializeGUIReferences() {
        mImageGridView = (GridView) findViewById(R.id.grid_view_images);
    }

    /**
     * Sets up the GUI...all the gross things that need to be done to and with
     * GUI references go here.
     */
    private void configureGUIWidgets() {

        // Add images to grid view (mImageGridView)
        ImageAdapter imageAdapter = new ImageAdapter(mContext);
        mImageGridView.setAdapter(imageAdapter);
        for (String imageURI : mImageURIs) {
            ApplicationHandler.enqueueGetImageAtURITask(imageURI, imageAdapter);
        }
    }




    // -------------------------------------------------------------------------
    // Inner classes
    // -------------------------------------------------------------------------
    public class ImageAdapter extends ArrayAdapter {

        private ImageAdapter(Context appContext) {
            super(appContext, android.R.layout.simple_list_item_1,
                    new ArrayList<Bitmap>());
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageBitmap((Bitmap) getItem(position));
            return imageView;
        }
    }

    public static class getImageAtURITask implements Runnable,
            ApplicationHandler.UpdateUITask {

        /** URL of the image that's on the internet download and decode. */
        private String mImageURI;

        /** Reference to decoded image hosted at mImageURI */
        private Bitmap mDecodedBitmap = null;

        /**
         * Reference to imageAdapter to add bitmap to once image hosted at
         * mImageURI is decoded
         */
        private ImageAdapter mImageAdapter;

        /**
         * Instantiates a getImageAtURITask, and dispatches it to the
         * ApplicationHandler for decoding, and things.
         * @param imageURI
         */
        public getImageAtURITask(String imageURI, ImageAdapter imageAdapter) {
            mImageURI = imageURI;
            mImageAdapter = imageAdapter;
        }

        /**
         * Gets the bitmap from the internet, decodes it, and sends a message to
         * ApplicationHandler to execute the method that needs to be on the UI
         * thread.
         */
        @Override
        public void run() {

            // Attempt to download and decode the bitmap...once image is
            // decoded, send another message to ApplicationHandler to execute
            // our updateUI method to add the bitmap to the UI. If this fails,
            // log the exception.
            try {
                URL url = new URL(mImageURI);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                mDecodedBitmap = bitmap;

                // Send message to UI thread to add bitmap to UI
                Message msg = ApplicationHandler.mHandler.obtainMessage(
                        ApplicationHandler.UPDATE_UI_TASK,  // Message.what
                        this                                // Message.obj
                );
                msg.sendToTarget();
            } catch (IOException e) {
                Log.e(null, "getImageAtURITask: Error downloading and decoding"
                        + " bitmap:");
                Log.e(null, e.toString());
            }
        }

        /**
         * Puts the decoded bitmap onto the UI; this method must be run on the
         * UI thread.
         */
        @Override
        public void updateUI() {
            mImageAdapter.add(mDecodedBitmap);
        }
    }
}
