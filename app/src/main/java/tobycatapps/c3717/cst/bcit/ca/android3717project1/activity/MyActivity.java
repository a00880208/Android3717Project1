package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.SessionData;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.dbAccess;



public class MyActivity extends Activity {

    static SessionData session;
    //////////////////////////
    // life cycle callbacks //
    //////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        session = new SessionData();

        //Set logged in to false
        session.isLoggedIn = false;

        if(!session.isLoggedIn)
        {
            logoutButtonSet();
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

    public void launchRandomPets(View view)
    {
        dbAccess.getAllImages(this,

                new Response.Listener<ArrayList<String>>() {
                    @Override
                    public void onResponse(ArrayList<String> response) {

                        Intent i = new Intent(MyActivity.this, ImageGridViewActivity.class);
                        i.putExtra(ImageGridViewActivity.KEY_IMAGE_URIS, response);
                        startActivity(i);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error launching random pets", error.toString());
                    }
                });
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
                    loginButtonSet(data.getStringExtra("userHandle"));
                    //if logged in is true
                }
            }
            if (resultCode == RESULT_CANCELED) {
                //Fill in later
            }
        }
    }

    public void launchLogout(View view)
    {
        logoutButtonSet();
    }

    public void launchMyAccount(View view)
    {
        String user = session.userHandle;
        final Intent i = new Intent(this, UserImageGridViewActivity.class);

        Response.Listener<ArrayList<String>> r = new Response.Listener<ArrayList<String>>()
        {
            public void onResponse(ArrayList<String> response) {
                ArrayList<String> imageUris;
                imageUris = response;
                i.putExtra(ImageGridViewActivity.KEY_IMAGE_URIS, imageUris);
                startActivity(i);
            }
        };
        dbAccess.getUserImages(this, user, r, null);
    }

    private void loginButtonSet(String userhandle)
    {
        session.isLoggedIn = true;
        session.userHandle = userhandle;
        findViewById(R.id.btn_login).setVisibility(View.GONE);
        findViewById(R.id.btn_MyAccount).setVisibility(View.VISIBLE);
    }

    private void logoutButtonSet()
    {    session.isLoggedIn = false;
        findViewById(R.id.btn_MyAccount).setVisibility(View.GONE);
        findViewById(R.id.btn_login).setVisibility(View.VISIBLE);
    }
}
