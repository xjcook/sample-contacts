package io.xjhub.samplecontacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "simplecontacts.db";

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CONTACT_CREATE_ENTRIES =
            "CREATE TABLE " + DbModel.Contact.TABLE_NAME + " (" +
                    DbModel.Contact._ID + " INTEGER PRIMARY KEY," +
                    DbModel.Contact.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DbModel.Contact.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    DbModel.Contact.COLUMN_NAME_KIND + TEXT_TYPE + COMMA_SEP +
                    DbModel.Contact.COLUMN_NAME_PICTURE_URL + TEXT_TYPE + COMMA_SEP +
                    DbModel.Contact.COLUMN_NAME_PICTURE + BLOB_TYPE + " )";

    private static final String SQL_ORDER_CREATE_ENTRIES =
            "CREATE TABLE " + DbModel.Order.TABLE_NAME + " (" +
                    DbModel.Order._ID + " INTEGER PRIMARY KEY," +
                    DbModel.Order.COLUMN_NAME_CONTACT_ID + INTEGER_TYPE + COMMA_SEP +
                    DbModel.Order.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DbModel.Order.COLUMN_NAME_COUNT + INTEGER_TYPE + COMMA_SEP +
                    DbModel.Order.COLUMN_NAME_KIND + TEXT_TYPE + " )";

    private static final String SQL_CONTACT_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbModel.Contact.TABLE_NAME;

    private static final String SQL_ORDER_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbModel.Order.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CONTACT_CREATE_ENTRIES);
        db.execSQL(SQL_ORDER_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_CONTACT_DELETE_ENTRIES);
        db.execSQL(SQL_ORDER_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
