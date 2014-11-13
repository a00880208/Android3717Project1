package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.HashMap;

public class UserImageGridViewActivity extends ImageGridViewActivity
{
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        //call parent constructor
        super.onCreate(savedInstanceState);
        //create a HashMap which contains all the images in the GridView that have been selected
        final HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();
        //an adapter class that holds images
        final ArrayListAdapter<Bitmap> myAdapter = (ArrayListAdapter<Bitmap>) mImageGridView.getAdapter();
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
}

