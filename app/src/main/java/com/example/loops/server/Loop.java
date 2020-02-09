package com.example.loops.server;

import java.util.ArrayList;

public class Loop {
    public static ArrayList<String> members;
    private static ChatBox chatBox;
    private int id;
    private int score;
    private static String creator;

    public Loop(int id){
        this.id=id;
        this.score=0;
        this.creator="";
        this.chatBox=new ChatBox();
    }

    public static void setCreator(String username){
        creator=username;
    }

    public static String getCreator(){
        return creator;
    }

    public static void addMessage(String message, String username){
        chatBox.addChat(message,username);
    }
    public static void addMember(String username){
        members.add(username);
    }

    public static boolean userExists(String username){
        return members.contains(username);
    }
}
