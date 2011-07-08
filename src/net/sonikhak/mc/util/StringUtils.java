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

package net.sonikhak.mc.util;

import java.nio.ByteBuffer;

/**
 * @author sonikhak (https://github.com/sonikhak)
 */
public class StringUtils {

    public static void writeString(ByteBuffer buf, String str) {
        int len = str.length();
        buf.putShort((short) len);
        for (int i = 0; i < len; i++) {
            buf.putChar(str.charAt(i));
        }
    }

    public static String readString(ByteBuffer buf) {
        int len = buf.getShort() & 0xFF;

        char[] characters = new char[len];
        for (int i = 0; i < len; i++) {
            characters[i] = buf.getChar();
        }

        return new String(characters);
    }
}
