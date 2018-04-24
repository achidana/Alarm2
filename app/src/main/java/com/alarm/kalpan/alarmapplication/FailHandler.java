package com.alarm.kalpan.alarmapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FailHandler implements Runnable {
    private TimeAlarm myAlarm;
    private int timeInSeconds;
    public FailHandler(int timeInSeconds, TimeAlarm myAlarm)
    {
        this.myAlarm = myAlarm;
        this.timeInSeconds = timeInSeconds;
        System.out.println("TAG in fail handler");
    }
    @Override
    public void run() {
        try
        {
            Thread.sleep(timeInSeconds * 1000);
        }

        catch(InterruptedException e)
        {
            return;
        }

        String text;
        ArrayList<String> phoneNumbers;

        System.out.println("TAG " + myAlarm.getIsCall() + " " + myAlarm.getIsText());
        if(myAlarm.getIsCall() || myAlarm.getIsText())
        {
            phoneNumbers = myAlarm.getNumbersToNotify();
            text = myAlarm.getTextMessage();
            System.out.println("TAG " + myAlarm.getTextMessage());
            text = text.replace("?", "%3f");
            text = text.replace(" ", "%20");
            for(String phoneNumber : phoneNumbers)
            {
                phoneNumber = phoneNumber.replace("-", "");
                phoneNumber = phoneNumber.replace(" ", "");
                phoneNumber = phoneNumber.replace("(", "");
                phoneNumber = phoneNumber.replace(")", "");
                if(myAlarm.getIsText())
                {
                    URL url;
                    HttpURLConnection conn;
                    StringBuilder result = new StringBuilder();
                    try
                    {
                        url = new URL("http://45.56.125.90:5000/" + "text/" + phoneNumber + "/" + text);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.setRequestMethod("GET");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while((line = rd.readLine()) != null)
                        {
                            result.append(line);
                        }

                        conn.disconnect();
                    }

                    catch (MalformedURLException e)
                    {
                        url = null;
                    }

                    catch(IOException e)
                    {
                        conn = null;
                    }
                }

                if(myAlarm.getIsCall())
                {
                    URL url;
                    HttpURLConnection conn;
                    StringBuilder result = new StringBuilder();
                    try
                    {
                        url = new URL("http://45.56.125.90:5000/" + "call/" + phoneNumber + "/" + text);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.setRequestMethod("GET");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while((line = rd.readLine()) != null)
                        {
                            result.append(line);
                        }

                        conn.disconnect();
                    }

                    catch (MalformedURLException e)
                    {
                        url = null;
                    }

                    catch(IOException e)
                    {
                        conn = null;
                    }
                }
            }

        }
    }
}
