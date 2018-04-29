package com.client;

import com.client.packet.*;

/**
 * Hello world!
 *
 */
//@ConstantFactory(name = "PacketPids", value = {"AWAITING_GAME", "IN_GAME", "AFTER_GAME", "SPECTATE_GAME"})
public class App {

//    private ClientPingPacket queueDataPacket;

    public App() {

        BasePacket<ClientPingPacket> packet1 = new BasePacket<>(new ClientPingPacket());
        packet1.getPacket().setPingmessage("o hi Mark Client!");

//        BasePacket<ServerPongPacket> packet2 = new BasePacket<>(new ServerPongPacket());
//        packet2.getPacket().setPingmessage("o hi John Server!");

        Container container = new Container();
        container.put(packet1);
//        container.put(packet2);

//        System.out.println("ClientPingPacket Message: " + container.get(ClientPingPacket.class).getValue5());
//        System.out.println("ServerPongPacket Message: " + container.get(ServerPongPacket.class).getValue2());

//        System.out.println("-----------------");
//        for (PacketPid packetPid : PacketPid.values())
//            System.out.println(" - PID " + packetPid.ordinal());
//        System.out.println("-----------------");

//        byte PACKET_PID = 0;
//
//        BasePacket<ClientPingPacket>
//
//        PacketFactory packetFactory = new PacketFactory();
//        packetFactory.setPacketArray(new ClientPingPacket(), PACKET_PID);
//
//        packetFactory.registerPacket();
//
//        this.queueDataPacket = packetFactory.getPacket(PACKET_PID);
//
//        BasePacket<ClientPingPacket> data = new BasePacket<>(new ClientPingPacket());
//

//        // Init
//        packetFactory.registerPacket(new BasePacket<>(new ClientPingPacket()));
//
//        // Setting up
//        BasePacket<ClientPingPacket> data = packetFactory.getPacket(PacketPids.PACKET_QUEUE_DATA);
//        data.getPacket().setValue1((byte) 1);
//        data.getPacket().setValue3(64);
//
//        byte[] array = packetFactory.getPacketArray(PacketPids.PACKET_QUEUE_DATA);
//        // Sending raw array
//
//        PacketFactory packetFactory2 = new PacketFactory(0);
//        packetFactory2.registerPacket(new BasePacket<>(new ClientPingPacket()));
//        BasePacket<ClientPingPacket> data2 = packetFactory2.getPacket(PacketPids.PACKET_QUEUE_DATA);
//        System.out.println(" receiver wipe correct ? " + data2.getPacket().getValue3());
//
//        // Receiving packet array
//        System.out.println("is CRC correct? " + packetFactory2.validatePacket(array));
//
//        // Receiving packet array
//        packetFactory2.updatePacket(array);
//
//        System.out.println(" check received data ? " + data2.getPacket().getValue3());


//        for (byte b : data.getPacket())
//            System.out.print(b + ":");
//        System.out.println();
//
//        System.out.println("Validate " + packetFactory.validatePacket(packetFactory.getPacketArray(PACKET_PID)));
//
//        System.out.println("Update " + packetFactory.updatePacket(packetFactory.getPacketArray(PACKET_PID)));
//
//        System.out.println("getValue1 " + ((ClientPingPacket) packetFactory.getPacket(PACKET_PID)).getValue1());
//        System.out.println("getValue3 " + ((ClientPingPacket) packetFactory.getPacket(PACKET_PID)).getValue3());

//        packetFactory.buildPacket(new GameDataPacket());

//        SimplePacket basePacket = new SimplePacket();
//        basePacket.setValue1((byte) 0x0f);
//        basePacket.setValue2((short) 64);
//        basePacket.setValue3(123);
//        basePacket.setValue4(12);
//        basePacket.setValue5("HelloWorld!");
//        basePacket.setValue6(0.99999d);
//        basePacket.setValue7(0.5f);
//        basePacket.packData();

//        PacketPid


//        SimplePacketBuilder simplePacketBuilder = new SimplePacketBuilder();


//        ClientPingPacket queueData = new ClientPingPacket();
////        queueData.setValue1((byte) 64);
////        queueData.setValue2((short) 120);
////        queueData.setValue3(69);
////        queueData.setValue4(12343534534L);
////        queueData.setValue5("HelloWorld!HelloWorld!HelloWorld!");
////        queueData.setValue6(0.4f);
////        queueData.setValue7(0.832456d);
////
//        byte[] array = queueData.getPacketByteArray();
////
//        ClientPingPacket queueData2 = new ClientPingPacket();
//        queueData2.setupPacket(array);
//        System.out.println(queueData2.getValue1());
//        System.out.println(queueData2.getValue2());
//        System.out.println(queueData2.getValue3());
//        System.out.println(queueData2.getValue4());
//        System.out.println(queueData2.getValue5());
//        System.out.println(queueData2.getValue6());
//        System.out.println(queueData2.getValue7());

//        System.out.println("END");
    }

    public static void main( String[] args ) {
        new App();
    }
}
