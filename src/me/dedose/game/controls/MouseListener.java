package me.dedose.game.controls;

import me.dedose.game.handlers.Handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {

    private Handler handler;
    public MouseListener(Handler handler){
        this.handler = handler;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // To get the player use handler.clientPlayer
        int x = e.getX();
        int y = e.getY();
    }
}
