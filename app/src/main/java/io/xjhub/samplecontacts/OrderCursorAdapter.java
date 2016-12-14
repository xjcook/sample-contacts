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

class OrderCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    OrderCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate the layout for each row
        return mInflater.inflate(R.layout.fragment_detail_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvCount = (TextView) view.findViewById(R.id.tvCount);

        String title = cursor.getString(cursor.getColumnIndex(DbModel.Order.COLUMN_NAME_TITLE));
        String count = cursor.getString(cursor.getColumnIndex(DbModel.Order.COLUMN_NAME_COUNT));

        tvName.setText(title);
        tvCount.setText(count);
    }

}
