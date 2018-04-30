package com.client.shared.packet.client;

import com.client.shared.packet.PacketSkeleton;
import com.client.types.Packet;
import com.client.types.PacketData;

@Packet
public abstract class ClientPing extends PacketSkeleton {

    private static final int MESSAGE_LENGTH = 24;

    @PacketData(stringLength = MESSAGE_LENGTH)
    protected String ping;

    @PacketData
    protected byte value1;

    @PacketData
    protected short value2;

    @PacketData
    protected int value3;

    @PacketData
    protected long value4;

    @PacketData
    protected float value6;

    @PacketData
    protected double value7;

    @PacketData
    protected double value8;

}
