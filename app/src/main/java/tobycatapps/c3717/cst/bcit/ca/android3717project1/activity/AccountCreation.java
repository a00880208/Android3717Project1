package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.DatabaseUser;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;

public class AccountCreation extends Activity
{
    EditText et_userHandle;
    EditText et_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        et_userHandle = (EditText) findViewById(R.id.ac_et_label_username);
        et_pin = (EditText) findViewById(R.id.ac_et_label_pin);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_creation, menu);
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

    public void submitAccount (View v)
    {
        DatabaseUser user = new DatabaseUser();
        user.setUserHandle(et_userHandle.getText().toString());
        user.setPin(et_pin.getText().toString());

        final String userHandle = user.getUserHandle();

        Response.Listener<Boolean> r = new Response.Listener<Boolean>()
        {
            public void onResponse(Boolean response) {
                //on success account creation
                if(response)
                {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", true);
                    returnIntent.putExtra("userHandle", userHandle);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        };

        //dbAccess.newUser(this, user.getUserHandle(), user.getPin(), r);
    }
}
