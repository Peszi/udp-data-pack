package com.client;

import com.client.shared.packet.BasePacket;
import com.client.shared.packet.PacketSkeleton;

import java.util.HashMap;
import java.util.Map;

public class Container {

    private Map<Class<? extends PacketSkeleton>, BasePacket<? extends PacketSkeleton>> objectMap;

    public Container() {
        this.objectMap = new HashMap<>();
    }

    public <T extends PacketSkeleton> void put(BasePacket<T> value) {
        this.objectMap.put(value.getPacket().getClass(), value);
    }

    public <T extends PacketSkeleton> T get(Class<T> key) {
        return key.cast(this.objectMap.get(key).getPacket());
    }
}
