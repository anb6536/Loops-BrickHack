package com.example.loops.server;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// Server class
public class server {



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);

        ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

        // running infinite loop for getting
        // client request
        while (true)
        {


            try
            {
                // socket object to receive incoming client requests
                Socket client = ss.accept();

                System.out.println("A new client is connected : " + client);
                ClientHandler clientHandler = new ClientHandler(client);
                clientHandlers.add(clientHandler);

                // obtaining input and out streams

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = clientHandler;

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}