package io.xjhub.samplecontacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "contacts.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactModel.TABLE_NAME + " (" +
                    ContactModel._ID + " INTEGER PRIMARY KEY," +
                    ContactModel.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    ContactModel.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    ContactModel.COLUMN_NAME_KIND + TEXT_TYPE + COMMA_SEP +
                    ContactModel.COLUMN_NAME_PICTURE_URL + TEXT_TYPE + COMMA_SEP +
                    ContactModel.COLUMN_NAME_PICTURE + BLOB_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactModel.TABLE_NAME;

    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
