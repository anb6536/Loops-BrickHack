package com.example.loops.server;

import android.os.Build;

import com.example.loops.util.Duplexer;
import com.example.loops.util.Protocols;
import java.io.*;
import java.net.*;
import java.util.HashMap;

import androidx.annotation.RequiresApi;

// Server class
public class server implements Protocols {
    public static HashMap<Integer,ClientHandler> clients;

    public server(){
        this.clients=new HashMap<>();
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);
        // running infinite loop for getting
        // client request

        Game game=new Game();
        game.run();
        while (true)
        {
            Socket s = null;
            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                System.out.println("A new client is connected : " + s);
                Duplexer duplexer= new Duplexer(s);
                String[] input=duplexer.read().split(" ");
                String username="";
                if(input[0].equals(CONNECT)){
                    username= input[1];
                }
                else {
                    // throw an error
                }
                int key=username.hashCode();
                ClientHandler client = new ClientHandler(duplexer,username,game);
                duplexer.send(AUTHENTICATED+" "+Integer.toString(key)); // sending the authentication message so that the user is able to login into the application
                if(!clients.containsKey(key)){
                    clients.put(key,client);
                }
                game.addClient(key,client); // maintains the list of clients who are online
                Thread t = client;
                // Invoking the start() method
                t.start();
                System.out.println("Assigning new thread for this client");
                // create a new thread object
                // adding the client in the loop so that the new player can be updated
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}