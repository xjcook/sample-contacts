package io.xjhub.samplecontacts;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ContactCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    ContactCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate the layout for each row
        return mInflater.inflate(R.layout.fragment_main_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        ImageView icPlaceholder = (ImageView) view.findViewById(R.id.icPlaceholder);

        String title = cursor.getString(cursor.getColumnIndex(DbModel.Contact.COLUMN_NAME_TITLE));
        String phone = cursor.getString(cursor.getColumnIndex(DbModel.Contact.COLUMN_NAME_PHONE));
        String pictureUrl = cursor.getString(cursor.getColumnIndex(DbModel.Contact.COLUMN_NAME_PICTURE_URL));

        tvName.setText(title);
        tvPhone.setText(phone);

        // Custom image if contact has picture,
        // otherwise show placeholder.
        if (pictureUrl != null) {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex(DbModel.Contact.COLUMN_NAME_PICTURE));
            if (blob != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                icPlaceholder.setImageBitmap(bitmap);
            } else {
                icPlaceholder.setImageResource(R.drawable.ic_placeholder);
            }
        } else {
            icPlaceholder.setImageResource(R.drawable.ic_placeholder);
        }
    }

}
