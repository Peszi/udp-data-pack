package com.client.shared.packet.client;

import com.client.shared.packet.PacketSkeleton;
import com.client.types.Packet;
import com.client.types.PacketData;

@Packet
public abstract class RegisterUser extends PacketSkeleton {

    @PacketData(stringLength = 96)
    protected String accessToken;

}
