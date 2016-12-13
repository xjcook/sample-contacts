package io.xjhub.samplecontacts;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class NewContactFragment extends Fragment {

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
            }
        });

    }
}
