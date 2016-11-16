package com.example.user.task_2;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_SENDTO;
import static android.content.Intent.ACTION_VIEW;

public class ContactActivity extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout numberContainer;
    LinearLayout mailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_contact);

        Contact contact = (Contact) getIntent().getSerializableExtra("contact");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView contactIcon = (ImageView) findViewById(R.id.contact_info_icon);
        TextView contactName = (TextView) findViewById(R.id.contact_info_name);


        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        numberContainer = (LinearLayout) findViewById(R.id.number_container);
        mailContainer = (LinearLayout) findViewById(R.id.mail_container);


        if (!contact.getIconString().equals("default"))
            contactIcon.setImageURI(Uri.parse(contact.getIconString()));
        else contactIcon.setImageResource(R.drawable.contact_default);

        contactName.setText(contact.getName());

        final ArrayList<String> numberList = contact.getNumbers();
        ArrayList<String> mailList = contact.getEmails();

        addItemList(numberList, "phone");
        addItemList(mailList, "mail");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); 
        }

        return super.onOptionsItemSelected(item);
    }


    private void addItemList(final ArrayList<String> list, String type){
        if (list.size() == 0) {
            switch (type){
                case "phone":
                    TextView  textView = new TextView(this);
                    textView.setText(R.string.empty_num);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.contact_text_size));
                    numberContainer.addView(textView);
                    break;
                case "mail":
                    TextView  textMailView = new TextView(this);
                    textMailView.setText(R.string.empty_mail);
                    textMailView.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.contact_text_size));
                    mailContainer.addView(textMailView);
            }

        }
        else {
            for (int i = 0; i < list.size(); i++) {
                switch (type) {
                    case "phone":
                        View childView = inflater.inflate(R.layout.i_number, numberContainer, false);
                        TextView numberView = (TextView) childView.findViewById(R.id.phone_number);
                        final String number = list.get(i);
                        numberView.setText(number);
                        numberContainer.addView(childView);

                        numberContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "Call", Toast.LENGTH_SHORT).show();
                                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                                startActivity(callIntent);
                            }
                        });
                        ImageButton sendSmsBtn = (ImageButton) childView.findViewById(R.id.sms_btn);
                        sendSmsBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent smsIntent = new Intent(ACTION_VIEW, Uri.fromParts("sms", number, null));
                                startActivity(smsIntent);
                            }
                        });
                        break;
                    case "mail":
                        View childMailView = inflater.inflate(R.layout.i_mail, mailContainer, false);
                        TextView address = (TextView) childMailView.findViewById(R.id.mail_address);
                        final String mailAddress = list.get(i);
                        address.setText(list.get(i));
                        mailContainer.addView(childMailView);

                        mailContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent mailIntent = new Intent(ACTION_SENDTO);

                                mailIntent.setData(Uri.parse("mailto:" + mailAddress));

                                startActivity(Intent.createChooser(mailIntent, "Send mail"));
                            }
                        });
                        break;
                }
            }
        }
    }

}
