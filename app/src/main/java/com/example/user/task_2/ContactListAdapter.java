package com.example.user.task_2;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by User on 11/10/2016.
 */

public class ContactListAdapter extends SimpleCursorAdapter {

    LayoutInflater cursorInflater;
    private LoaderManager loaderManager;
    private Context context;
    Cursor cursor;
    String from[];

    public ContactListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.cursor = c;
        this.from = from;
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View view = cursorInflater.inflate(R.layout.i_contact, viewGroup, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView contactNameView = (TextView) view.findViewById(R.id.contact_name);
        ImageView contactIconView = (ImageView) view.findViewById(R.id.contact_icon);

        String iconString = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));


        contactNameView.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

        if (iconString != null)
            contactIconView.setImageURI(Uri.parse(iconString));
        else contactIconView.setImageResource(R.drawable.contact_default);

    }


}
