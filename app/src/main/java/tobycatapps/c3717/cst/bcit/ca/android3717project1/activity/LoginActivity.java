package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.VolleyManager;

public class LoginActivity extends Activity {

    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
    }

    String pass;
    String password;
    public void onGoClick(View view)
    {
        final String login = ((EditText)findViewById(R.id.editTextUsername)).getText().toString();
        password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        spinner.setVisibility(View.VISIBLE);

        String urlp1 = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/user?q={\"userHandle\":\"";
        String urlp2 = "\"}&apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx";
        String url = urlp1 + login + urlp2;

        JsonArrayRequest request = new JsonArrayRequest(
                url,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        spinner.setVisibility(View.GONE);

                        for(int i = 0; i < jsonArray.length(); ++i) {
                            try {
                                JSONObject passData = jsonArray.getJSONObject(i);
                                pass = passData.getString("pin");
                            } catch (JSONException e) {
                                throw new RuntimeException(e.toString());
                            }
                        }

                        validatePass(login, pass, password);
                    }
                },

        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                // hide loading spinner
                spinner.setVisibility(View.GONE);
                String errorStr = "";

                if (volleyError.networkResponse != null) {
                    switch (volleyError.networkResponse.statusCode) {

                        case 404:
                            errorStr = (getString(R.string.not_found));
                            break;

                        case 400:
                        case 500:
                            errorStr =(getString(R.string.server_error));
                            break;

                        default:
                            errorStr =(getString(R.string.unknown_error));
                            break;
                    }
                } else {
                    errorStr = (getString(R.string.no_internet));
                    Toast.makeText(LoginActivity.this, errorStr, Toast.LENGTH_SHORT).show();
                }
            }

        });
        VolleyManager.getRequestQueue(this).add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    private void validatePass(String login, String pass, String password)
    {

        String test = pass + " : " + password;
        Log.d("ALEX CHECK ASS", test);

        if(password.equals(pass))
        {
            Toast.makeText(LoginActivity.this, "seems legit", Toast.LENGTH_SHORT).show();
            loginComplete(login);
        }
        else
        {
            Toast.makeText(LoginActivity.this, "seems not legit", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRegisterLinkClick(View view)
    {
        Intent i = new Intent(this, AccountCreationActivity.class);
        startActivityForResult(i, 2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 2) {
            if(resultCode == RESULT_OK){
                String userhandle = data.getStringExtra("userHandle");
                loginComplete(userhandle);
            }
            if (resultCode == RESULT_CANCELED) {
                //Fill in later
            }
        }
    }

    public void loginComplete(String userhandle)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("userHandle", userhandle);
        returnIntent.putExtra("result", true);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
