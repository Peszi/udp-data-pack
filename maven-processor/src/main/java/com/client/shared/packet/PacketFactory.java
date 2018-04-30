package com.client.shared.packet;

import java.util.*;

public class PacketFactory {

    private CrcUtil crcUtil;

    private Map<Class<? extends PacketSkeleton>, BasePacket<? extends PacketSkeleton>> packetsClassMap;
    private Map<Byte, BasePacket<? extends PacketSkeleton>> packetsPidMap;
    private int packetLength;

    private Class<? extends PacketSkeleton> outPacket;

    public PacketFactory(int packetLength) {
        this.crcUtil = new CrcUtil();
        this.packetLength = packetLength;
        this.packetsClassMap = new HashMap<>();
        this.packetsPidMap = new HashMap<>();
    }

    public void setOutPacket(Class<? extends PacketSkeleton> outPacket) {
        this.outPacket = outPacket;
    }

    public <T extends PacketSkeleton> void bindPacket(T packet) {
        BasePacket<T> basePacket = new BasePacket<>(packet);
        basePacket.init(this.packetLength);
        this.packetsClassMap.put(packet.getClass(), basePacket);
        this.packetsPidMap.put(basePacket.getPacketPid(), basePacket);
    }

    public <T extends PacketSkeleton> T getPacket(Class<T> key) {
        return key.cast(this.packetsClassMap.get(key).getPacket());
    }

    @Deprecated
    public BasePacket<? extends PacketSkeleton> getBasePacket(Class<? extends PacketSkeleton> key) {
        return this.packetsClassMap.get(key);
    }

    public boolean validatePacket(byte[] packetData) {
        final byte[] crcDst = this.crcUtil.calcCrc32(packetData, BasePacket.PID_OFFSET);
        return CrcUtil.compareCrc(packetData, crcDst);
    }

    public short updatePacket(byte[] packetData) {
        final byte packetPid = packetData[BasePacket.PID_OFFSET];
        this.packetsPidMap.get(packetPid).setupPacket(packetData);
        return packetPid;
    }

    public byte[] getOutPacketBuffer() {
        return this.packetsClassMap.get(this.outPacket).getPacketByteArray(this.crcUtil);
    }

    public byte[] getPacketBuffer(Class<? extends PacketSkeleton> key) {
        return this.packetsClassMap.get(key).getPacketByteArray(this.crcUtil);
    }

    public byte[] geNewByteBuffer() {
        return new byte[this.packetLength];
    }

    public int getPacketLength() {
        return this.packetLength;
    }

}
