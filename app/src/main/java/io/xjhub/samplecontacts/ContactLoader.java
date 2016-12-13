package io.xjhub.samplecontacts;

import android.content.AsyncTaskLoader;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ContactLoader extends AsyncTaskLoader<Void> {

    private static final String TAG = "ContactLoader";

    ContactLoader(Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    public Void loadInBackground() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.ContactService service = retrofit.create(Api.ContactService.class);
        Call<Api.ContactWrapper> call = service.listContacts();

        Api.ContactWrapper contactWrapper = null;
        try {
            Log.i(TAG, "Contacts downloading...");
            contactWrapper = call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        if (contactWrapper != null) {
            Log.i(TAG, "Contacts reloading...");
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();

            // Delete all contacts
            ops.add(ContentProviderOperation.newDelete(ContactModel.CONTENT_URI)
                    .build());

            // Insert downloaded contacts
            for (Api.Contact contact : contactWrapper.items) {
                if (!TextUtils.isEmpty(contact.name)) {
                    ops.add(ContentProviderOperation.newInsert(ContactModel.CONTENT_URI)
                            .withValue(ContactModel.COLUMN_NAME_TITLE, contact.name)
                            .withValue(ContactModel.COLUMN_NAME_PHONE, contact.phone)
                            .withValue(ContactModel.COLUMN_NAME_KIND, contact.kind)
                            .build());
                }
            }

            try {
                getContext().getContentResolver().applyBatch(ContactModel.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }

        return null;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}
