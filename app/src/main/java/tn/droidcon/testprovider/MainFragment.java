package tn.droidcon.testprovider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import tn.droidcon.testprovider.adapter.CustomAdapter;
import tn.droidcon.testprovider.database.TestContentProvider;
import tn.droidcon.testprovider.database.tables.RecordsTable;
import tn.droidcon.testprovider.ui.AddDialog;
import tn.droidcon.testprovider.wrapper.ListItemWrapper;


public class MainFragment extends Fragment implements AddDialog.OnAddListener, LoaderManager.LoaderCallbacks<Cursor>, CustomAdapter.OnItemClickedListener {


    private static final String LIST_CONTENT_KEY = "list_content_key";
    private static final int RECORD_TABLE_ID = 1;


    private CustomAdapter mAdapter;
    private ArrayList<ListItemWrapper> mObjectList;
    private android.support.v4.app.LoaderManager mLoaderManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);


        mLoaderManager = getLoaderManager();

        if (savedInstanceState == null) {
            mLoaderManager.initLoader(RECORD_TABLE_ID, null, this);
            mObjectList = new ArrayList<ListItemWrapper>();

        } else {
            mObjectList = (ArrayList<ListItemWrapper>) savedInstanceState.getSerializable(LIST_CONTENT_KEY);
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CustomAdapter(mObjectList, this);
        recyclerView.setAdapter(mAdapter);
        setHasOptionsMenu(true);
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(LIST_CONTENT_KEY, mObjectList);

        super.onSaveInstanceState(outState);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            AddDialog addDialog = AddDialog.newInstance(this);
            addDialog.show(getActivity().getSupportFragmentManager(), "");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOkClicked(ListItemWrapper listItemWrapper) {

        mObjectList.add(listItemWrapper);
        mAdapter.notifyDataSetChanged();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecordsTable.LABEL, listItemWrapper.getTitle());
        contentValues.put(RecordsTable.DESCRIPTION, listItemWrapper.getDescription());

        Uri uri = getActivity().getContentResolver().insert(
                TestContentProvider.RECORDS_CONTENT_URI,
                contentValues);

        listItemWrapper.setId(Long.valueOf(uri.getLastPathSegment()));
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getActivity().getApplicationContext(), TestContentProvider.RECORDS_CONTENT_URI,
                RecordsTable.PROJECTION_ALL, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v("slim", "onLoadFinished called");
        if (loader.getId() == RECORD_TABLE_ID) {
            Log.v("slim", "onLoadFinished: RecordTable");

            if (data.moveToFirst()) {
                while (!data.isAfterLast()) {
                    mObjectList.add(createListItem(data));
                    if (!data.isClosed()) {
                        data.moveToNext();
                    }
                }

                mAdapter.notifyDataSetChanged();
            }
            data.close();
            mLoaderManager.destroyLoader(RECORD_TABLE_ID);
        }


    }

    @Override
    public void onLoaderReset(Loader loader) {
//do nothing
    }


    private ListItemWrapper createListItem(Cursor data) {

        ListItemWrapper listItemWrapper = new ListItemWrapper();

        listItemWrapper.setId(data.getLong(data.getColumnIndex(RecordsTable._ID)));
        listItemWrapper.setTitle(data.getString(data.getColumnIndex(RecordsTable.LABEL)));
        listItemWrapper.setDescription(data.getString(data.getColumnIndex(RecordsTable.DESCRIPTION)));

        return listItemWrapper;
    }

    @Override
    public void onItemClicked(int position) {
        //delete the clicked item


        Log.v("slim", "mObjectList removed item position = "+position);
        Log.v("slim", "mObjectList removed item id = "+mObjectList.get(position).getId());
        Log.v("slim", "mObjectList removed item title = "+mObjectList.get(position).getTitle());

        getActivity().getContentResolver().delete(
                ContentUris.withAppendedId(
                        TestContentProvider.RECORDS_CONTENT_URI, mObjectList.get(position).getId()),
                null, null);

        mObjectList.remove(position);
        mAdapter.notifyItemRemoved(position);


        Log.v("slim", "delete performed");
        for(ListItemWrapper listItemWrapper : mObjectList){
            Log.v("slim", "mObjectList item id = "+listItemWrapper.getId());
            Log.v("slim", "mObjectList item title = "+listItemWrapper.getTitle());
            Log.v("slim", "-----------------------");
        }

    }
}