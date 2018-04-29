package com.client.packet;

public class DataUtility {

    public static int sizeof(Class dataType) {
        if (dataType == null) throw new NullPointerException();
        if (dataType == byte.class || dataType == Byte.class) return Byte.BYTES;
        if (dataType == char.class || dataType == Character.class) return Character.BYTES;
        if (dataType == short.class || dataType == Short.class) return Short.BYTES;
        if (dataType == int.class || dataType == Integer.class) return Integer.BYTES;
        if (dataType == long.class || dataType == Long.class) return Long.BYTES;
        if (dataType == float.class || dataType == Float.class) return Float.BYTES;
        if (dataType == double.class || dataType == Double.class) return Double.BYTES;
        return 4;
    }
}
