package com.example.swalt.alarm2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by JohnRedmon on 2/20/18.
 */

public class AudioArchive extends Activity{
    private ArrayList<String> audioMessages = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private String userText = "";
    private final int REQUEST_CODE_RECORD = 1005;
    private String audioFileLocation = "";
    private MediaPlayer mp = new MediaPlayer();

    private Button recordButton;
    private Button stopButton;

    private MediaRecorder mediaRecorder;
    String voiceStoragePath;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_archive);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, audioMessages);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        initialize();
        //initializeListener();
    }

    private void initialize() {
        String name0 = "Bells";
        String name1 = "John's Voice";

        int rawID0 = getResources().getIdentifier("bell", "raw", getPackageName());
        MediaPlayer mp0 = MediaPlayer.create(this, rawID0);

        int rawID1 = getResources().getIdentifier("skylevelup", "raw", getPackageName());
        MediaPlayer mp1 = MediaPlayer.create(this, rawID1);

        AudioMessage am0 = new AudioMessage(name0, mp0);
        AudioMessage am1 = new AudioMessage(name1, mp1);

        audioMessages.add(am0.getName());
        adapter.notifyDataSetChanged();
        audioMessages.add(am1.getName());
        adapter.notifyDataSetChanged();

        //For when called to be played, test for now
        //mp0.start();
    }

    //Just adding text item, still working on grabbing audio from dialog vs. opening new activity
    public void addItems(View v) {
        Intent intent = new Intent(this, testRecordAudio.class);
        startActivityForResult(intent, REQUEST_CODE_RECORD);
        Log.d("postIntent", "finish intent");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Voice Message");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        //OK is pressed, opens editText and adds to adapter
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userText = input.getText().toString();
                try {
                    mp.setDataSource(audioFileLocation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AudioMessage newAM = new AudioMessage(userText, mp);
                audioMessages.add(newAM.getName());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_RECORD) {
            audioFileLocation = data.getDataString();
        }
    }

}
