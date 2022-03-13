package org.pipeman.dinjogame.physics;

public class Hitbox {
    public float xOffset;
    public float yOffset;
    public float xSize;
    public float ySize;

    public Hitbox(float xOffset, float yOffset, float xSize, float ySize) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xSize = xSize;
        this.ySize = ySize;
    }
}
