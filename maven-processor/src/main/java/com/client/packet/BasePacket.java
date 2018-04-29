package com.client.packet;

import com.client.types.PacketData;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class BasePacket<T extends PacketSkeleton> implements PacketConstructorInterface {

    public static final int PID_OFFSET = 4;
    public static final int DATA_OFFSET = 6;

    private static final int BUFFER_LENGTH = 128;

    private Map<String, Integer> bufferMap;

    private ByteBuffer byteBuffer;

    private boolean dataChanged;

    private T packet;

    public BasePacket(T packet) {
        this.packet = packet;
        this.initBuffer(BasePacket.BUFFER_LENGTH);
        this.packet.setPacketConstructor(this);
    }

    public T getPacket() {
        return packet;
    }

    private void initBuffer(int length) {
        this.byteBuffer = ByteBuffer.allocate(length);
        this.setupMapping();
    }

    public void setupPacket(byte[] byteArray) {
        this.byteBuffer.position(0);
        this.byteBuffer.put(byteArray, 0, byteArray.length);
    }

    private void setupMapping() {
        this.bufferMap = new HashMap<>();
        int dataPosition = BasePacket.DATA_OFFSET;
        for (Field field : this.packet.getClass().getSuperclass().getDeclaredFields()) {
            if (field.isAnnotationPresent(PacketData.class)) {
                int varLength = DataUtility.sizeof(field.getType());
                if (field.getType().isAssignableFrom(String.class))
                    varLength = field.getAnnotation(PacketData.class).stringLength();
                if ((dataPosition + varLength) <= BasePacket.BUFFER_LENGTH) {
                    this.bufferMap.put(field.getName(), dataPosition);
                    dataPosition += varLength;
                } else {
                    System.err.println("Packet data overflow! [ " + (dataPosition + varLength) + " of " + BasePacket.BUFFER_LENGTH + " ]");
                }
            }
        }
        System.out.println("Packet length " + dataPosition);
    }

    @Override
    public void setPacketPid(int packetPid) {
        this.byteBuffer.put(BasePacket.PID_OFFSET, (byte) packetPid);
    }

    @Override
    public void packVariable(String fieldName, byte value) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) { this.byteBuffer.put(dataPosition, value); this.dataChanged = true; }
    }

    @Override
    public void packVariable(String fieldName, short value) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) { this.byteBuffer.putShort(dataPosition, value); this.dataChanged = true; }
    }

    @Override
    public void packVariable(String fieldName, int value) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) { this.byteBuffer.putInt(dataPosition, value); this.dataChanged = true; }
    }

    @Override
    public void packVariable(String fieldName, long value) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) { this.byteBuffer.putLong(dataPosition, value); this.dataChanged = true; }
    }

    @Override
    public void packVariable(String fieldName, float value) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) { this.byteBuffer.putFloat(dataPosition, value); this.dataChanged = true; }
    }

    @Override
    public void packVariable(String fieldName, double value) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) { this.byteBuffer.putDouble(dataPosition, value); this.dataChanged = true; }
    }

    @Override
    public void packVariable(String fieldName, String value, int length) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) {
            int strLen = value.getBytes().length;
            if (strLen > length)
                value.substring(0, length);
            this.byteBuffer.position(dataPosition);
            this.byteBuffer.put(value.getBytes(), 0, value.length());
            this.dataChanged = true;
        }
    }

    @Override
    public byte getPackedByte(String fieldName) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) {
            this.byteBuffer.position(dataPosition);
            return this.byteBuffer.get();
        }
        return 0;
    }

    @Override
    public short getPackedShort(String fieldName) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) {
            this.byteBuffer.position(dataPosition);
            return this.byteBuffer.getShort();
        }
        return 0;
    }

    @Override
    public int getPackedInt(String fieldName) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) {
            this.byteBuffer.position(dataPosition);
            return this.byteBuffer.getInt();
        }
        return 0;
    }

    @Override
    public long getPackedLong(String fieldName) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) {
            this.byteBuffer.position(dataPosition);
            return this.byteBuffer.getLong();
        }
        return 0;
    }

    @Override
    public float getPackedFloat(String fieldName) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) {
            this.byteBuffer.position(dataPosition);
            return this.byteBuffer.getFloat();
        }
        return 0;
    }

    @Override
    public double getPackedDouble(String fieldName) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) {
            this.byteBuffer.position(dataPosition);
            return this.byteBuffer.getDouble();
        }
        return 0;
    }

    @Override
    public String getPackedString(String fieldName, int stringLen) {
        Integer dataPosition = this.bufferMap.get(fieldName);
        if (dataPosition != null) {
            byte[] byteArray = new byte[stringLen];
            this.byteBuffer.position(dataPosition);
            this.byteBuffer.get(byteArray, 0, stringLen);
            return new String(byteArray).trim();
        }
        return "";
    }

    public byte getPacketPid() {
        return this.byteBuffer.get(BasePacket.PID_OFFSET);
    }

    public byte[] getPacketByteArray(CrcUtil crcUtil) {
        if (this.dataChanged) {
            this.dataChanged = false;
            this.byteBuffer.put(crcUtil.calcCrc32(this.byteBuffer.array(), 4), 0, 4);
        }
        return this.byteBuffer.array();
    }

//    public void packData() {
//        DataUtility packetBuffer = new DataUtility(Main.LENGTH);
////        Field[] fields = BasePacket.class.getFields(); // returns inherited members but not private members.
////        Field[] fields2 = BasePacket.class.getDeclaredFields(); // returns all members including private members but not inherited members.
//        Field[] fields2 = this.getClass().asSubclass(this.getClass()).getDeclaredFields();
//        System.out.println("-------------------");
//        long startTime = System.nanoTime();
//        for (Field field : fields2) {
//            try {
////                field.setAccessible(true);
//                final Object value = field.get(this);
//                if (field.isAnnotationPresent(Packet.class)) {
////                    System.out.println(field.getName() + " = " + field.getType().getName() + " = " + value);
////                    "d".getClass().forName();
////                    Class<?> theClass = Class.forName(field.getType().toString());
////                    if (value instanceof Integer) {
////                        System.out.println("Integer");
////                    }
////                    if (value.getClass().getName()) {
//                        System.out.println(field.getType().getName());
////                    }
////                    packetBuffer.putData(1);
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("-------------------");
//        System.out.println("Time " + TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS) + " ms");
//    }

}
