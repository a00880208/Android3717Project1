package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;


public class MyActivity extends Activity {

    static boolean loggedIn = false;
    //////////////////////////
    // life cycle callbacks //
    //////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        if(!loggedIn)
        {
            findViewById(R.id.btn_imgUpload).setEnabled(false);
        }
    }

    ///////////////////////
    // android callbacks //
    ///////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

    ///////////////////////
    // interface methods //
    ///////////////////////
    public void launchLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                boolean loggedIn = data.getExtras().getBoolean("result");
                if(loggedIn)
                {
                    //if logged in is true
                    findViewById(R.id.btn_imgUpload).setEnabled(true);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                //Fill in later
            }
        }
        /*
        todo: SHOULD PROBABLY SET A GLOBAL BOOLEAN SOMEWHERE TO DO STUFF
         */
    }

    public void launchRandomPets(View view)
    {
        Intent i = new Intent(this, ImageGridViewActivity.class);
        i.putExtra(ImageGridViewActivity.KEY_IMAGE_URIS, new String[] {
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/SV7nyMD.jpg",
                "http://i.imgur.com/vuHylTZ.jpg",
                "http://i.imgur.com/pyxMupg.jpg",
                "http://i.imgur.com/7qjvKyi.jpg",
                "http://i.imgur.com/y6BBAuW.jpg"
        });
        startActivity(i);
    }

    public void launchWeeklyPets(View view) {

    }

    public void launchSearch(View view) {
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }

    public void launchImageUpload(View view)
    {
        Intent i = new Intent(this, ImageUploadActivity.class);
        startActivity(i);
    }
}
