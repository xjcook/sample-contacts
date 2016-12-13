package io.xjhub.samplecontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        // Pass ContactId into fragment
        Bundle args = new Bundle();
        Intent intent = getIntent();

        args.putLong(EXTRA_ID, intent.getLongExtra(EXTRA_ID, -1));

        fragment.setArguments(args);
    }
}
