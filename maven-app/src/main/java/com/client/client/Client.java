package com.client.client;

import com.client.shared.packet.*;
import com.client.shared.packet.client.ClientPingPacket;
import com.client.shared.packet.server.PacketPids;
import com.client.shared.packet.server.ServerPong;
import com.client.shared.packet.server.ServerPongPacket;

import java.io.IOException;
import java.net.*;

public class Client {

    private static final int PACKET_LENGTH = 128;

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 4445;

    private PacketFactory packetFactory;
    private DatagramSocket socket;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;

    public Client() {
        this.packetFactory = new PacketFactory(PACKET_LENGTH);
        this.init();
    }

    private void init() {
        try {
            this.socket = new DatagramSocket();
            this.socket.setSoTimeout(1000);
            this.inPacket = new DatagramPacket(this.packetFactory.geNewByteBuffer(), this.packetFactory.getPacketLength());
            this.outPacket = new DatagramPacket(this.packetFactory.geNewByteBuffer(), this.packetFactory.getPacketLength(),
                    InetAddress.getByName(SERVER_IP), SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
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
        this.packetFactory.setOutPacket(EmptyPacket.class);
        while(true) {
            try {
                this.outPacket.setData(this.packetFactory.getOutPacketBuffer());
                this.socket.send(this.outPacket);
                this.packetFactory.setOutPacket(EmptyPacket.class);
                this.socket.receive(this.inPacket);
                final byte[] receivedData = this.inPacket.getData();
                if (this.packetFactory.validatePacket(receivedData)) {
                    final short packetPid = this.packetFactory.updatePacket(receivedData);
                    switch (packetPid) {
                        case PacketPids.PACKET_SERVER_PONG:
                            this.handleQueueData(this.packetFactory.getPacket(ServerPongPacket.class));
                            break;
                        default:
                            System.out.println("UNKNOWN PACKET");
                            break;
                    }
                } else {
                    System.err.println("Packet is invalid!");
                }
            } catch (SocketTimeoutException e) {
                System.err.println("Socket timeout!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleQueueData(ServerPongPacket dataPacket) {
        // handle data
        final String message = dataPacket.getPongmessage();
        System.out.println(">" + message);

        // prepare feedback
        ClientPingPacket packet = this.packetFactory.getPacket(ClientPingPacket.class);
        packet.setPing(ServerPong.SOME_STRING);
        packet.setValue1(ServerPong.SOME_BYTE);
        packet.setValue2(ServerPong.SOME_SHORT);
        packet.setValue3(ServerPong.SOME_INT);
        packet.setValue4(ServerPong.SOME_LONG);
        packet.setValue6(ServerPong.SOME_FLOAT);
        packet.setValue7(ServerPong.SOME_DOUBLE);
        packet.setValue8(ServerPong.SOME_DOUBLE_2);
        this.packetFactory.setOutPacket(packet.getClass());
    }
}
