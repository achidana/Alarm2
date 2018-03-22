package com.example.swalt.alarm2;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import java.lang.Object;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ViewAnimator;

import java.util.ArrayList;

/**
 * Created by JohnRedmon on 2/11/18.
 * Contains Default text messages to be used for phone a friend
 */

public class TextArchive extends Activity {

    private ArrayList<String> textMessages = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private String userText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, textMessages);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        initialize();
        initializeListener();
    }

    private void initialize() {
        String template0 = "I overslept!";
        String template1 = "Can you come wake me up?";
        String template2 = "Can you call me to wake me up?";

        textMessages.add(template0);
        adapter.notifyDataSetChanged();
        textMessages.add(template1);
        adapter.notifyDataSetChanged();
        textMessages.add(template2);
        adapter.notifyDataSetChanged();
    }

    //Called when button is pressed
    public void addItems(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Message");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        //OK is pressed, opens editText and adds to adapter
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userText = input.getText().toString();
                textMessages.add(userText);
                adapter.notifyDataSetChanged();
            }
        });

        //Cancel is pressed, closes the box without updating list
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    //Initilializes onClick functionality of listView
    public void initializeListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TextArchive.this);
                builder.setTitle("Edit or Delete?");
                //toRemove is the index of the item in the list to be removed/editted
                final int toRemove = i;

                //Delete is pressed, removes item from list
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textMessages.remove(toRemove);
                        adapter.notifyDataSetChanged();
                    }
                });

                //Edit is pressed, calls seperate function editItem
                //TODO: Check editting of default values/cancel
                builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String editText = textMessages.get(toRemove);
                        editItem(view, editText, toRemove);
                        adapter.notifyDataSetChanged();
                    }
                });

                //Select is pressed, closes dialog box and returns String to caller
                builder.setNegativeButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent data = new Intent();
                        data.putExtra("theText", textMessages.get(toRemove));
                        setResult(RESULT_OK, data);
                        dialogInterface.cancel();
                        //Sends selected message back to main alarm page
                        finish();
                    }
                });
                builder.show();
            }
        });
    }

    //Similar structure to addItem, called when edit is pressed
    public void editItem(View v, final String current, final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Message");

        //Opens editText with previous message already loaded
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(current);
        builder.setView(input);

        //OK is pressed, adds new message
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userText = input.getText().toString();
                textMessages.set(index, userText);
                adapter.notifyDataSetChanged();
            }
        });
        //Cancel is pressed, ignores changes made and restores original message to list
        //TODO: Check that cancel doesn't add a new entry
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textMessages.add(current);
                adapter.notifyDataSetChanged();
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
