package com.client.packet;

public interface PacketConstructorInterface {
    void setPacketPid(int packetPid);
    void packVariable(String fieldName, byte value);
    void packVariable(String fieldName, short value);
    void packVariable(String fieldName, int value);
    void packVariable(String fieldName, long value);
    void packVariable(String fieldName, float value);
    void packVariable(String fieldName, double value);
    void packVariable(String fieldName, String value, int length);
    byte getPackedByte(String fieldName);
    short getPackedShort(String fieldName);
    int getPackedInt(String fieldName);
    long getPackedLong(String fieldName);
    float getPackedFloat(String fieldName);
    double getPackedDouble(String fieldName);
    String getPackedString(String fieldName, int stringLen);
}
