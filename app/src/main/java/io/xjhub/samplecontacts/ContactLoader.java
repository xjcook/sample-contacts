package io.xjhub.samplecontacts;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ContactLoader extends AsyncTaskLoader<List<Api.Contact>> {

    private static final String LOG_TAG = "ContactLoader";

    ContactLoader(Context context) {
        super(context);
    }

    @Override
    public List<Api.Contact> loadInBackground() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.ContactService service = retrofit.create(Api.ContactService.class);
        Call<Api.ContactWrapper> call = service.listContacts();

        try {
            Api.ContactWrapper contactWrapper = call.execute().body();
            if (contactWrapper != null) {
                return contactWrapper.items;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));
        }

        return null;
    }

}
