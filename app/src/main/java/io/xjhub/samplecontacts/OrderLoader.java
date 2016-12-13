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

class OrderLoader extends AsyncTaskLoader<Void> {

    private static final String TAG = "OrderLoader";

    private long mContactId;

    OrderLoader(Context context, long contactId) {
        super(context);
        mContactId = contactId;
        onContentChanged();
    }

    @Override
    public Void loadInBackground() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.URL + Api.ORDER_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.ContactService service = retrofit.create(Api.ContactService.class);
        Call<Api.OrderWrapper> call = service.listOrders(mContactId);

        Api.OrderWrapper orderWrapper = null;
        try {
            Log.i(TAG, "Orders for contact: " + mContactId + " downloading...");
            orderWrapper = call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        if (orderWrapper != null && orderWrapper.items.size() > 0) {
            Log.i(TAG, "Orders for contact: " + mContactId + " reloading...");
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();

            // Delete all orders for contact
            ops.add(ContentProviderOperation.newDelete(DbModel.Order.CONTENT_URI)
                    .withSelection(
                            DbModel.Order.COLUMN_NAME_CONTACT_ID + "=?",
                            new String[]{String.valueOf(mContactId)})
                    .build());

            // Insert downloaded orders
            for (Api.Order order : orderWrapper.items) {
                if (!TextUtils.isEmpty(order.name)) {
                    ops.add(ContentProviderOperation.newInsert(DbModel.Order.CONTENT_URI)
                            .withValue(DbModel.Order.COLUMN_NAME_CONTACT_ID, mContactId)
                            .withValue(DbModel.Order.COLUMN_NAME_TITLE, order.name)
                            .withValue(DbModel.Order.COLUMN_NAME_COUNT, order.count)
                            .withValue(DbModel.Order.COLUMN_NAME_KIND, order.kind)
                            .build());
                }
            }

            try {
                getContext().getContentResolver().applyBatch(DbModel.AUTHORITY, ops);
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
