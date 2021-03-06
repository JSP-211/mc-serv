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

import java.util.ArrayList;

/**
 * @author sonikhak (https://github.com/sonikhak)
 */
final class CodecStore {
    private static ArrayList<Codec> codecs = new ArrayList<Codec>();

    static {
        codecs.add(new HandshakeCodec());
        codecs.add(new LoginCodec());
    }

    public static Codec get(int opcode) {
        for(Codec c : codecs) {
            if(c.opcode() == opcode) {
                return c;
            }
        }
        return null;
    }
}
