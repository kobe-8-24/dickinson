package com.htsc.netty.connection.per.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BasicModel implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("BasicModel start .......");
            ServerSocket ss = new ServerSocket(8080);
            while (!Thread.interrupted()) {
                new Thread(new Handler(ss.accept())).start();
            }
            //创建新线程来handle
            // or, single-threaded, or a thread pool
        } catch (IOException ex) {
            /* ... */
        }
    }
}

class Handler implements Runnable {
    final Socket socket;

    Handler(Socket s) {
        socket = s;
    }

    @Override
    public void run() {
        try {
            System.out.println("handle start......");
            byte[] input = new byte[1024];
            socket.getInputStream().read(input);
            byte[] output = process(input);
            socket.getOutputStream().write(output);
        } catch (IOException ex) {
            /* ... */
        }
    }

    private byte[] process(byte[] input) {
        byte[] output = null;
        /* ... */
        return output;
    }
}