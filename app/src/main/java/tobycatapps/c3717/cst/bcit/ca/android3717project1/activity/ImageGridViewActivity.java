package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.ArrayList;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.M;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.VolleyManager;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;


public class ImageGridViewActivity extends Activity {


    //////////////////////////////////////
    // starting intent keys & variables //
    //////////////////////////////////////
    /** starting intent key to String[] of URIs of images to display */
    public static final String KEY_IMAGE_URIS =
            "tobycatapps.c3717.cst.bcit.ca.android3717project1.KEY_IMAGE_URIS";

    /** array of image URIs extracted from the starting intent */
    protected ArrayList<String> mImageURLs;


    ////////////////////
    // GUI references //
    ////////////////////
    /** reference to grid view that contains all the images */
    protected static GridView mImageGridView;


    /////////////////////
    // class constants //
    /////////////////////
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


    ///////////////////
    // instance data //
    ///////////////////
    /** application context */
    private Context mContext;

    /** used as a placeholder while real images are downloaded and decoded */
    private Bitmap mLoadingImage;

    /** used as a placeholder when real image fails to download and decode */
    private Bitmap mErrorImage;


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

        parseStartingIntent();
        initializeInstanceData();
        initializeGUIReferences();
        configureGUIComponents();
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
     * parses the activity's starting intent into corresponding instance fields.
     */
    private void parseStartingIntent() {
        mImageURLs = getIntent().getStringArrayListExtra(KEY_IMAGE_URIS);
    }

    /**
     * initializes all instance data of this activity.
     */
    private void initializeInstanceData() {
        mContext = this;

        Resources r = getResources();

        mLoadingImage = BitmapFactory.decodeResource(r, R_ID_LOADING_IMAGE);
        mErrorImage = BitmapFactory.decodeResource(r, R_ID_ERROR_IMAGE);
    }


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
    private void configureGUIComponents() {

        // instantiate & populate adapter with placeholder loading images
        ArrayListAdapter<Bitmap> adapter = new MyImageAdapter<Bitmap>(mContext);
        mImageGridView.setAdapter(adapter);
        while (adapter.getArrayList().size() < mImageURLs.size()) {
            adapter.add(mLoadingImage);
        }

        // download & decoded image Bitmaps into array adapter as we scroll
        // (and as they become visible)
        mImageGridView.setOnScrollListener(new MyOnScrollListener(adapter));

        // add a click listener to the grid view
        mImageGridView.setOnItemClickListener(new MyOnItemClickListener());
    }

    private void loadImage(final String imageURL, final Bitmap errorImage,
            final ArrayListAdapter<Bitmap> adapter, final int position) {

        VolleyManager.getRequestQueue(mContext).add(
                new ImageRequest(
                        imageURL, new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap bitmap) {
                        adapter.getArrayList().set(position, bitmap);
                        adapter.notifyDataSetChanged();
                    }
                }, 0, 0, null, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        adapter.getArrayList().set(position, errorImage);
                        adapter.notifyDataSetChanged();
                        Log.e("ImageRequest.onErrorResponse",
                                volleyError.getMessage());
                    }
                }));

    }


    ///////////////////
    // inner classes //
    ///////////////////
    /**
     * downloads & decodes image Bitmaps into array adapter as we scroll (and as
     *   they become visible)
     */
    private class MyOnScrollListener implements AbsListView.OnScrollListener {

        /** lower bound index of the AdapterView item that we've seen */
        private int mMinVisibleItem = 1;

        /** upper bound index of the AdapterView item that we've seen */
        private int mMaxVisibleItem = 0;

        private final ArrayListAdapter<Bitmap> mAdapter;

        public MyOnScrollListener(ArrayListAdapter<Bitmap> adapter) {
            this.mAdapter = adapter;
        }

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
            // do nothing
        }

        /**
         * callback method to be invoked when the list or grid has been
         * scrolled. This will be called after the scroll has completed.
         *
         * @param view View whose scroll state is being reported
         * @param firstVisibleItem index of the first visible cell
         *   (ignore if visibleItemCount == 0)
         * @param visibleItemCount number of visible cells
         * @param totalItemCount number of items in the list adaptor
         */
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            // load all newly visible items that are before what we've
            // last seen
            for (int visibleItem = firstVisibleItem;
                 visibleItem < mMinVisibleItem; ++visibleItem) {
                String imageURL = M.toThumbnail(
                        mImageURLs.get(visibleItem), M.ImageSize.BIG_SQR);
                loadImage(imageURL, mErrorImage, mAdapter, visibleItem);

            }

            // load all newly visible items that are after what we've
            // last seen
            int lastVisibleItem = Math.min(
                    firstVisibleItem+visibleItemCount,
                    totalItemCount-1);
            for (int visibleItem = lastVisibleItem;
                 visibleItem > mMaxVisibleItem; --visibleItem) {
                String imageURL = M.toThumbnail(
                        mImageURLs.get(visibleItem), M.ImageSize.BIG_SQR);
                loadImage(imageURL, mErrorImage, mAdapter, visibleItem);

            }

            // update what we've last seen
            mMinVisibleItem = Math.min(firstVisibleItem, mMinVisibleItem);
            mMaxVisibleItem = Math.max(lastVisibleItem, mMinVisibleItem);
        }
    }

    /**
     * adapter for mImageGridView
     */
    private class MyImageAdapter<T extends Bitmap> extends ArrayListAdapter<T> {

        public MyImageAdapter(Context appContext) {
            super(appContext);
        }

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
    }

    /**
     * listener for mImageGridView
     */
    private class MyOnItemClickListener implements OnItemClickListener {

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
    }
}
