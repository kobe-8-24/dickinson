package com.htsc.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            Socket socket = serverSocket.accept();
            byte[] b = new byte[4];
            StringBuffer stringBuffer = new StringBuffer();
            while (socket.getInputStream().read(b) != -1) {
                stringBuffer.append(new String(b));
                b = new byte[4];
            }
            System.out.println("读取的数据：" + stringBuffer.toString());
            socket.close();
        }
    }
}
