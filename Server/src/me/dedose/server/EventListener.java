package me.dedose.server;

import me.dedose.packets.AddConnectionPacket;
import me.dedose.packets.PlayerPositionUpdatePacket;
import me.dedose.packets.RemoveConnectionPacket;

public class EventListener {

    public void received(Object p,Connection connection) {
        if(p instanceof AddConnectionPacket) {
            AddConnectionPacket packet = (AddConnectionPacket)p;
            packet.id = connection.id;
            for(int i=0; i<ConnectionHandler.connections.size(); i++) {
                Connection c = ConnectionHandler.connections.get(i);
                if(c != connection) {
                    c.sendObject(packet);
                }
            }

            System.out.println("Connection: " + packet.id + " has connected");

        }else if(p instanceof RemoveConnectionPacket) {
            RemoveConnectionPacket packet = (RemoveConnectionPacket)p;
            System.out.println("Connection: " + packet.id + " has disconnected");
            ConnectionHandler.connections.get(packet.id).close();
            ConnectionHandler.connections.remove(packet.id);
        }else if(p instanceof PlayerPositionUpdatePacket){
            PlayerPositionUpdatePacket packet =(PlayerPositionUpdatePacket)p;
            packet.id = connection.id;
            for(int i = 0; i < ConnectionHandler.connections.size(); i++){
                Connection c = ConnectionHandler.connections.get(i);
                if(c != connection){
                     c.sendObject(packet);
                }
            }
        }
    }

}