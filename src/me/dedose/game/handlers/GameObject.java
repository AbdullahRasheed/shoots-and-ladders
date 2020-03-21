package me.dedose.game.handlers;

import me.dedose.game.main.Main;

import java.awt.*;

public abstract class GameObject {

    public static final double accelY = Main.UNIT/20.0; // gravitational acceleration for player
    public static final double terminalVel = 0.4*Main.UNIT;  //terminal velocity of player

    protected int x, y; // coordinates of player
    protected ID id; //player id
    protected int velX, velY; // velocities of player

    public GameObject(int x, int y, ID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setVelX(int velX){
        this.velX = velX;
    }

    public void setVelY(int velY){
        this.velY = velY;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getVelX(){
        return velX;
    }

    public int getVelY(){
        return velY;
    }

    public void setId(ID id){
        this.id = id;
    }

    public ID getId(){
        return id;
    }

}
