package io.xjhub.samplecontacts;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewContactFragment extends Fragment {

    private static final String TAG = "NewContactFragment";
    private static final int MIN_LENGTH = 5;

    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private Button mAddButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact_new, container, false);

        mNameEditText = (EditText) rootView.findViewById(R.id.nameEditText);
        mPhoneEditText = (EditText) rootView.findViewById(R.id.phoneEditText);
        mAddButton = (Button) rootView.findViewById(R.id.addBtn);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check minimum length
                if (mNameEditText.getText().length() < MIN_LENGTH) {
                    mNameEditText.setError(getString(R.string.error_min_length));
                }

                if (mPhoneEditText.getText().length() < MIN_LENGTH) {
                    mPhoneEditText.setError(getString(R.string.error_min_length));
                }

                // Upload new contact
                String name = mNameEditText.getText().toString();
                String phone = mPhoneEditText.getText().toString();

                if (name.length() >= MIN_LENGTH && phone.length() >= MIN_LENGTH) {
                    new CreateNewContact(name, phone).execute();
                }
            }
        });

    }

    private class CreateNewContact extends AsyncTask<Void, Void, Api.Contact> {

        private Api.Contact mContact;

        CreateNewContact(String name, String phone) {
            mContact = new Api.Contact(name, phone);
        }

        @Override
        protected Api.Contact doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.URL + Api.CONTACT_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api.ContactService service = retrofit.create(Api.ContactService.class);
            Call<Api.Contact> call = service.createContact(mContact);

            Api.Contact contact = null;
            try {
                Log.i(TAG, "Contact uploading...");
                contact = call.execute().body();
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }

            return contact;
        }

        @Override
        protected void onPostExecute(Api.Contact contact) {
            if (contact != null) {
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(),
                        R.string.error_upload_contact, Toast.LENGTH_LONG).show();
            }
        }
    }
}
