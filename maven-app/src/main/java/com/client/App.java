//package com.client;
//
//import com.client.shared.packet.*;
//
///**
// * Hello world!
// *
// */
////@ConstantFactory(name = "PacketPids", value = {"AWAITING_GAME", "IN_GAME", "AFTER_GAME", "SPECTATE_GAME"})
//public class App {
//    public static final float VALUE_6 = 0.3232f;
//    public static final int VALUE_3 = 69;
//
////    private ClientPingPacket queueDataPacket;
//
//    private CrcUtil crcUtil = new CrcUtil();
//
//    public App() {
//
//        // client
//        ClientPingPacket pingPacket = new ClientPingPacket();
//        PacketFactory client = new PacketFactory(128);
//        client.setOutPacket(ClientPingPacket.class);
//        client.bindPacket(pingPacket);
//
//        pingPacket.setValue1(ServerPong.SOME_BYTE);
//        pingPacket.setValue2(ServerPong.SOME_SHORT);
//
//        byte[] clientData = client.getOutPacketBuffer();
//
//        for (int i = 0; i < 48; i++)
//            System.out.print(clientData[i] + ":");
//        System.out.println();
//
//        // server
//        PacketFactory server = new PacketFactory(128);
//        server.bindPacket(new ClientPingPacket());
//
////        for (int i = 0; i < 12; i++)
////            System.out.print(clientData[i] + ":");
////        System.out.println();
//
//        System.out.println("Crc condition : " + server.validatePacket(clientData));
//        server.updatePacket(clientData);
//
//        final ClientPingPacket dataPacket = server.getPacket(ClientPingPacket.class);
//        System.out.println(">"
//                + ":" + dataPacket.getValue1() + " TO " + ServerPong.SOME_BYTE
//                + ":" + dataPacket.getValue2() + " TO " + ServerPong.SOME_SHORT
//                + ":" + dataPacket.getValue3() + " TO " + ServerPong.SOME_INT
//                + ":" + dataPacket.getValue4() + " TO " + ServerPong.SOME_LONG
//                + ":" + dataPacket.getValue6() + " TO " + ServerPong.SOME_FLOAT
//                + ":" + dataPacket.getValue7() + " TO " + ServerPong.SOME_DOUBLE
//                + ":" + dataPacket.getValue8() + " TO " + ServerPong.SOME_DOUBLE_2);
//
//        System.out.println("result");
//        System.out.println((dataPacket.getValue1() == ServerPong.SOME_BYTE) ? true : false);
//        System.out.println((dataPacket.getValue2() == ServerPong.SOME_SHORT) ? true : false);
//        System.out.println((dataPacket.getValue3() == ServerPong.SOME_INT) ? true : false);
//        System.out.println((dataPacket.getValue4() == ServerPong.SOME_LONG) ? true : false);
//        System.out.println((dataPacket.getValue6() == ServerPong.SOME_FLOAT) ? true : false);
//        System.out.println((dataPacket.getValue7() == ServerPong.SOME_DOUBLE) ? true : false);
//        System.out.println((dataPacket.getValue8() == ServerPong.SOME_DOUBLE_2) ? true : false);
//        System.out.println("test end");
//
////        BasePacket<ServerPongPacket> packet2 = new BasePacket<>(new ServerPongPacket());
////        packet2.getPacket().setPingmessage("o hi John Server!");
//
////        container.put(packet2);
//
////        System.out.println("ClientPingPacket Message: " + container.get(ClientPingPacket.class).getValue5());
////        System.out.println("ServerPongPacket Message: " + container.get(ServerPongPacket.class).getValue2());
//
////        System.out.println("-----------------");
////        for (PacketPid packetPid : PacketPid.values())
////            System.out.println(" - PID " + packetPid.ordinal());
////        System.out.println("-----------------");
//
////        byte PACKET_PID = 0;
////
////        BasePacket<ClientPingPacket>
////
////        PacketFactory packetFactory = new PacketFactory();
////        packetFactory.setPacketArray(new ClientPingPacket(), PACKET_PID);
////
////        packetFactory.registerPacket();
////
////        this.queueDataPacket = packetFactory.getPacket(PACKET_PID);
////
////        BasePacket<ClientPingPacket> data = new BasePacket<>(new ClientPingPacket());
////
//
////        // Init
////        packetFactory.registerPacket(new BasePacket<>(new ClientPingPacket()));
////
////        // Setting up
////        BasePacket<ClientPingPacket> data = packetFactory.getPacket(PacketPids.PACKET_QUEUE_DATA);
////        data.getPacket().setValue1((byte) 1);
////        data.getPacket().setValue3(64);
////
////        byte[] array = packetFactory.getPacketArray(PacketPids.PACKET_QUEUE_DATA);
////        // Sending raw array
////
////        PacketFactory packetFactory2 = new PacketFactory(0);
////        packetFactory2.registerPacket(new BasePacket<>(new ClientPingPacket()));
////        BasePacket<ClientPingPacket> data2 = packetFactory2.getPacket(PacketPids.PACKET_QUEUE_DATA);
////        System.out.println(" receiver wipe correct ? " + data2.getPacket().getValue3());
////
////        // Receiving packet array
////        System.out.println("is CRC correct? " + packetFactory2.validatePacket(array));
////
////        // Receiving packet array
////        packetFactory2.updatePacket(array);
////
////        System.out.println(" check received data ? " + data2.getPacket().getValue3());
//
//
////        for (byte b : data.getPacket())
////            System.out.print(b + ":");
////        System.out.println();
////
////        System.out.println("Validate " + packetFactory.validatePacket(packetFactory.getPacketArray(PACKET_PID)));
////
////        System.out.println("Update " + packetFactory.updatePacket(packetFactory.getPacketArray(PACKET_PID)));
////
////        System.out.println("getValue1 " + ((ClientPingPacket) packetFactory.getPacket(PACKET_PID)).getValue1());
////        System.out.println("getValue3 " + ((ClientPingPacket) packetFactory.getPacket(PACKET_PID)).getValue3());
//
////        packetFactory.buildPacket(new GameDataPacket());
//
////        SimplePacket basePacket = new SimplePacket();
////        basePacket.setValue1((byte) 0x0f);
////        basePacket.setValue2((short) 64);
////        basePacket.setValue3(123);
////        basePacket.setValue4(12);
////        basePacket.setValue5("HelloWorld!");
////        basePacket.setValue6(0.99999d);
////        basePacket.setValue7(0.5f);
////        basePacket.packData();
//
////        PacketPid
//
//
////        SimplePacketBuilder simplePacketBuilder = new SimplePacketBuilder();
//
//
////        ClientPingPacket queueData = new ClientPingPacket();
//////        queueData.setValue1((byte) 64);
//////        queueData.setValue2((short) 120);
//////        queueData.setValue3(69);
//////        queueData.setValue4(12343534534L);
//////        queueData.setValue5("HelloWorld!HelloWorld!HelloWorld!");
//////        queueData.setValue6(0.4f);
//////        queueData.setValue7(0.832456d);
//////
////        byte[] array = queueData.getPacketByteArray();
//////
////        ClientPingPacket queueData2 = new ClientPingPacket();
////        queueData2.setupPacket(array);
////        System.out.println(queueData2.getValue1());
////        System.out.println(queueData2.getValue2());
////        System.out.println(queueData2.getValue3());
////        System.out.println(queueData2.getValue4());
////        System.out.println(queueData2.getValue5());
////        System.out.println(queueData2.getValue6());
////        System.out.println(queueData2.getValue7());
//
////        System.out.println("END");
//    }
//
//    public static void main( String[] args ) {
//        new App();
//    }
//
////    private void init() {
////        try {
////            this.connection = new Connection(InetAddress.getByName(this.SERVER_IP), this.SERVER_PORT);
////            this.socket = new DatagramSocket();
////            this.isWorking = true;
////        } catch (UnknownHostException e) {
////            e.printStackTrace();
////        } catch (SocketException e) {
////            e.printStackTrace();
////        }
////    }
////
////    @Override
////    public void run() {
////        try {
////            this.sendPacket = new JoinPacket(API_KEY).getPacket(this.connection);
////            this.receivePacket = new DatagramPacket(this.buffer, this.buffer.length);
////            this.setGameStatus(GameStatus.IDLE);
////            this.setConnectionStatus(ConnectionStatus.DISCONNECTED);
////            while (this.isWorking) {
////                this.socket.send(this.sendPacket);
////                this.socket.receive(this.receivePacket);
////                if (PacketUtility.checkPacketCrc32(this.receivePacket.getData())) {
////                    switch (PacketUtility.getPacketType(this.receivePacket.getData())) {
////                        case BasePacket.QUEUE_PACKET:
////                            this.setConnectionStatus(ConnectionStatus.CONNECTED);
////                            this.handleAwaitingPacket();
////                            break;
////                        case BasePacket.GAME_PACKET:
////                            this.setConnectionStatus(ConnectionStatus.CONNECTED);
////                            break;
////                        case BasePacket.REJECT_PACKET:
////                            System.out.println("REJECT_PACKET");
////                            this.setConnectionStatus(ConnectionStatus.AUTHORIZING);
////                            sendPacket = new JoinPacket(API_KEY).getPacket(this.connection);
////                            break;
////                    }
////                } else {
////                    System.err.println("Packet corrupted!");
////                }
////                Thread.sleep(1000);
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////        this.socket.close();
//}
