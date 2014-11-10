package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tobycatapps.c3717.cst.bcit.ca.android3717project1.VolleyManager;
import tobycatapps.c3717.cst.bcit.ca.android3717project1.R;

public class SearchActivity extends Activity {



    ////////////////////
    // GUI references //
    ////////////////////
    /** text used to give the user feedback when the search goes wrong */
    private TextView feedbackText;

    /** spinner used to indicate loading */
    private ProgressBar spinner;

    /** used to specify a uploader name filter for the search */
    private EditText uploaderNameFilterInput;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput1;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput3;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput5;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput7;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput9;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput2;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput4;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput6;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput8;

    /** used to activate or deactivate an image tag */
    private CheckBox tagInput10;



    //////////////////////////
    // life cycle callbacks //
    //////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeGUIReferences();
    }



    ///////////////////////
    // android callbacks //
    ///////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
    /**
     * constructs a query and executes it on the internet. when it responds,
     * begins the ImageGridViewActivity; displays an error message otherwise
     */
    public void search(View view) {

        // show loading spinner
        spinner.setVisibility(View.VISIBLE);
        feedbackText.setVisibility(View.GONE);

        // get tags user inputs
        ArrayList<String> tags = new ArrayList<String>();
        if (tagInput1.isChecked()) tags.add(tagInput1.getText().toString());
        if (tagInput3.isChecked()) tags.add(tagInput3.getText().toString());
        if (tagInput5.isChecked()) tags.add(tagInput5.getText().toString());
        if (tagInput7.isChecked()) tags.add(tagInput7.getText().toString());
        if (tagInput9.isChecked()) tags.add(tagInput9.getText().toString());
        if (tagInput2.isChecked()) tags.add(tagInput2.getText().toString());
        if (tagInput4.isChecked()) tags.add(tagInput4.getText().toString());
        if (tagInput6.isChecked()) tags.add(tagInput6.getText().toString());
        if (tagInput8.isChecked()) tags.add(tagInput8.getText().toString());
        if (tagInput10.isChecked()) tags.add(tagInput10.getText().toString());

        // get uploader name user input
        String uploaderName = uploaderNameFilterInput.getText().toString();

        // construct query
        StringBuilder query = new StringBuilder();
        query.append("{");
        if (!uploaderName.isEmpty()) {
            query.append("\"Uploader\":\"");
            query.append(uploaderName);
            query.append("\",");
        }
        for (String tag : tags) {
            query.append("\"Tags\":\"");
            query.append(tag);
            query.append("\",");
        }
        query.setLength(query.length() - 1);
        query.append("}");
        String url = "https://api.mongolab.com/api/1/databases/petbitsdb/collections/image?apiKey=vPbnh_1kRwQBVtry-B6IiUh_yXYZHbZx&"
                + "q=" + query.toString();

        // execute query and start the gridview activity
        JsonArrayRequest request =
                new JsonArrayRequest(
                        url,

                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray data) {

                                // hide loading spinner
                                spinner.setVisibility(View.GONE);

                                // parse data
                                String[] imageUris = new String[data.length()];
                                for (int i = 0; i < data.length(); ++i) {
                                    try {
                                        JSONObject imageData = data.getJSONObject(i);
                                        imageUris[i] = imageData.getString("URL");
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e.toString());
                                    }
                                }

                                // start ImageGridViewActivity
                                if (imageUris.length > 0) {
                                    Intent i = new Intent(SearchActivity.this, ImageGridViewActivity.class);
                                    i.putExtra(ImageGridViewActivity.KEY_IMAGE_URIS, imageUris);
                                    startActivity(i);
                                } else {
                                    feedbackText.setVisibility(View.VISIBLE);
                                    feedbackText.setText(getString(R.string.zero_results));
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                // hide loading spinner
                                spinner.setVisibility(View.GONE);

                                if (volleyError.networkResponse != null) {
                                    switch (volleyError.networkResponse.statusCode) {

                                        case 404:
                                            feedbackText.setText(getString(R.string.not_found));
                                            break;

                                        case 400:
                                        case 500:
                                            feedbackText.setText(getString(R.string.server_error));
                                            break;

                                        default:
                                            feedbackText.setText(getString(R.string.unknown_error));
                                            break;
                                    }
                                } else {
                                    feedbackText.setText(getString(R.string.no_internet));
                                }
                                feedbackText.setVisibility(View.VISIBLE);
                            }
                        });

        VolleyManager.getInstance().getRequestQueue().add(request);
    }

    /** clears the fields on the form */
    public void clearFields(View view) {
        uploaderNameFilterInput.setText("");
        tagInput1.setChecked(false);
        tagInput3.setChecked(false);
        tagInput5.setChecked(false);
        tagInput7.setChecked(false);
        tagInput9.setChecked(false);
        tagInput2.setChecked(false);
        tagInput4.setChecked(false);
        tagInput6.setChecked(false);
        tagInput8.setChecked(false);
        tagInput10.setChecked(false);
    }



    /////////////////////
    // support methods //
    /////////////////////
    private void initializeGUIReferences() {
        feedbackText = (TextView) findViewById(R.id.feedback);
        spinner = (ProgressBar) findViewById(R.id.spinner);
        uploaderNameFilterInput = (EditText) findViewById(R.id.uploader_search_edit_text);
        tagInput1 = (CheckBox) findViewById(R.id.checkBox1);
        tagInput3 = (CheckBox) findViewById(R.id.checkBox3);
        tagInput5 = (CheckBox) findViewById(R.id.checkBox5);
        tagInput7 = (CheckBox) findViewById(R.id.checkBox7);
        tagInput9 = (CheckBox) findViewById(R.id.checkBox9);
        tagInput2 = (CheckBox) findViewById(R.id.checkBox2);
        tagInput4 = (CheckBox) findViewById(R.id.checkBox4);
        tagInput6 = (CheckBox) findViewById(R.id.checkBox6);
        tagInput8 = (CheckBox) findViewById(R.id.checkBox8);
        tagInput10 = (CheckBox) findViewById(R.id.checkBox10);
    }
}
