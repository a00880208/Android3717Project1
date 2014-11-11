package tobycatapps.c3717.cst.bcit.ca.android3717project1.activity;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * An ArrayAdapter for ListViews and GridViews ... contains extra methods for
 * convenience that the base class ArrayAdapter does not have.
 *
 * Created by Eric on 2014-11-11.
 */
public class ArrayListAdapter<T> extends ArrayAdapter<T> {

    private ArrayList<T> mArrayList;

    public ArrayListAdapter(Context appContext) {
        this(appContext, new ArrayList<T>());
    }

    private ArrayListAdapter(Context appContext, ArrayList<T> arrayList) {
        super(appContext, android.R.layout.simple_list_item_1, arrayList);
        mArrayList = arrayList;
    }

    public ArrayList<T> getArrayList() {
        return mArrayList;
    }
}
