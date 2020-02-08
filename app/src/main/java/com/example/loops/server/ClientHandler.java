package com.example.loops.server;

import android.os.Build;

import com.example.loops.util.Duplexer;
import com.example.loops.util.Protocols;

import java.io.IOException;

import androidx.annotation.RequiresApi;

class ClientHandler extends Thread implements Protocols {
    private Duplexer duplexer;
    // Constructor
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ClientHandler(Duplexer duplexer) throws IOException {
        this.duplexer=duplexer;
    }

    @Override
    public void run() {
        while (true){

        }
    }
}