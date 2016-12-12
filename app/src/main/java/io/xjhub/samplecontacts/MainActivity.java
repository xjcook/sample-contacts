package io.xjhub.samplecontacts;

import android.app.LoaderManager;
import android.content.ContentProviderOperation;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Api.Contact>> {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Api.Contact>> onCreateLoader(int i, Bundle bundle) {
        return new ContactLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Api.Contact>> loader, List<Api.Contact> contacts) {
        Log.i(TAG, "Contacts reloading...");
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Delete all contacts
        ops.add(ContentProviderOperation.newDelete(ContactModel.CONTENT_URI)
                .build());

        // Insert downloaded contacts
        for (Api.Contact contact : contacts) {
            ops.add(ContentProviderOperation.newInsert(ContactModel.CONTENT_URI)
                .withValue(ContactModel.COLUMN_NAME_TITLE, contact.name)
                .withValue(ContactModel.COLUMN_NAME_PHONE, contact.phone)
                .withValue(ContactModel.COLUMN_NAME_KIND, contact.kind)
                .build());
        }

        try {
            getContentResolver().applyBatch(ContactModel.AUTHORITY, ops);
        } catch (RemoteException | OperationApplicationException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Api.Contact>> loader) {

    }
}
