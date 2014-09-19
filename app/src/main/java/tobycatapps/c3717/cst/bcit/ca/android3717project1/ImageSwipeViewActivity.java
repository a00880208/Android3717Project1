package tobycatapps.c3717.cst.bcit.ca.android3717project1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;


public class ImageSwipeViewActivity extends Activity {


    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_swipe_view);
        String url = "http://millionwaystoearnmoney.com/admin/uploads/category/male-cat2.JPG";
        mImageView = (ImageView) findViewById(R.id.imageViewer);
        openImage(url, mImageView);
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

    public void openImage(String url, final ImageView imageView)
    {
        System.out.println("openImage success");
        ImageRequest request =
                new ImageRequest(url, new Response.Listener<Bitmap>() {
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

}
