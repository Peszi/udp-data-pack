package com.client.packet;

import java.util.*;

public class PacketFactory {

    private CrcUtil crcUtil;

    private Map<Class<? extends PacketSkeleton>, BasePacket<? extends PacketSkeleton>> packetsMap;
    private byte[] byteBuffer;

    private short outPacketPid;

    public PacketFactory(int packetLength) {
        this.crcUtil = new CrcUtil();
        this.byteBuffer = new byte[packetLength];
        this.packetsMap = new HashMap<>();
    }

    public void setOutPacketPid(short outPacketPid) {
        this.outPacketPid = outPacketPid;
    }

    public byte[] getOutPacketBuffer() {
        return this.getPacketArray(this.outPacketPid);
    }

    public <T extends PacketSkeleton> void putPacket(BasePacket<T> value) {
        this.packetsMap.put(value.getPacket().getClass(), value);
    }

    public <T extends PacketSkeleton> T getPacket(Class<T> key) {
        return key.cast(this.packetsMap.get(key).getPacket());
    }

    @Deprecated
    public BasePacket<? extends PacketSkeleton> getBasePacket(Class<? extends PacketSkeleton> key) {
        return this.packetsMap.get(key);
    }

    public boolean validatePacket(byte[] packetData) {
        final byte[] crcDst = this.crcUtil.calcCrc32(packetData, BasePacket.PID_OFFSET);
        return CrcUtil.compareCrc(packetData, crcDst);
    }

    public short updatePacket(byte[] packetData) {
        final byte packetPid = packetData[BasePacket.PID_OFFSET];
        this.packetsMap.get(packetPid).setupPacket(packetData);
        return packetPid;
    }

    public byte[] getPacketArray(short packetPid) {
        return this.packetsMap.get(packetPid).getPacketByteArray(this.crcUtil);
    }

    public byte[] getByteBuffer() {
        return this.byteBuffer;
    }

    public int getPacketLength() {
        return this.byteBuffer.length;
    }
}
