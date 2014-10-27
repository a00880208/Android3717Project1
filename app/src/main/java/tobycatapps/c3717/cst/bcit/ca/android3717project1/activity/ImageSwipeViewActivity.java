package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.AppController;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;


public class ImageSwipeViewActivity extends Activity {

    int galleryIndex;
    String[] parsedUrls;
    ImageView mImageView;

    public final static String URL_LIST =
            "tobycatapps.c3717.cst.bcit.ca.android3717project1.urlist";
    public final static String INDEX =
            "tobycatapps.c3717.cst.bcit.ca.android3717project1.index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_swipe_view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mImageView = (ImageView) findViewById(R.id.imageViewer);
        //openImage(url, mImageView);

        System.out.println("ALEX: ImageSwipeView launched!");

        Intent randomPetsIntent = getIntent();
        parsedUrls = randomPetsIntent.getStringArrayExtra(URL_LIST);

        galleryIndex = randomPetsIntent.getIntExtra(INDEX, 0);
        openImage(parsedUrls,galleryIndex, mImageView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_swipe_view, menu);
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
// Access the RequestQueue through your singleton class.
        AppController.getInstance().getRequestQueue().add(request);
//        image.setImageResource(R.drawable.ic_launcher);
    }

    //Tempory button to change the image to image in next index
    public void NextClick(View view)
    {
        System.out.println("ALEX: NEXTCLICK");
        NextImage();
    }

    //Temporary button to change the image to image in previous index
    public void BackClick(View view)
    {
        System.out.println("ALEX: BACKCLICK");
        BackImage();
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
