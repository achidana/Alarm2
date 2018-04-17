package com.alarm.kalpan.alarmapplication;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetInfo extends Activity {

    String phoneNumber;
    EditText phoneNumberText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);



        phoneNumberText = (EditText) findViewById(R.id.editTextPhoneNumber);
        Button okButton = findViewById(R.id.OK);
        Button selectContact = findViewById(R.id.selectContact);

        okButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent dataIntent = new Intent();
                phoneNumber = phoneNumberText.getText().toString();
                dataIntent.putExtra("theNumber", phoneNumber);
                setResult(RESULT_OK, dataIntent);   //set the ok status, and the dataIntent to be sent when finished
                finish();
            }
        });

        selectContact.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent phoneAppIntent = new Intent();
                phoneAppIntent.setAction(Intent.ACTION_PICK);
                phoneAppIntent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(phoneAppIntent, 1);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == 1)
            {

                String n = "7656378554";
                phoneNumberText.setText(n.toCharArray(), 0, n.length());
                ContentResolver contentResolver = getContentResolver();

                Uri uri = data.getData();
                Cursor cursor = contentResolver.query(data.getData(), null, null, null, null);

                //mvocing cursor to the first and only row in the table (but we still need to move it there)
                cursor.moveToFirst();
                phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("data1"));
                System.out.println("Phone number is " + phoneNumber);
                phoneNumberText.setText(phoneNumber);
                cursor.close();
            }
        }
    }
}
