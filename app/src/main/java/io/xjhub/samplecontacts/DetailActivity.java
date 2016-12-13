package io.xjhub.samplecontacts;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void> {

    public static final String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getLoaderManager().initLoader(2, null, this);

        // Check that the activity is using the layout version with
        // the fragment_detail FrameLayout
        if (findViewById(R.id.fragment_detail) != null) {
            // Return if restored from a previous state
            if (savedInstanceState != null) {
                return;
            }

            // Pass Intent extras into fragment
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_detail' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail, detailFragment).commit();
        }
    }

    @Override
    public Loader<Void> onCreateLoader(int i, Bundle bundle) {
        long contactId = getIntent().getLongExtra(EXTRA_ID, 0);
        return new OrderLoader(this, contactId);
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void aVoid) {

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
}
