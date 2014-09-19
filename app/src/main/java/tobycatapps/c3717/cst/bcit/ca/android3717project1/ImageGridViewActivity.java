package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

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
    public static final String KEY_IMAGE_URIS =
            "tobycatapps.c3717.cst.bcit.ca.android3717project1.KEY_IMAGE_URIS";

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
        switch (id) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        ImageAdapter<Bitmap> imageAdapter = new ImageAdapter<Bitmap>(mContext);
        mImageGridView.setAdapter(imageAdapter);
        for (String imageURI : mImageURIs) {
            addImageToArrayAdapter(imageURI, imageAdapter);
        }

        // Add a click listener to the grid view
        mImageGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
            /*
             * when a cell in the grid view is tapped, it dispatches an intent
             * to start ImageSwipeViewActivity
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(mContext, ImageSwipeViewActivity.class);
                i.putExtra(ImageSwipeViewActivity.URL_LIST, mImageURIs);
                i.putExtra(ImageSwipeViewActivity.INDEX, position);
                startActivity(i);
            }
        });
    }




    // -------------------------------------------------------------------------
    // Support methods
    // -------------------------------------------------------------------------
    private void addImageToArrayAdapter(String uri,
            final ArrayAdapter<Bitmap> imageAdapter) {
        ImageRequest request =
            new ImageRequest(uri, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageAdapter.add(bitmap);
                }
            }, 0, 0, null,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {}
            });
        AppController.getInstance().getRequestQueue().add(request);
    }




    // -------------------------------------------------------------------------
    // Inner classes
    // -------------------------------------------------------------------------
    /** ArrayAdapter; populates ListViews and GridViews with ImageViews. */
    public class ImageAdapter<T extends Bitmap> extends ArrayAdapter<T> {

        private ImageAdapter(Context appContext) {
            super(appContext, android.R.layout.simple_list_item_1,
                    new ArrayList<T>());
        }

        /** Create a new ImageView for each item referenced by the Adapter */
        // TODO: make the ImageViews bigger or something.
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

            imageView.setImageBitmap(getItem(position));
            return imageView;
        }
    }
}
