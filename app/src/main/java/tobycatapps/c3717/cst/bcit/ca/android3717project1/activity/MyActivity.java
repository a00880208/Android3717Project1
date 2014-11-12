package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;


public class MyActivity extends Activity {



    //////////////////////////
    // life cycle callbacks //
    //////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
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

    }

    public void launchRandomPets(View view)
    {
        Intent i = new Intent(this, ImageGridViewActivity.class);
        i.putExtra(ImageGridViewActivity.KEY_IMAGE_URIS, new String[] {
                "http://rahviews.com/wp-content/uploads/2013/03/Hats-for-Cats5.jpg",
                "http://explosionhub.com/wp-content/uploads/2012/07/small-cat.jpg",
                "http://millionwaystoearnmoney.com/admin/uploads/category/male-cat2.JPG",
                "http://www.catchannel.com/images/constipation-in-cats.jpg",
                "http://rahviews.com/wp-content/uploads/2013/03/Hats-for-Cats4.jpg",
                "http://www.catchannel.com/images/tech-cat-tagg.jpg",
                "http://www.catchannel.com/images/depressed-cat-hiss.jpg",
                "http://www.petspictured.com/cats/lg_pix/tg-gray-cat-lg.jpg",
                "http://www.weirdpalace.com/img/fun/yoga-cats/yoga-cats07.jpg"
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
