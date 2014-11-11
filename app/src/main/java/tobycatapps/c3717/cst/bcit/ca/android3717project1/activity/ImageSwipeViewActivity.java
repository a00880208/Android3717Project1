package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.VolleyManager;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;

public class ImageSwipeViewActivity extends Activity implements GestureDetector.OnGestureListener{

    int galleryIndex;
    String[] parsedUrls;
    ImageView mImageView;

    public final static String URL_LIST =
            "tobycatapps.c3717.cst.bcit.ca.android3717project1.urlist";
    public final static String INDEX =
            "tobycatapps.c3717.cst.bcit.ca.android3717project1.index";

    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_swipe_view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mImageView = (ImageView) findViewById(R.id.imageViewer);

        System.out.println("ALEX: ImageSwipeView launched!");

        Intent randomPetsIntent = getIntent();
        parsedUrls = randomPetsIntent.getStringArrayExtra(URL_LIST);

        galleryIndex = randomPetsIntent.getIntExtra(INDEX, 0);
        openImage(parsedUrls,galleryIndex, mImageView);

        mDetector = new GestureDetector(this, this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //this.mDetector.onTouchEvent(event);
        MotionEvent.PointerCoords ptrCoords = new MotionEvent.PointerCoords();
        event.getPointerCoords(0, ptrCoords);
        Log.e("ptrCoords.x", String.valueOf(ptrCoords.x));
        Log.e("ptrCoords.y", String.valueOf(ptrCoords.y));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        float sensitvity = 50;

        if((e1.getX() - e2.getX()) > sensitvity){
            NextImage();
        }else if((e2.getX() - e1.getX()) > sensitvity){
            BackImage();
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_swipe_view, menu);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            /** android's up button is pressed */
            case android.R.id.home:
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            upIntent.putExtra(ImageGridViewActivity.KEY_IMAGE_URIS, parsedUrls);
            NavUtils.navigateUpTo(this, upIntent);
            return true;

            /** the settings MenuItem was pressed */
            case R.id.action_settings:
            return true;

            /** idk; let the system deal with it */
            default:
            return super.onOptionsItemSelected(item);
        }
    }



    //Open image function takes the array of strings, parses the right one out and then updates
    //the view with the image
    public void openImage(String[] parsedUrls, int galleryIndex, final ImageView imageView)
    {
        ImageRequest request =
                new ImageRequest(parsedUrls[galleryIndex], new Response.Listener<Bitmap>() {
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {}
                });
        VolleyManager.getRequestQueue(this).add(request);
    }


    //function to change the image to the next image and update it
    public void NextImage()
    {
        System.out.println("ALEX: NEXTIMAGE");
        if(galleryIndex < parsedUrls.length -1)
        {
            galleryIndex++;
            System.out.println("ALEX: "+galleryIndex+" "+parsedUrls[galleryIndex]);
            openImage(parsedUrls, galleryIndex, mImageView);
        }
        else
        {
            NoMoreImagesToast().show();
        }
    }

    //function to change the image to the previous image and update it
    public void BackImage()
    {
        System.out.println("ALEX: BACKIMAGE");
        if(galleryIndex > 0)
        {
            galleryIndex--;
            openImage(parsedUrls, galleryIndex, mImageView);
        }
        else
        {
            NoMoreImagesToast().show();
        }
    }

    //Creates a toast that notifies the user that there are no more images.
    //Ignore the yellow, it's just tripping out that the toast isn't instantly used!
    public Toast NoMoreImagesToast()
    {
        Toast toast = Toast.makeText(getApplicationContext(), "There are no more images!", Toast.LENGTH_SHORT);
        return toast;
    }

    
}
