package io.xjhub.samplecontacts;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Void> onCreateLoader(int i, Bundle bundle) {
        return new ContactLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void aVoid) {}

    @Override
    public void onLoaderReset(Loader<Void> loader) {}
}
