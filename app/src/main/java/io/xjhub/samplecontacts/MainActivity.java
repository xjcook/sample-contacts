package io.xjhub.samplecontacts;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void> {

    private static final String TAG = "MainActivity";
    private static final int CONTACT_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup ActionBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Initialize ContactLoader
        getLoaderManager().initLoader(CONTACT_LOADER, null, this);

        // Register an observer
        LocalBroadcastManager.getInstance(this).registerReceiver(mSyncReceiver,
                new IntentFilter("sync-contacts"));

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

    private BroadcastReceiver mSyncReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Restart ContactLoader
            Log.i(TAG, "Force sync contacts");
            getLoaderManager().restartLoader(CONTACT_LOADER, null, MainActivity.this);
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister an observer
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mSyncReceiver);
        super.onDestroy();
    }
}
