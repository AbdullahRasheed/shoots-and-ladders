package me.dedose.game.render;

import me.dedose.game.client.Client;
import me.dedose.game.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends Canvas {

    int width, height;

    public Window(int width, int height, String title, Main main){
        this.width = width;
        this.height = height;
        JFrame frame = new JFrame(title);

        // TODO: make wOrk
        ImageIcon icon = new ImageIcon("icon.png");
        frame.setIconImage(icon.getImage());

        frame.setPreferredSize(getDimension());
        frame.setMaximumSize(getDimension());
        frame.setMinimumSize(getDimension());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        frame.add(main);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                main.client.close();
            }
        });

        main.start();

    }

    private Dimension getDimension(){
        return new Dimension(width, height);
    }
}
