package com.example.loops.server;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.loops.util.Duplexer;
import com.example.loops.util.Protocols;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class ClientHandler extends Thread implements Protocols
{
    private Duplexer server;


    // Constructor
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ClientHandler(Socket s) throws IOException

    {
        Duplexer server = new Duplexer(s);
        this.server = server;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run()
    {
        String received;
        while (true)
        {
            received = this.server.read();
            if (received.equals(WELCOME)){
                String[] token = received.split(" ");
                //pass the token[1] to the db handler so that it checks adds it into the array list.
                String user = token[1];
                int number = user.hashCode();
                server.send(AUTHENTICATED+" "+Integer.toString(number)+" "+user);
            }
            else if(received.equals(SEND)){

            }
        }
    }
}