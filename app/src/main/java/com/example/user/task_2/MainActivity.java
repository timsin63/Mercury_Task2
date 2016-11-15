package com.example.user.task_2;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private static final int CONTACT_ID_INDEX = 0;
    private static final int LOOKUP_KEY_INDEX = 1;


    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_URI
    };

    private final static int[] TO_IDS = {
            R.id.contact_name,
            R.id.contact_icon
    };

    CursorAdapter cursorAdapter;
    CursorAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);


        adapter = new ContactListAdapter(this, R.layout.i_contact, null, FROM_COLUMNS, TO_IDS, 0);

        ListView list = (ListView) findViewById(R.id.contact_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        getLoaderManager().initLoader(0, null, this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Uri contentUri = ContactsContract.Contacts.CONTENT_URI;

        return new CursorLoader(this, contentUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View item, int position, long rowID){
        Cursor cursor = (Cursor) parent.getAdapter().getItem(0);

        cursor.moveToPosition(position);

        Contact contact = getItem(cursor);

        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }


    public Contact getItem(Cursor cursor){
        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
        if (photo == null)
            photo = "default";

        String hasPhoneNumbers = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();

        if (Integer.parseInt(hasPhoneNumbers) > 0){
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phones.moveToNext()) {
                numbers.add(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            }

            phones.close();
        }

        Cursor eMailsCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
        while (eMailsCursor.moveToNext()){
            emailList.add(eMailsCursor.getString(eMailsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
        }


        Log.i("contact name:", name);
        Log.i("contact photo:", photo);

        for (int i = 0; i < numbers.size(); i++) {
            Log.i("contact number " + i + ":", numbers.get(i));
        }
        for (int i = 0; i < emailList.size(); i++) {
            Log.i("contact email " + i + ":", emailList.get(i));
        }

        return new Contact(contactId, name, photo, numbers, emailList);
    }
}
