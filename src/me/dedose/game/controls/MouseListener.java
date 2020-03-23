package me.dedose.game.controls;

import me.dedose.game.handlers.Handler;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class MouseListener extends MouseAdapter {

    private Handler handler;
    public MouseListener(Handler handler){
        this.handler = handler;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            handler.clientPlayer.setFiringState(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            handler.clientPlayer.setFiringState(false);
        }
    }
}
