package com.example.loops.server;

import java.util.ArrayList;

public class Game implements Runnable{
    private static ArrayList<ClientHandler> clients;

    public Game(){
        this.clients=new ArrayList<>();
    }

    public static void addClient(ClientHandler client){
        clients.add(client);
    }
    @Override
    public void run() {

    }
}
