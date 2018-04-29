package com.client;

import com.client.packet.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Sender {

    private static final int PACKET_LENGTH = 128;
    private static final int SERVER_PORT = 4445;
    private static final short DEFAULT_OUT_PACKET = 0;

    private PacketFactory packetFactory;
    private DatagramPacket packet;
    private DatagramSocket socket;

    private Sender() {
        this.packetFactory = new PacketFactory(PACKET_LENGTH);
        this.init();
    }

    private void init() {
        try {
            this.socket = new DatagramSocket(SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.register();
        this.loop();
    }

    private void register() {
        this.packetFactory.putPacket(new BasePacket<>(new ServerPongPacket()));
        this.packetFactory.putPacket(new BasePacket<>(new ClientPingPacket()));
    }

    private void loop() {
        this.packet = new DatagramPacket(this.packetFactory.getByteBuffer(), this.packetFactory.getPacketLength());
        while(true) {
            try {
                this.socket.receive(this.packet);
                final byte[] receivedData = this.packet.getData();
                this.packetFactory.setOutPacketPid(DEFAULT_OUT_PACKET);
                if (this.packetFactory.validatePacket(receivedData)) {
                    final short packetPid = this.packetFactory.updatePacket(receivedData);
                    switch (packetPid) {
                        case PacketPids.PACKET_CLIENT_PING:
                            this.handleQueueData(this.packetFactory.getPacket(ClientPingPacket.class));
                            break;
                    }
                } else {
                    System.err.println("Packet is invalid!");
                }
                this.socket.send(new DatagramPacket(this.packetFactory.getOutPacketBuffer(), this.packetFactory.getPacketLength(), this.packet.getAddress(), this.packet.getPort()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleQueueData(ClientPingPacket dataPacket) {
        // handle data
        final int value3 = dataPacket.getValue3();

        // prepare feedback
        ServerPongPacket serverData = this.packetFactory.getPacket(ServerPongPacket.class);
        serverData.setPongmessage("Pong from server!");
        this.packetFactory.setOutPacketPid(PacketPids.PACKET_SERVER_PONG);
    }

    public static void main(String[] args) {
        new Sender();
    }
}
