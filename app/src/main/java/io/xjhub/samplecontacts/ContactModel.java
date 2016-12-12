package io.xjhub.samplecontacts;

import android.net.Uri;
import android.provider.BaseColumns;

final class ContactModel implements BaseColumns {

    static final String AUTHORITY = "io.xjhub.samplecontacts.ContactProvider";
    static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ContactModel.TABLE_NAME);

    static final String TABLE_NAME = "contact";
    static final String COLUMN_NAME_TITLE = "title";
    static final String COLUMN_NAME_PHONE = "phone";
    static final String COLUMN_NAME_KIND = "kind";
    static final String COLUMN_NAME_PICTURE = "picture";

}
