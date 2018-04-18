package com.alarm.kalpan.alarmapplication;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GetInfo extends Activity {

    ArrayList<String> phoneNumbers;
    ArrayAdapter<String> phoneNumberToViewAdapter;
    int maxNumbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);

        phoneNumbers = new ArrayList<>();

        final ListView list = findViewById(R.id.listView2);
        Button okButton = findViewById(R.id.OK);
        Button add = findViewById(R.id.addNumber);
        Button cancel = findViewById(R.id.cancelButton);
        Button delete = findViewById(R.id.deleteButton2);

        phoneNumberToViewAdapter = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_list_item_1, phoneNumbers);
        Globals globals = (Globals) getApplication();
        maxNumbers = globals.maxBackupNumbers;

        list.setAdapter(phoneNumberToViewAdapter);
        okButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent dataIntent = new Intent();

                // put the array list as a
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("PhoneNumbers", phoneNumbers);

                dataIntent.putExtra(getString(R.string.app_name) + ".PhoneNumbers", bundle);
                setResult(RESULT_OK, dataIntent);   //set the ok status, and the dataIntent to be sent when finished
                finish();
            }
        });

        add.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(phoneNumbers.size() == maxNumbers)
                {
                    Toast.makeText(getApplicationContext(), "No more", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent phoneAppIntent = new Intent();
                phoneAppIntent.setAction(Intent.ACTION_PICK);
                phoneAppIntent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(phoneAppIntent, 1);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //indicate to the calling activity that cancel was pressed by saying RESULT_CANCELED
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = list.getCheckedItemPosition();
                if(position < 0)
                {
                    return;
                }

                phoneNumbers.remove(position);
                phoneNumberToViewAdapter.notifyDataSetChanged();
                list.setItemChecked(position, false);
                list.setSelection(-1);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("Flag 6");
                list.setItemChecked(position, true);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == 1)
            {
                String phoneNumber;

                ContentResolver contentResolver = getContentResolver();

                Uri uri = data.getData();
                Cursor cursor = contentResolver.query(data.getData(), null, null, null, null);

                //mvocing cursor to the first and only row in the table (but we still need to move it there)
                cursor.moveToFirst();
                phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("data1"));

                phoneNumberToViewAdapter.add(phoneNumber);
                cursor.close();
            }
        }
    }
}
