package com.client.server;

import com.client.shared.packet.*;
import com.client.shared.packet.client.ClientPingPacket;
import com.client.shared.packet.client.RegisterUserPacket;
import com.client.shared.packet.server.PacketPids;
import com.client.shared.packet.server.ServerPongPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

    private static final int PACKET_LENGTH = 128;

    private static final int SERVER_PORT = 4445;

    private PacketFactory packetFactory;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;
    private DatagramSocket socket;

    private Server() {
        this.packetFactory = new PacketFactory(PACKET_LENGTH);
        this.packetFactory.setOutPacket(ServerPongPacket.class);
        this.init();
    }

    private void init() {
        try {
            this.socket = new DatagramSocket(SERVER_PORT);
            this.inPacket = new DatagramPacket(this.packetFactory.geNewByteBuffer(), this.packetFactory.getPacketLength());
            this.outPacket = new DatagramPacket(this.packetFactory.geNewByteBuffer(), this.packetFactory.getPacketLength());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.register();
        this.loop();
    }

    private void register() {
        this.packetFactory.bindPacket(new ServerPongPacket());
        this.packetFactory.bindPacket(new ClientPingPacket());
    }

    private void loop() {
        while(true) {
            try {
                this.socket.receive(this.inPacket);
                final byte[] receivedData = this.inPacket.getData();
                if (this.packetFactory.validatePacket(receivedData)) {
                    final short packetPid = this.packetFactory.updatePacket(receivedData);
                    switch (packetPid) {
                        case PacketPids.PACKET_CLIENT_PING:
                            this.handleQueueData(this.packetFactory.getPacket(ClientPingPacket.class));
                            break;
                        case PacketPids.PACKET_REGISTER_USER:
                            this.handleRegisterData(this.packetFactory.getPacket(RegisterUserPacket.class));
                            break;
                         default:
                             System.err.println("Pid invalid!");
                             break;
                    }
                } else {
                    System.err.println("Packet is invalid!");
                }
                this.sendPacket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPacket() throws IOException {
        this.outPacket.setAddress(this.inPacket.getAddress());
        this.outPacket.setPort(this.inPacket.getPort());
        this.outPacket.setData(this.packetFactory.getOutPacketBuffer());
        this.socket.send(this.outPacket);
        this.packetFactory.setOutPacket(ServerPongPacket.class);
    }

    private void handleRegisterData(RegisterUserPacket dataPacket) {
        // handle data
        System.out.println("User registering " + dataPacket.getAccesstoken());

        // prepare feedback
        ServerPongPacket serverData = this.packetFactory.getPacket(ServerPongPacket.class);
        serverData.setPongmessage("Pong from server!");
        this.packetFactory.setOutPacket(serverData.getClass());
    }

    private void handleQueueData(ClientPingPacket dataPacket) {
        // handle data
        System.out.println("{" + dataPacket.getPing()
                            + " + " + dataPacket.getValue1()
                            + " + " + dataPacket.getValue2()
                            + " + " + dataPacket.getValue3()
                            + " + " + dataPacket.getValue4()
                            + " + " + dataPacket.getValue6()
                            + " + " + dataPacket.getValue7()
                            + " + " + dataPacket.getValue8());

        // prepare feedback
        ServerPongPacket serverData = this.packetFactory.getPacket(ServerPongPacket.class);
        serverData.setPongmessage("Pong from server!");
        this.packetFactory.setOutPacket(serverData.getClass());
    }

    public static void main(String[] args) {
        new Server();
    }
}
