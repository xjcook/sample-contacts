package io.xjhub.samplecontacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class ContactListAdapter extends ArrayAdapter<Api.Contact> {

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView phone;
    }

    ContactListAdapter(Context context, List<Api.Contact> contactList) {
        super(context, R.layout.fragment_main_row, contactList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Api.Contact contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_main_row, parent, false);
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
        viewHolder.name.setText(contact.name);
        viewHolder.phone.setText(contact.phone);
        // Return the completed view to render on screen
        return convertView;
    }

}
