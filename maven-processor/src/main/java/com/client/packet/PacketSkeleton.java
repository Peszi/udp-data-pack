package com.client.packet;

public abstract class PacketSkeleton {

    protected PacketConstructorInterface packetConstructor;

    public void setPacketConstructor(PacketConstructorInterface packetConstructor) {
        this.packetConstructor = packetConstructor;
        this.setPacketPid();
    }

    protected abstract void setPacketPid();

}
