package me.dedose.game.objects;

import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.ID;

import java.awt.*;

public class Player extends GameObject {

    public boolean onFloor = false;
    protected static final int maxHealth = 100;
    protected int health;
    public Player(int x, int y, ID id) {
        super(x, y, id);
        health = maxHealth;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    // set health
    public void setHealth(int h) {
        health = h;
    }

    // change health
    public boolean changeHealth(int dh) {
        health += dh;
        if (health <= 0) {
            health = 0;
            return false;
        } else
            return true;
    }

    // are ded
    public boolean isAlive() {return health != 0;}
}
