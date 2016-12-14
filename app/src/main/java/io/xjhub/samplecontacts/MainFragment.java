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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize CursorLoader
        getLoaderManager().initLoader(1, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),
                DbModel.Contact.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        setListAdapter(new ContactCursorAdapter(getActivity(), cursor, 0));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = ((ContactCursorAdapter) getListAdapter()).getCursor();
        cursor.moveToPosition(position);

        String title = cursor.getString(cursor.getColumnIndex(DbModel.Contact.COLUMN_NAME_TITLE));
        String phone = cursor.getString(cursor.getColumnIndex(DbModel.Contact.COLUMN_NAME_PHONE));

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_ID, id);
        intent.putExtra(DetailActivity.EXTRA_TITLE, title);
        intent.putExtra(DetailActivity.EXTRA_PHONE, phone);

        startActivity(intent);
    }
}
