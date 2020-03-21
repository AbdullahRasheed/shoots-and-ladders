package me.dedose.game.objects;

import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.ID;

import java.awt.*;

public class Floor extends GameObject {

    int width, height;
    public Floor(int x, int y, ID id, int width, int height) {
        super(x, y, id);
        this.width = width;
        this.height = height;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x,y,width,height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }
}
