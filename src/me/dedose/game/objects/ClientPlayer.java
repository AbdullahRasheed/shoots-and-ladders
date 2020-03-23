package me.dedose.game.objects;

import me.dedose.game.client.Client;
import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;
import me.dedose.packets.PlayerPositionUpdatePacket;

import java.awt.*;
import java.util.LinkedList;

public class ClientPlayer extends Player {

    public int mx, my;

    private static final long INCREASE_AMMO = 100;
    private static final long FIRE_DELAY = 2;

    private Handler handler;
    private Client client;
    // checks if player is on the floor
    public ClientPlayer(int x, int y, ID id, int idKey, Handler handler, Client client) {
        super(x, y, id, idKey);
        this.handler = handler;
        health = maxHealth;
        this.client = client;
        this.idKey = idKey;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        // if the player moves to da otha side tp them ya
        if (x + getBounds().width < 0 || x > Main.WIDTH) {
            x = Math.floorMod(x, Main.WIDTH);
        }

        //neeger heeger
        // TODO: make dis less autsm bc of counter use
        counter2++;
        if (ammo < maxAmmo) counter++;
        if (counter%100==0){
            changeAmmo(1);
            counter = 0;
        }

        // handle pew pew
        if(firing && counter2>=fireDelay) {//so that you cant rapid fire faster than once every two ticks
            Fire();
            counter2 = 0;
        }

        velY += accelY;
        // terminal velocity
        if (velY > terminalVel) velY = (int)terminalVel;

        // stop collision with floor
        LinkedList<GameObject> copy = (LinkedList<GameObject>) handler.object.clone(); // quik fix
        for (GameObject gameObject : copy) {
            if (!(gameObject instanceof Floor)) continue;
            if (this.getBounds().intersects(gameObject.getBounds())) {
                onFloor = true;
                y = gameObject.getY() - getBounds().height;
                velY = 0;
                break;

            }
        }

        PlayerPositionUpdatePacket packet = new PlayerPositionUpdatePacket();
        packet.x = this.x/Main.UNIT;
        packet.y = this.y/Main.UNIT;
        client.sendObject(packet);
    }

    // PewPew
    public boolean Fire() {
        //
        // if (ammo == 0) return false;
        changeAmmo(-1);
        double mousedist = mouseDistance();
        double circx = ((mx-x-getBounds().width/2)/mousedist);
        double circy = ((my-y-getBounds().width/2)/mousedist);
        handler.addObject(new Bullet(x , y , ID.Bullet, "(you)", circx, circy, handler));
        return true;
    }

    // Get the cursor distance from the middle of the player
    private double mouseDistance() {
        return (Math.sqrt(Math.pow(mx-x-getBounds().width/2, 2)+Math.pow(my-y-getBounds().width/2, 2))); //pythag thrm aka pithigga therum
    }

    private static final int barLength = (int)(1.4 * Main.UNIT); //length of the health bar
    private static final int eyeDiameter = (int)(0.28 * Main.UNIT); //diameter of the eye
    private static final int pupilDiameter = (int)(0.12 * Main.UNIT); //pupil size

    @Override
    public void render(Graphics g) {
        //body of the player
        g.setColor(Color.black);
        g.fillRect(x, y, (int)Main.UNIT/2, (int)Main.UNIT/2);

        g.setColor(Color.decode("#ecf0f1")); //eye size
        g.fillOval(x + getBounds().width / 2 - eyeDiameter / 2, y + getBounds().width / 2 - eyeDiameter / 2, eyeDiameter, eyeDiameter);
        g.setColor(Color.decode("#1e272e")); //pupil

        // pupil movement and
        // eye of the player
        double mousedist = mouseDistance();
        if (mousedist > (eyeDiameter - pupilDiameter)/2) {
            double circx = ((mx-x-getBounds().width/2)/mousedist);
            double circy = ((my-y-getBounds().width/2)/mousedist);
            g.fillOval(x + getBounds().width / 2 - pupilDiameter/ 2 +(int)((eyeDiameter/2-pupilDiameter/2)*circx),
                    y + getBounds().width / 2 - pupilDiameter / 2+(int)((eyeDiameter/2-pupilDiameter/2)*circy),
                    pupilDiameter, pupilDiameter);
        } else {
            g.fillOval(mx - pupilDiameter / 2,
                    my - pupilDiameter / 2,
                    pupilDiameter, pupilDiameter);
        }

        // Health bar of the player
        g.setColor(Color.decode("#2c3e50"));
        g.fillRect(x + getBounds().width/2 - barLength / 2, (int)(y - Main.UNIT / 2), barLength, (int)(Main.UNIT/6));

        double p = (double)health / (double)maxHealth; //p is percentage of helff

        if (p != 1 && p != 0) p = 1 / (1 + Math.pow(p / (p - 1), -2)); //make percentage func logistic-like. Bro why not just make it linear?
        g.setColor(colorGrad(p, Color.decode("#FF0009"), Color.decode("#0be881"))); //first color is red, second is green
        g.fillRect(x + getBounds().width/2 - barLength / 2, (int)(y - Main.UNIT / 2), (int)(p * barLength), (int)(Main.UNIT/6));

        // Ammo the player has
        g.setColor(new Color(44, 62, 80, 127));
        g.setFont(new Font("Monospaced", Font.BOLD, (int)(Main.UNIT / 4)));

        //writes ammo left
        String am = ammo + "/" + maxAmmo;
        g.drawString(am, x + getBounds().width/2 - g.getFontMetrics().stringWidth(am)/2, (int)(y - Main.UNIT/10));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, (int)Main.UNIT/2, (int)Main.UNIT/2);
    }

    // calculate color gradient of health bar at given percentage
    private Color colorGrad(double percent, Color color1, Color color2) {
        int red = color1.getRed() + (int)(percent * (color2.getRed() - color1.getRed()));
        int green = color1.getGreen() + (int)(percent * (color2.getGreen() - color1.getGreen()));
        int blue = color1.getBlue() + (int)(percent * (color2.getBlue() - color1.getBlue()));
        return new Color (red, green, blue);
    }
}
