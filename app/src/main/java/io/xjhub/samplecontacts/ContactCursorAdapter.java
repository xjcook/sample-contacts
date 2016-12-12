package io.xjhub.samplecontacts;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

class ContactCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView phone;
    }

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
    public void bindView(View convertView, Context context, Cursor cursor) {
        // Get the data item for this position
        String title = cursor.getString(cursor.getColumnIndex(ContactModel.COLUMN_NAME_TITLE));
        String phone = cursor.getString(cursor.getColumnIndex(ContactModel.COLUMN_NAME_PHONE));

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.tvPhone);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(title);
        viewHolder.phone.setText(phone);
    }

}
