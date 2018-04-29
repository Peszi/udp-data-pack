package com.client.packet;

import com.client.types.Packet;
import com.client.types.PacketData;

@Packet
public abstract class ClientPing extends PacketSkeleton {

    private static final int MESSAGE_LENGTH = 24;

    @PacketData
    protected byte value1;

    @PacketData
    protected short value2;

    @PacketData
    protected int value3;

    @PacketData
    protected long value4;

    @PacketData(stringLength = MESSAGE_LENGTH)
    protected String pingMessage;

    @PacketData
    protected float value6;

    @PacketData
    protected double value7;

}
