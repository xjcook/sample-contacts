package io.xjhub.samplecontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class NewContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_new);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Check that the activity is using the layout version with
        // the fragment_detail FrameLayout
        if (findViewById(R.id.fragment_contact_new) != null) {
            // Return if restored from a previous state
            if (savedInstanceState != null) {
                return;
            }

            // Pass Intent extras into fragment
            NewContactFragment newContactFragment = new NewContactFragment();
            newContactFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_contact_new' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_contact_new, newContactFragment).commit();
        }
    }

}
