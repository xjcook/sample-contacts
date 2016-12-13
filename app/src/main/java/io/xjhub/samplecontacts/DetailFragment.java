package io.xjhub.samplecontacts;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class DetailFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialize CursorLoader
        getLoaderManager().initLoader(3, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = new String[]{
                DbModel.Order._ID,
                DbModel.Order.COLUMN_NAME_TITLE
        };

        String selection = null;
        String[] selectionArgs = null;
        Bundle args = getArguments();

        if (args != null) {
            long id = getArguments().getLong(DetailActivity.EXTRA_ID);
            if (id != 0) {
                selection = DbModel.Order.COLUMN_NAME_CONTACT_ID + "=?";
                selectionArgs = new String[]{String.valueOf(id)};
            }
        }

        return new CursorLoader(getActivity(),
                DbModel.Order.CONTENT_URI, projection, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        setListAdapter(new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{DbModel.Order.COLUMN_NAME_TITLE},
                new int[]{android.R.id.text1},
                0
        ));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}

}
