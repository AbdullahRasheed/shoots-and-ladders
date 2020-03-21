package me.dedose.game.objects;

import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.main.Main;

import java.awt.*;

public class ClientPlayer extends Player {

    private Handler handler;
    // checks if player is on the floor
    public ClientPlayer(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        health = maxHealth;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        // if the player moves to da otha side tp them ya
        if (x + getBounds().width < 0 || x > Main.WIDTH) {
            x = Math.floorMod(x, Main.WIDTH);
        }

        velY += accelY;
        // terminal velocity
        if (velY > terminalVel) velY = (int)terminalVel;

        // stop collision with floor
        for (GameObject gameObject : handler.object) {
            if (!(gameObject instanceof Floor)) continue;
            if (this.getBounds().intersects(gameObject.getBounds())) {
                onFloor = true;
                y = gameObject.getY() - getBounds().height;
                velY = 0;
                break;
            }
        }
    }

    private static final int barLength = (int)(1.4 * Main.UNIT); //length of the health bar
    private static final int eyeDiameter = (int)(0.28 * Main.UNIT); //diameter of the eye
    private static final int pupilDiameter = (int)(0.12 * Main.UNIT); //pupil size

    PointerInfo mouse = MouseInfo.getPointerInfo(); //mouse location
    Point mouseloc = mouse.getLocation();
    int mousex = (int) mouseloc.getX();
    int mousey = (int) mouseloc.getY();
    int mousedist = (int)(Math.pow(mousex, ))
    int circx= (int)

    // calculate color gradient of health bar at given percentage
    private Color colorGrad(double percent, Color color1, Color color2) {
        int red = color1.getRed() + (int)(percent * (color2.getRed() - color1.getRed()));
        int green = color1.getGreen() + (int)(percent * (color2.getGreen() - color1.getGreen()));
        int blue = color1.getBlue() + (int)(percent * (color2.getBlue() - color1.getBlue()));
        return new Color (red, green, blue);
    }

    @Override
    public void render(Graphics g) {
        //body of the player
        g.setColor(Color.black);
        g.fillRect(x, y, (int)Main.UNIT/2, (int)Main.UNIT/2);

        // eye of the player
        g.setColor(Color.decode("#ecf0f1")); //eye
        g.fillOval(x + getBounds().width / 2 - eyeDiameter / 2, y + getBounds().width / 2 - eyeDiameter / 2, eyeDiameter, eyeDiameter);
        g.setColor(Color.decode("#1e272e")); //pupil
        g.fillOval(x + getBounds().width / 2 - pupilDiameter/ 2, y + getBounds().width / 2 - pupilDiameter / 2,
                pupilDiameter, pupilDiameter);

        // Health bar of the player
        g.setColor(Color.decode("#2c3e50"));
        g.fillRect(x + getBounds().width/2 - barLength / 2, (int)(y - Main.UNIT / 2), barLength, (int)(Main.UNIT/6));
        double p = (double)health / (double)maxHealth; //p is percentage of helff
        // if (p != 1) p = 1 / (1 + Math.pow(p / (p - 1), -2)); <- bruh what? this shit makes no sense
        g.setColor(colorGrad(p, Color.decode("#EA2027"), Color.decode("#0be881"))); //first color is red, second is green
        g.fillRect(x + getBounds().width/2 - barLength / 2, (int)(y - Main.UNIT / 2), (int)(p * barLength), (int)(Main.UNIT/6));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, (int)Main.UNIT/2, (int)Main.UNIT/2);
    }
}
