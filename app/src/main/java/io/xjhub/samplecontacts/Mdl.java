package io.xjhub.samplecontacts;

import android.provider.BaseColumns;

final class Mdl {
    // Prevents accidentally instantiating this class
    private Mdl() {}

    /* Inner class that defines the table contents */
    static class Contact implements BaseColumns {
        static final String TABLE_NAME = "contact";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_PHONE = "phone";
        static final String COLUMN_NAME_KIND = "kind";
        static final String COLUMN_NAME_PICTURE = "picture";
    }
}
