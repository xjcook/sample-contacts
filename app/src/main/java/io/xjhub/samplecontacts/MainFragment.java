package io.xjhub.samplecontacts;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<List<Api.Contact>> {

    private static final String LOG_TAG = "MainFragment";

    private ContactAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ContactAdapter(getActivity(), new ArrayList<Api.Contact>());
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<List<Api.Contact>> onCreateLoader(int i, Bundle bundle) {
        return new ContactLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Api.Contact>> loader, List<Api.Contact> contacts) {
        mAdapter.setData(contacts);
    }

    @Override
    public void onLoaderReset(Loader<List<Api.Contact>> loader) {
        mAdapter.setData(new ArrayList<Api.Contact>());
    }
}
