package io.xjhub.samplecontacts;

import android.net.Uri;
import android.provider.BaseColumns;

final class DbModel {
    // Prevents accidentally instantiating this class
    private DbModel() {}

    static final String AUTHORITY = "io.xjhub.samplecontacts.DbProvider";

    static class Contact implements BaseColumns {
        static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Contact.TABLE_NAME);
        static final String TABLE_NAME = "contact";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_PHONE = "phone";
        static final String COLUMN_NAME_KIND = "kind";
        static final String COLUMN_NAME_PICTURE = "picture";
        static final String COLUMN_NAME_PICTURE_URL = "pictureUrl";
    }

    static class Order implements BaseColumns {
        static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Order.TABLE_NAME);
        static final String TABLE_NAME = "order";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_COUNT = "count";
        static final String COLUMN_NAME_KIND = "kind";
    }

}
