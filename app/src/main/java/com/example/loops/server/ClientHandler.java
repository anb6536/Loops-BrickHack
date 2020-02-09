package com.example.loops.server;

import android.os.Build;
import com.example.loops.util.Duplexer;
import com.example.loops.util.Protocols;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import androidx.annotation.RequiresApi;
class ClientHandler extends Thread implements Protocols {
    public Duplexer duplexer;
    private ArrayList<String> contacts;
    private String username;
    private HashMap<Integer,Loop> loops;
    private Game game;
    private int numberOfLoops;
    // Constructor
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ClientHandler(Duplexer duplexer,String username, Game game) throws IOException {
        this.duplexer=duplexer;
        this.contacts=new ArrayList<>();
        this.username=username;
        this.loops=new HashMap<>();
        this.game=game;
        this.numberOfLoops=0;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {
        /**
         * CONNECT
         * AUTHENTICATED
         * SEND
         * RECIEVE
         * LOOP_COMPLETE
         * DISCONNECT
         */
        while (true){
            String message=this.duplexer.read();
            ArrayList<String> messages=new ArrayList<>();
            messages.addAll(Arrays.asList(message.split(" ")));
            switch (messages.get(0)) {
                case SEND:
                    String sendTo = messages.get(1);
                    String msg = "";
                    String loopID = messages.get(2); // extracting the loopID from the message
                    for (int i = 4; i < messages.size(); i++) {
                        msg = msg + " " + messages.get(i);
                    }
                    // Sending the message
                    // updating the loop
                    String sendMessage=RECEIVE+" "+msg;
                    int sendToKey=sendTo.hashCode();

                    if(game.loops.get(loopID)==null){
                        if(numberOfLoops<3){
                            int newLoopID=game.getNewLoopID(username,numberOfLoops);
                            Loop loop=new Loop(newLoopID);
                            loop.setCreator(username);
                            loop.addMember(username);
                            loop.addMember(sendTo);
                            loop.addMessage(sendMessage,username);
                        }
                        else{ // when the maximum number of loops have been reached
                            game.clients.get(username).duplexer.send(MAX_LOOP);
                            continue;
                        }
                    }
                    else{
                        // end Loop case implemented
                        game.loops.get(loopID).addMessage(sendMessage, username);
                        if (game.loops.get(loopID).userExists(sendTo)){
                            int index = game.loops.get(loopID).members.indexOf(sendTo);
                            game.endLoop(game.loops.get(loopID).members, index);
                        }
                        else {
                            game.loops.get(loopID).addMember(sendTo);
                        }
                    }
                    // sending the actual message
                    if(game.clients.get(sendToKey)!=null){
                        game.clients.get(sendToKey).duplexer.send(sendMessage);
                    }
                    // completing the loop logic
                    break;
                case DISCONNECT:
                    // removing the player since it is going offline
                    game.clients.remove(username.hashCode());
                    interrupt();
            }
        }
    }
}