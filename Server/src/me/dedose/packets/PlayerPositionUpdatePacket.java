package me.dedose.packets;

import java.io.Serializable;

public class PlayerPositionUpdatePacket implements Serializable {

    private static final long serialVersionUID = 1L;

    public int id;
    public double x;
    public double y;
}