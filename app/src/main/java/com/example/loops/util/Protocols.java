/**
 * File: Protocols.java
 * Description: Defines the protocols for the client server interface.
 * Authors: Gnandeep, Snchit, Aahish, Mehul.
 */
package com.example.loops.util;

/**
 * Defining the protocols for the server
 */
public interface Protocols {

    /**
     * From: Client
     * To: Server
     * Format: "CONNECT"_"USR"
     * Informs the server that the client wants to get connected to the server
     */
    public static final String WELCOME = "WELCOME";

    /**
     * From: server
     * To: Client
     * Format: "AUTH"_"UID"_"USR"
     * Informs the client that he has been authenticated if he/she logs in again
     */
    public static final String AUTHENTICATED = "AUTHENTICATED";

    /**
     * From: client
     * To: Server
     * Format: "SEND"_"USR"_"MSG"
     * Requests the server to send the message
     */
    public static String SEND = "SEND";

    /**
     * From : server
     * To : client
     * Format: "RECIEVE"_"MSG"
     * Notifies the client that he/she received a new loop
     */
    public static String RECEIVE = "RECEIVE";

    /**
     * From: server
     * To: client
     * Format: "LOOPCOMP"_"VAL"_"USR"
     * Notifies the clients that the loop is complete
     */
    public static String LOOP_COMPLETE = "LOOP_COMPLETE";

    /**
     * From: client
     * To: Server
     * Format: "DISCONNECT"_"USR"
     * Informs the server it wants to disconnect and go offline
     */
    public static final String DISCONNECT = "DISCONNECT";
}
