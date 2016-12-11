package io.xjhub.samplecontacts;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends ListFragment {

    public static final String LOG_TAG = "MainFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new GetContactListTask().execute();
    }

    private class GetContactListTask extends AsyncTask<Void, Void, List<Api.Contact>> {

        @Override
        protected List<Api.Contact> doInBackground(Void... voids) {
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

        @Override
        protected void onPostExecute(List<Api.Contact> contacts) {
            if (contacts != null) {
                ContactListAdapter adapter = new ContactListAdapter(getActivity(), contacts);
                setListAdapter(adapter);
            }
        }

    }

}
