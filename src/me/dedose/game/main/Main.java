package me.dedose.game.main;

import me.dedose.game.controls.KeyListener;
import me.dedose.game.controls.MouseListener;
import me.dedose.game.handlers.GameObject;
import me.dedose.game.handlers.Handler;
import me.dedose.game.handlers.ID;
import me.dedose.game.objects.Floor;
import me.dedose.game.objects.ClientPlayer;
import me.dedose.game.render.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width, //actual screen width
        SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height; //actual screen height
    public static final int WIDTH = Math.min(SCREEN_WIDTH, SCREEN_HEIGHT*3/2); //makes sure window ratio is 2 to 3
                        int HEIGHT = Math.min(SCREEN_HEIGHT, SCREEN_WIDTH*2/3)-10; //makes sure window ratio is 2 to 3
    public static final double UNIT = WIDTH/30; //universal unit. screen is 20 by 30 units

    private boolean running = false;

    private Thread thread;
    private Handler handler;

    // init
    public Main(){
        this.handler = new Handler();
        this.addKeyListener(new KeyListener(handler));
        this.addMouseListener(new MouseListener(handler)); // its making error so i coment

        //PLAYER
        ClientPlayer clientPlayer = new ClientPlayer((int)Main.UNIT*2, (int)Main.UNIT*2, ID.Player, handler);
        handler.setClientPlayer(clientPlayer);
        handler.addObject(clientPlayer);
        //FLOOR
        handler.addObject(new Floor(-50,(int)UNIT*17,ID.Floor,WIDTH + 50,10));
        new Window(WIDTH, HEIGHT, "Shoots and Ladders", this);
    }

    //Starts Thread
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    //Stops thread
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    // thread runnable
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        // runs this code while running
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            if(running) render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    // renders all registered gameobjects
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        g.dispose();
        bs.show();
    }

    // runs all registered gameobjects' tick method
    private void tick(){
        handler.tick();

        for (GameObject removePush : handler.removePush) {
            handler.object.remove(removePush);
        }
        handler.removePush.clear();
    }

    public static void main(String[] args){
        new Main();
    }
}
