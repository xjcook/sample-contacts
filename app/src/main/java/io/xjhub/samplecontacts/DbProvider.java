package io.xjhub.samplecontacts;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import static io.xjhub.samplecontacts.DbModel.AUTHORITY;

public class DbProvider extends ContentProvider {

    private static final String TAG = "DbProvider";

    static final int CONTACTS = 1;
    static final int CONTACT_ID = 2;
    static final int ORDERS = 3;
    static final int ORDER_ID = 4;

    static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private DbHelper mDbHelper;
    private SQLiteDatabase db;

    static {
        // multiple rows
        sUriMatcher.addURI(AUTHORITY, DbModel.Contact.TABLE_NAME, CONTACTS);
        sUriMatcher.addURI(AUTHORITY, DbModel.Order.TABLE_NAME, ORDERS);

        // single row
        sUriMatcher.addURI(AUTHORITY, DbModel.Contact.TABLE_NAME + "/#", CONTACT_ID);
        sUriMatcher.addURI(AUTHORITY, DbModel.Order.TABLE_NAME + "/#", ORDER_ID);
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

            case ORDERS:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + DbModel.Order.TABLE_NAME;

            case CONTACT_ID:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + "." + DbModel.Contact.TABLE_NAME;

            case ORDER_ID:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + "." + DbModel.Order.TABLE_NAME;

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

        String table;

        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
                table = DbModel.Contact.TABLE_NAME;
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DbModel.Contact._ID + " ASC";
                }
                break;

            case ORDERS:
                table = DbModel.Order.TABLE_NAME;
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DbModel.Order._ID + " ASC";
                }
                break;

            case CONTACT_ID:
                table = DbModel.Contact.TABLE_NAME;
                selection = selection + DbModel.Contact._ID + " = " + uri.getLastPathSegment();
                break;

            case ORDER_ID:
                table = DbModel.Order.TABLE_NAME;
                selection = selection + DbModel.Order._ID + " = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Make query
        db = mDbHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(table);

        Cursor cursor = qb.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        // Register to watch a content URI for changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        db = mDbHelper.getWritableDatabase();
        String table;
        Uri contentUri;

        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
            case CONTACT_ID:
                table = DbModel.Contact.TABLE_NAME;
                contentUri = DbModel.Contact.CONTENT_URI;
                break;

            case ORDERS:
            case ORDER_ID:
                table = DbModel.Order.TABLE_NAME;
                contentUri = DbModel.Order.CONTENT_URI;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        long rowId = db.insert(table, null, contentValues);

        if (rowId > 0) {
            Uri _uri = ContentUris.withAppendedId(contentUri, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        Log.e(TAG, "Failed to add a record into " + uri);
        return null;
    }

    @Override
    public int update(
            Uri uri,
            ContentValues contentValues,
            String selection,
            String[] selectionArgs) {

        db = mDbHelper.getWritableDatabase();
        String table;

        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
                table = DbModel.Contact.TABLE_NAME;
                break;

            case ORDERS:
                table = DbModel.Order.TABLE_NAME;
                break;

            case CONTACT_ID:
                table = DbModel.Contact.TABLE_NAME;
                selection = selection + DbModel.Contact._ID + " = " + uri.getLastPathSegment();
                break;

            case ORDER_ID:
                table = DbModel.Order.TABLE_NAME;
                selection = selection + DbModel.Order._ID + " = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        int count = db.update(table, contentValues, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = mDbHelper.getWritableDatabase();
        String table;

        switch (sUriMatcher.match(uri)) {
            case CONTACTS:
                table = DbModel.Contact.TABLE_NAME;
                break;

            case ORDERS:
                table = DbModel.Order.TABLE_NAME;
                break;

            case CONTACT_ID:
                table = DbModel.Contact.TABLE_NAME;
                selection = selection + DbModel.Contact._ID + " = " + uri.getLastPathSegment();
                break;

            case ORDER_ID:
                table = DbModel.Order.TABLE_NAME;
                selection = selection + DbModel.Order._ID + " = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        int count = db.delete(table, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}
