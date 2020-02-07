package com.example.loops.util;


public interface Protocols {
    /**
     * Defining the protocols for the server
     */


    /**
     * From: server
     * To: client
     * Informs the client that he/she is connected to the server
     */
    public static final String WELCOME = "WELCOME";

    /**
     * From: client
     * To: Server
     * Requests the server to send the message
     */
    public static String SEND = "SEND";

    /**
     * From : server
     * To : client
     * Notifies the client that he/she received a new loop
     */
    public static String RECEIVE = "RECEIVE";
}
