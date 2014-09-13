package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;


public class ImageGridViewActivity extends Activity {

    // references to GUI views
    /** reference to grid view that contains all the images */
    private static GridView mImageGridView;

    // starting intent keys and values
    public static final String KEY_IMAGE_URIS = "KEY_IMAGE_URIS";
    private String[] mImageURIs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid_view);  // set activity's screen layout
        initializeGUIReferences();

        // extract information from starting intent
        Intent i = getIntent();
        mImageURIs = i.getStringArrayExtra(KEY_IMAGE_URIS);
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

    /**
     * initializes all GUI references of this activity; initializes value of the GUI reference with
     * the corresponding view in the layout.
     */
    private void initializeGUIReferences() {
        mImageGridView = (GridView) findViewById(R.id.grid_view_images);
    }
}
