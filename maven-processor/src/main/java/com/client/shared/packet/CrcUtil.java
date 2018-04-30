package com.client.shared.packet;

import java.util.zip.CRC32;

public class CrcUtil {

    private CRC32 crc32;

    public CrcUtil() {
        this.crc32 = new CRC32();
    }

    public byte[] calcCrc32(byte[] data, int offset) {
        this.crc32.reset();
        this.crc32.update(data, offset, data.length - offset);
        return this.longToBytes(this.crc32.getValue());
    }

    private byte[] longToBytes(long l) {
        byte[] result = new byte[4];
        for (int i = 3; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    public static boolean compareCrc(byte[] crcSrc, byte[] crcDst) {
        boolean match = true;
        for (int i = 0; i < 4; i++)
            if (crcSrc[i] != crcDst[i])
                match = false;
        return match;
    }
}
