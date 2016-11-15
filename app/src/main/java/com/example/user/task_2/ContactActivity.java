package com.example.user.task_2;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    ImageButton call;
    ImageButton sms;
    ImageButton mailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_contact);

        Contact contact = (Contact) getIntent().getSerializableExtra("contact");

        ImageView contactIcon = (ImageView) findViewById(R.id.contact_info_icon);
        TextView contactName = (TextView) findViewById(R.id.contact_info_name);
        TextView phone = (TextView) findViewById(R.id.contact_info_numbers);
        TextView mail = (TextView) findViewById(R.id.contact_info_emails);

        dropButtons();

        Log.i("icon", contact.getIconString());

        if (!contact.getIconString().equals("default"))
            contactIcon.setImageURI(Uri.parse(contact.getIconString()));
        else contactIcon.setImageResource(R.drawable.contact_default);

        contactName.setText(contact.getName());

        ArrayList<String> numberList = contact.getNumbers();
        ArrayList<String> mailList = contact.getEmails();

        addItemList(numberList, phone, "phone");
        addItemList(mailList, mail, "mail");


        


    }

    private void addItemList(ArrayList<String> list, TextView textView, String type){
        if (list.size() == 0) {
            switch (type){
                case "phone":
                    textView.setText(R.string.empty_num);

                    call.setEnabled(false);
                    sms.setEnabled(false);
                    call.getBackground().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.MULTIPLY);
                    sms.getBackground().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.MULTIPLY);

                    break;
                case "mail":
                    textView.setText(R.string.empty_mail);

                    mailBtn.setEnabled(false);
                    mailBtn.getBackground().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.MULTIPLY);
            }

        }
        else if (list.size() == 1){
            textView.setText(list.get(0));
        } else {
            textView.setText(list.get(0));
            for (int i = 1; i < list.size(); i++) {
                textView.append("\n" + list.get(i));
            }
        }
        textView.append("\n");
    }


    private void dropButtons(){
        call = (ImageButton) findViewById(R.id.call);
        sms = (ImageButton) findViewById(R.id.sms);
        mailBtn = (ImageButton) findViewById(R.id.mail);

        call.getBackground().clearColorFilter();
        sms.getBackground().clearColorFilter();
        mailBtn.getBackground().clearColorFilter();

        call.setEnabled(true);
        sms.setEnabled(true);
        sms.setEnabled(true);
    }

}
