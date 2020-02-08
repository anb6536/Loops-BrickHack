package com.example.loops.server;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.loops.util.Duplexer;
import com.example.loops.util.Protocols;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


class ClientHandler extends Thread implements Protocols
{
    private Duplexer server;

    private ArrayList<ClientHandler> handlers;

    private String uid;

    private int flag;   //0 for offline, 1 for online


    // Constructor
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ClientHandler(Socket s, ArrayList<ClientHandler> handlers) throws IOException

    {
        Duplexer server = new Duplexer(s);
        this.server = server;
        this.handlers = handlers;
        this.flag = 1;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run()
    {
        String received;
        while (true)
        {
            received = this.server.read();
            if (received.equals(CONNECT)){
                String[] token = received.split(" ");
                String user = token[1];
                this.uid = user;
                int number = user.hashCode();
                server.send(AUTHENTICATED+" "+Integer.toString(number)+" "+user);
            }
            else if(received.equals(SEND)){
                String msg = "";
                String[] token = received.split(" ");
                String usr = token[1];
                for (int i = 2; i<token.length-1; i++){
                    msg += token[i];
                }
                for (ClientHandler handler : this.handlers){
                    if (handler.uid.equals(usr)){
                        handler.server.send(RECEIVE+ " "+ msg);
                    }
                }

            }
            else if(received.equals(DISCONNECT)){
                String[] token = received.split(" ");
                String usr = token[1];
                for (ClientHandler handler: this.handlers){
                    if (handler.uid.equals(usr)) {
                        handler.flag = 0;
                    }
                }
            }
        }
    }
}