/*
 * Copyright 2011 sonikhak <https://github.com/sonikhak>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sonikhak.mc;

import net.sonikhak.mc.net.Client;
import net.sonikhak.mc.util.Stopwatch;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import static java.lang.System.out;

/**
 * @author sonikhak (https://github.com/sonikhak)
 */
public class Server implements Runnable {
    private ArrayList<Client> clients = new ArrayList<Client>();
    private ServerSocketChannel servSockChan;
    private Selector selector;
    private Stopwatch timer = new Stopwatch();

    private void start(int port) {
        try {
            servSockChan = ServerSocketChannel.open();
            selector = Selector.open();
            servSockChan.configureBlocking(false);
            servSockChan.socket().bind(new InetSocketAddress(port));
            servSockChan.register(selector, SelectionKey.OP_ACCEPT);
            out.printf("Listening on port %d\n", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept() throws IOException {
        SocketChannel socketChannel;
        for (int i = 0; i < 15; i++) {
            socketChannel = servSockChan.accept();
            if (socketChannel == null) {
                break;
            }
            socketChannel.configureBlocking(false);
            SelectionKey read = socketChannel.register(selector, SelectionKey.OP_READ);
            clients.add(new Client(read));
            out.printf("Accepted connection %s\n", socketChannel.socket().getLocalAddress().getHostAddress());
        }
    }

    private void cycle() throws IOException {
        selector.selectNow();
        for (SelectionKey skey : selector.selectedKeys()) {
            if (skey.isAcceptable()) {
                accept();
            }
            if (skey.isReadable()) {
                for (Client c : clients) {
                    c.handleCodecs();
                }
            }
        }
    }

    @SuppressWarnings({"InfiniteLoopStatement"})
    @Override
    public void run() {
        start(25565);
        while (true) {
            try {
                cycle();
                //I DIDNT WRITE THIS
                long sleepTime = 500 - timer.elapsed();
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                } else {
                    System.out.println("[WARNING]: Server load: " + (100 + (Math.abs(sleepTime) / (500 / 100))) + "%!");
                }
                timer.reset();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }
}
