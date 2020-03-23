package me.dedose.game.objects;

import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;

import java.awt.*;
import java.util.LinkedList;

public class Bullet extends GameObject {
    private static final int vel = (int)Main.UNIT / 4; // bullet velocity

    private Handler handler;

    String playerId;
    double veloX, veloY;
    double xtemp, ytemp;
    public Bullet(int x, int y, ID id, String playerId, double circx, double circy, Handler handler) {
        super(x, y, id);
        this.playerId = playerId;
        veloX = circx * vel;
        veloY = circy * vel;
        xtemp=x-getBounds().width/2+Main.UNIT/4; // temporary variable to make sure that the rounding to an int occurs on the x and not on the velox
        ytemp=y-getBounds().width/2+Main.UNIT/4; // temporary variable to make sure that the rounding to an int occurs on the y and not on the veloy

        this.handler = handler;
    }

    @Override
    public void tick() {
        xtemp += veloX;
        ytemp += veloY;
        x=(int)xtemp;
        y=(int)ytemp;

        //bullet appears on other side
        if (x + getBounds().width < 0 || x > Main.WIDTH) {
            x = Math.floorMod(x, Main.WIDTH);
        }

        // commit suicide if out of map
        if (y > Main.HEIGHT + Main.UNIT * 2 || y + Main.UNIT * 2 < 0) kms();

        // collision stuff
        LinkedList<GameObject> copy = (LinkedList<GameObject>) handler.object.clone(); // quik fix
        for (GameObject gameObject : copy) { // code smell xd
            //floor
            if (gameObject instanceof Floor && this.getBounds().intersects(gameObject.getBounds())) {
                //veloY = -veloY; // bounce
                // kms();
                break;
            }

            // bullet
            if (gameObject instanceof Bullet && gameObject != this && this.getBounds().intersects(gameObject.getBounds())) { // ignore if self

                break;
            }

            // player
        }
    }

    // die
    public void kms() {
        handler.removeObject(this); // die
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.decode("#2f3640"));
        g.fillOval(x, y, getBounds().width, getBounds().height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, (int)Main.UNIT / 8, (int)Main.UNIT / 8);
    }
}
