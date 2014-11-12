package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.ImgurUploadTask;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;


public class ImageUploadActivity extends Activity
{
    private final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void pickImageOnClick(View view)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                imageView.setImageBitmap(bitmap);

                selectedImage = data.getData();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploadOnClick(View view)
    {
        if(selectedImage != null)
        {
            new myImgurUploadTask(selectedImage, this).execute();
        }
    }

    private class myImgurUploadTask extends ImgurUploadTask
    {

        public myImgurUploadTask(Uri imageUri, Activity activity)
        {
            super(imageUri, activity);
        }

        @Override
        public void onPostExecute(String imageId) {
            super.onPostExecute(imageId);
            boolean test = (imageId == null);
            if (test) {
                Toast.makeText(getApplicationContext(), "shit the bed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), imageId, Toast.LENGTH_SHORT).show();
            }

            //send to database imgurdata
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_upload, menu);
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
}
