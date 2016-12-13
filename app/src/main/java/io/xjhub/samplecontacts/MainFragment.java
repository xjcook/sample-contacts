package io.xjhub.samplecontacts;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ContactCursorAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        getLoaderManager().initLoader(1, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = new String[]{
                ContactModel._ID,
                ContactModel.COLUMN_NAME_TITLE,
                ContactModel.COLUMN_NAME_PHONE
        };

        return new CursorLoader(getActivity(),
                ContactModel.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        mAdapter = new ContactCursorAdapter(getActivity(), cursor, 0);
        setListAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_ID, id);
        startActivity(intent);
    }
}
