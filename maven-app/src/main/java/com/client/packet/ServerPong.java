package com.client.packet;


import com.client.types.Packet;
import com.client.types.PacketData;

@Packet
public abstract class ServerPong extends PacketSkeleton {

    @PacketData
    protected byte value1;

    @PacketData(stringLength = 26)
    protected String pongMessage;

}
