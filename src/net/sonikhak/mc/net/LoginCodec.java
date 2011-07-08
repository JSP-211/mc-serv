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

import net.sonikhak.mc.util.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author sonikhak (https://github.com/sonikhak)
 */
final class LoginCodec implements Codec {

    @Override
    public int opcode() {
        return 0x01;
    }

    @Override
    public void handle(SocketChannel chan, ByteBuffer in) throws IOException {
        System.out.println("LOGINNN");
        int entityID = in.getInt();
        String username = StringUtils.readString(in);
        long map_seed = in.getLong();
        byte dimension = in.get();
        ByteBuffer out = ByteBuffer.allocate(80);
        out.putInt(entityID);
        StringUtils.writeString(out, username);
        out.putLong(map_seed);
        out.put(dimension);
        chan.write(out);
    }
}
