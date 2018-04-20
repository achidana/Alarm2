package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by JohnRedmon on 2/20/18.
 * Stores the audio messages
 */

public class AudioArchive extends Activity{
    private ArrayList<String> audioMessages = new ArrayList<String>();
    private ArrayList<AudioMessage> amObjects = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private String userText = "";
    private final int REQUEST_CODE_RECORD = 1005;
    private String audioFileLocation = "";
    private MediaPlayer mp = new MediaPlayer();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_archive);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, audioMessages);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        //initialize();
        initializeListener();
    }

    //To be filled with default sounds
    private void initialize() {
        String name0 = "Bells";
        String name1 = "John's Voice";

        int rawID0 = getResources().getIdentifier("bell", "raw", getPackageName());
        MediaPlayer mp0 = MediaPlayer.create(this, rawID0);

        int rawID1 = getResources().getIdentifier("skylevelup", "raw", getPackageName());
        MediaPlayer mp1 = MediaPlayer.create(this, rawID1);

        AudioMessage am0 = new AudioMessage(name0, mp0);
        AudioMessage am1 = new AudioMessage(name1, mp1);

        amObjects.add(am0);
        amObjects.add(am1);

        audioMessages.add(am0.getName());
        adapter.notifyDataSetChanged();
        audioMessages.add(am1.getName());
        adapter.notifyDataSetChanged();
    }

    //Just adding text item, still working on grabbing audio from dialog vs. opening new activity
    public void addItems(View v) {
        boolean startRecord = true;
        try {
            MediaRecorder mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Enable Microphone for Alarm^2 in Apps->Settings", Toast.LENGTH_LONG);
            toast.show();
            startRecord = false;
        }
        if (startRecord) {
            Intent intent = new Intent(this, TestRecordAudio.class);
            startActivityForResult(intent, REQUEST_CODE_RECORD);


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
                        Log.d("TAG", audioFileLocation);
                    } catch (Exception e) {
                        Log.d("TAG", "failure");
                        e.printStackTrace();
                    }
                    AudioMessage newAM = new AudioMessage(userText, mp);
                    amObjects.add(newAM);
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
    }

    //Initilializes onClick functionality of listView
    public void initializeListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AudioArchive.this);
                builder.setTitle(audioMessages.get(i));
                //toRemove is the index of the item in the list to be removed/editted
                final int toRemove = i;

                //Delete is pressed, removes item from list
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        amObjects.remove(toRemove);
                        audioMessages.remove(toRemove);
                        adapter.notifyDataSetChanged();
                    }
                });


                //Play is pressed, calls seperate function editItem
                builder.setNeutralButton("Play", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println(amObjects.get(toRemove).getName());
                        //System.out.println(amObjects.get(toRemove).getMediaPlayer());
                        try {
                            amObjects.get(toRemove).getMediaPlayer().prepare();
                        } catch (IOException e) {
                            System.out.println("IO");
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            System.out.println("Illegal State");
                            e.printStackTrace();
                        }
                        amObjects.get(toRemove).getMediaPlayer().start();
                    }
                });


                //Select is pressed, closes dialog box and returns data to caller
                builder.setNegativeButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent data = new Intent();
                        data.putExtra("name", amObjects.get(toRemove).getName());
                        //Filepath, would have to set the mp3 player again in the caller
                        data.putExtra("filepath",audioFileLocation);
                        //data.getParcelableExtra("mp3", amObjects.get(toRemove).getMediaPlayer());
                        dialogInterface.cancel();
                        setResult(RESULT_OK, data);
                        finish();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_RECORD) {
            audioFileLocation = data.getStringExtra("SavedLocation");
            Log.d("TAG", audioFileLocation);
        }
    }

}
