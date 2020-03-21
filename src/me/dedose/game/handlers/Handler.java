package me.dedose.game.handlers;

import me.dedose.game.objects.ClientPlayer;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<>();
    public LinkedList<GameObject> removePush = new LinkedList<>();

    public ClientPlayer clientPlayer;

    public void tick(){
        for (GameObject gameObject : object) {
            gameObject.tick();
        }
    }

    public void render(Graphics g){
        for (GameObject gameObject : object) {
            gameObject.render(g);
        }
    }

    public void setClientPlayer(ClientPlayer clientPlayer){
        this.clientPlayer = clientPlayer;
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.removePush.add(object);
    }
}
