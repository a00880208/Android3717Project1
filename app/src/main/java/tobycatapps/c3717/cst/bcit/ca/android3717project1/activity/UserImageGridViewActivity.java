package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.ImgurUploadTask;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;

public class UserImageGridViewActivity extends ImageGridViewActivity
{

    private final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    private Uri selectedImage;
    private Bitmap mLoadingImage;
    ArrayListAdapter<Bitmap> myAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        //call parent constructor
        super.onCreate(savedInstanceState);
        findViewById(R.id.btn_addImage).setVisibility(View.VISIBLE);
        //create a HashMap which contains all the images in the GridView that have been selected
        final HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();
        //an adapter class that holds images
        myAdapter = (ArrayListAdapter<Bitmap>) mImageGridView.getAdapter();
        mImageGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        mImageGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id) {
                //dialog builder
                AlertDialog.Builder builder1 = new AlertDialog.Builder(UserImageGridViewActivity.this);
                builder1.setMessage("Confirm Deleting Image.");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //put code to remove the image from the gridview
                                myAdapter.getArrayList().remove(position);
                                mImageURLs.remove(position);
                                myAdapter.notifyDataSetChanged();
                                //put code to remove the image from the DB

                                //close the dialog after click
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder1.create();
                alert.show();

                //end dialog builder
                return true;
            }
        });
    }

    public void uploadImageButton (View view)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);

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
                try
                {
                    Toast.makeText(getApplicationContext(), imageId, Toast.LENGTH_SHORT).show();
                    //get the new list of URI
                    String temp = "http://i.imgur.com/"+imageId+".jpg";
                    mImageURLs.add(temp);
                    Log.d("MALFORMED URL: ", temp);
                    myAdapter.getArrayList().add(null);
                    loadImage(imageId, myAdapter, mImageURLs.size() - 1);
                    //repopulate GridView
                    //mImageGridView.invalidateViews();
                }
                catch (RuntimeException e)
                {

                }

            }

            //send to database imgurdata
        }
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

                selectedImage = data.getData();

                if(selectedImage != null)
                {
                    new myImgurUploadTask(selectedImage, this).execute();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

