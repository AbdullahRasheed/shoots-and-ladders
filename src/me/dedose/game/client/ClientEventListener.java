package me.dedose.game.client;

import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;
import me.dedose.game.objects.Player;
import me.dedose.packets.AddConnectionPacket;
import me.dedose.packets.PlayerPositionUpdatePacket;
import me.dedose.packets.RemoveConnectionPacket;

public class ClientEventListener {

    private Handler handler;
    public ClientEventListener(Handler handler){
        this.handler = handler;
    }

    public void received(Object p) {
        if(p instanceof AddConnectionPacket) {
            AddConnectionPacket packet = (AddConnectionPacket)p;
            ClientConnectionHandler.connections.put(packet.id,new ClientConnection(packet.id));
            System.out.println(packet.id + " has connected");

            handler.addObject(new Player((int)Main.UNIT*2, (int)Main.UNIT*2, ID.Player, packet.id));
        }else if(p instanceof RemoveConnectionPacket) {
            RemoveConnectionPacket packet = (RemoveConnectionPacket)p;
            System.out.println("Connection: " + packet.id + " has disconnected");
            ClientConnectionHandler.connections.remove(packet.id);

            for (GameObject gameObject : handler.object) {
                if(!(gameObject instanceof Player)) continue;
                if(((Player)gameObject).idKey == packet.id) {
                    handler.removeObject(gameObject);
                }
            }
        }else if(p instanceof PlayerPositionUpdatePacket) {
            PlayerPositionUpdatePacket packet = (PlayerPositionUpdatePacket)p;
            for (GameObject gameObject : handler.object) {
                if(!(gameObject instanceof Player)) continue;
                if(((Player)gameObject).idKey == packet.id) {
                    gameObject.setX((int)(packet.x*Main.UNIT));
                    gameObject.setY((int)(packet.y*Main.UNIT));
                    return;
                }
            }
            handler.addObject(new Player((int)(packet.x*Main.UNIT), (int)(packet.y*Main.UNIT), ID.Player, packet.id));
        }
    }
}
