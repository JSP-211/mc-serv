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

package net.sonikhak.mc.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author sonikhak (https://github.com/sonikhak)
 */
public final class Client {
    private SocketChannel skt;
    private ByteBuffer in;
    private ByteBuffer out;

    public Client(SelectionKey skey) {
        in = ByteBuffer.allocateDirect(512);
        out = ByteBuffer.allocateDirect(512);
        this.skt = (SocketChannel) skey.channel();
    }

    public void handleCodecs() throws IOException {
        if(skt.read(in) == -1) {
            return;
        }
        in.flip();
        while (in.hasRemaining()) {
            int opcode = in.get() & 0xFF;
            if(opcode == -1) {
                break;
            }
            try {
                CodecStore.get(opcode).handle(skt, in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
