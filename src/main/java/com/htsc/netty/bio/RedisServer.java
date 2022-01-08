package com.htsc.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            Socket socket = serverSocket.accept();
            byte[] b = new byte[10];
            socket.getInputStream().read(b);
            System.out.println("读取的数据：" + new String(b));
            socket.close();
        }
    }
}
