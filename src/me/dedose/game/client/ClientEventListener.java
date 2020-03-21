package me.dedose.game.client;

import me.dedose.game.client.packets.AddConnectionPacket;
import me.dedose.game.client.packets.RemoveConnectionPacket;

public class ClientEventListener {

    public void received(Object p) {
        if(p instanceof AddConnectionPacket) {
            AddConnectionPacket packet = (AddConnectionPacket)p;
            ClientConnectionHandler.connections.put(packet.id,new ClientConnection(packet.id));
            System.out.println(packet.id + " has connected");
        }else if(p instanceof RemoveConnectionPacket) {
            RemoveConnectionPacket packet = (RemoveConnectionPacket)p;
            System.out.println("Connection: " + packet.id + " has disconnected");
            ClientConnectionHandler.connections.remove(packet.id);
        }
    }
}
