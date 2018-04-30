package com.client.shared.packet.server;


import com.client.shared.packet.PacketSkeleton;
import com.client.types.Packet;
import com.client.types.PacketData;

@Packet
public abstract class ServerPong extends PacketSkeleton {

    public static final String SOME_STRING = "Hello World!";
    public static final byte SOME_BYTE = (byte) 0xf0;
    public static final short SOME_SHORT = (short) 0xff0f;
    public static final int SOME_INT = 32233;
    public static final long SOME_LONG = 21321321312L;
    public static final float SOME_FLOAT = 0.2534f;
    public static final double SOME_DOUBLE = 0.213213213d;
    public static final double SOME_DOUBLE_2 = 0.8384234d;

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

    @PacketData(stringLength = 24)
    protected String pongMessage;

    @PacketData
    protected double value7;

    @PacketData
    protected double value8;

}
