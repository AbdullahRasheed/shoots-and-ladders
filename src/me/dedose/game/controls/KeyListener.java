package me.dedose.game.controls;

import me.dedose.game.handlers.Handler;
import me.dedose.game.main.Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

    private Handler handler;
    public KeyListener(Handler handler){
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key){
            // for testing vv
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                handler.clientPlayer.changeAmmo(-1);
                handler.clientPlayer.changeHealth(-11);
                break;
            // for testing ^^

            // player movement
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if(handler.clientPlayer.onFloor){
                    handler.clientPlayer.setVelY(-(int)Main.UNIT/2);
                    handler.clientPlayer.onFloor = false;
                }
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                handler.clientPlayer.setVelX((int)Main.UNIT/9);
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                handler.clientPlayer.setVelX(-(int)Main.UNIT/9);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key){
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if(handler.clientPlayer.getVelX() == (int)Main.UNIT/9) handler.clientPlayer.setVelX(0);
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if(handler.clientPlayer.getVelX() == -(int)Main.UNIT/9) handler.clientPlayer.setVelX(0);
                break;
        }
    }
}
