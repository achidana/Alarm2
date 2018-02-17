package com.example.swalt.alarm2;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
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
 * Include customization? Double check sprint doc
 */

public class TextArchive extends ListActivity {
    private ArrayList<String> textMessages = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listView = new ListView(this);
    private String userText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, textMessages);
        listView = (ListView)findViewById(R.id.textList);
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

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userText = input.getText().toString();
                textMessages.add(userText);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public void initializeListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TextArchive.this);
                builder.setTitle("Edit or Delete?");
                final int toRemove = i;

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textMessages.remove(toRemove);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String editText = textMessages.get(i);

                        final EditText newInput = new EditText(TextArchive.this);
                        newInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        newInput.setHint(editText);
                        String edittedText = newInput.getText().toString();

                        textMessages.remove(toRemove);
                        adapter.notifyDataSetChanged();
                        textMessages.add(edittedText);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
    }
}
