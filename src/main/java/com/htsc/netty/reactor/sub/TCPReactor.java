package com.htsc.netty.reactor.sub;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class TCPReactor implements Runnable {

    private final ServerSocketChannel ssc;

    private final Selector selector;// mainReactor用的selector

    public TCPReactor(int port) throws IOException {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // Acc
    }

    @Override
    public void run() {

    }
}
