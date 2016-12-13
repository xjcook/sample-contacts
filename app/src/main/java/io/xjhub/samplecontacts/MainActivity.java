package io.xjhub.samplecontacts;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getLoaderManager().initLoader(0, null, this);

        // Check that the activity is using the layout version with
        // the fragment_main FrameLayout
        if (findViewById(R.id.fragment_main) != null) {
            // Return if restored from a previous state
            if (savedInstanceState != null) {
                return;
            }

            // Pass Intent extras into fragment
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_main' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_main, mainFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem newContactItem = menu.findItem(R.id.action_newcontact);

        newContactItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
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
