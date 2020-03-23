package me.dedose.game.objects;

import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;

import java.awt.*;

public class Player extends GameObject {

    int counter=0; // this might move
    int counter2=0; //also might move

    public int idKey;

    public boolean onFloor = false;
    protected static final int maxHealth = 100; //starting health
    protected static final int maxAmmo = 50; //startin ammo
    protected static final int fireDelay = 2; // number of ticks between rapidfire shots
    protected int health; //var for player health
    protected int ammo = maxAmmo; //var for ammo
    protected boolean firing = false;

    public Player(int x, int y, ID id, int idKey) {
        super(x, y, id);
        health = maxHealth;
        this.idKey = idKey;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x, y, (int)Main.UNIT/2, (int)Main.UNIT/2);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    // set health
    public void setHealth(int h) {
        health = h;
    }

    // set firing state
    public boolean setFiringState(boolean state) {
        if (firing == state) return false;
        firing = state;
        return true;
    }

    // get firing state
    public boolean firingState() { return firing; }

    // change health
    public boolean changeHealth(int dh) {
        health += dh;
        if (health <= 0) {
            health = 0;
            return false;
        } else
            return true;
    }

    // change ammo
    public void changeAmmo(int da) {
        ammo += da;
        if (ammo < 0) {
            ammo = 0;
        } else if (ammo > maxAmmo) {
            ammo = maxAmmo;
        }
    }

    // are ded
    public boolean isAlive() {return health != 0;}
}
