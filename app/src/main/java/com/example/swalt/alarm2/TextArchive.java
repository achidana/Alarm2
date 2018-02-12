package com.example.swalt.alarm2;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
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
    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, textMessages);
        setListAdapter(adapter);

        //initialize();
    }

    private void initialize() {
        String template0 = "I overslept!";
        String template1 = "Can you come wake me up?";
        String template2 = "Can you call me to wake me up?";

        textMessages.add(template0);
        textMessages.add(template1);
        textMessages.add(template2);
    }

    public void addItems(View v) {
        textMessages.add("clicked: " + clickCount++);
        adapter.notifyDataSetChanged();
    }

    public void addMessage(String message) {
        textMessages.add(message);
    }

    public void removeMessage(String message) {
        textMessages.remove(message);
    }

    public void editMessage(String oldMessage, String newMessage) {
        textMessages.remove(textMessages.indexOf(oldMessage));
        textMessages.add(newMessage);

    }
}
