package io.xjhub.samplecontacts;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import static io.xjhub.samplecontacts.DbModel.AUTHORITY;
import static io.xjhub.samplecontacts.DbModel.CONTENT_URI;

public class ContactProvider extends ContentProvider {

    static final int CONTACTS = 1;
    static final int CONTACT_ID = 2;

    static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private DbHelper mDbHelper;
    private SQLiteDatabase db;

    static {
        // multiple rows
        sUriMatcher.addURI(AUTHORITY, DbModel.Contact.TABLE_NAME, 1);

        // single row
        sUriMatcher.addURI(AUTHORITY, DbModel.Contact.TABLE_NAME + "/#", 2);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + DbModel.Contact.TABLE_NAME;

            case CONTACT_ID:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + "." + DbModel.Contact.TABLE_NAME;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DbModel.Contact._ID + " ASC";
                }
                break;

            case CONTACT_ID:
                selection = selection + DbModel.Contact._ID + " = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Make query
        db = mDbHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DbModel.Contact.TABLE_NAME);

        Cursor cursor = qb.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        db = mDbHelper.getWritableDatabase();
        long rowId = db.insert(DbModel.Contact.TABLE_NAME, null, contentValues);

        if (rowId > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int update(
            Uri uri,
            ContentValues contentValues,
            String selection,
            String[] selectionArgs) {

        db = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
                break;

            case CONTACT_ID:
                selection = selection + DbModel.Contact._ID + " = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        int count = db.update(DbModel.Contact.TABLE_NAME, contentValues, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
                break;

            case CONTACT_ID:
                selection = selection + DbModel.Contact._ID + " = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        int count = db.delete(DbModel.Contact.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}
