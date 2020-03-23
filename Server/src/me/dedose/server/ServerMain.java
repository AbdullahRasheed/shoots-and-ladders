package me.dedose.server;

public class ServerMain {

    public static void main(String[] args) {
        Server server = new Server(25565);
        server.start();
    }
}
