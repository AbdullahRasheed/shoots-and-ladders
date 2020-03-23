package me.dedose.game.controls;

import me.dedose.game.handlers.Handler;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseMotionListener extends MouseMotionAdapter {

    private Handler handler;
    public MouseMotionListener(Handler handler){
        this.handler = handler;
    }
    //private boolean clicked = False;

    @Override
    public void mouseMoved(MouseEvent e) {
        // To get the player use handler.clientPlayer
        int x = e.getX();
        int y = e.getY();
        handler.clientPlayer.mx = x;
        handler.clientPlayer.my = y;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // To get the player use handler.clientPlayer
        int x = e.getX();
        int y = e.getY();
        handler.clientPlayer.mx = x;
        handler.clientPlayer.my = y;
        /*
        if (SwingUtilities.isLeftMouseButton(e)) {
            //clicked = True;
            //if (!clicked)
            handler.clientPlayer.Fire();
        }*/
    }
}
