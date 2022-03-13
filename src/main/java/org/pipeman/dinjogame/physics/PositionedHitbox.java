package org.pipeman.dinjogame.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PositionedHitbox {
    private float posX;
    private float posY;
    private float xOffset;
    private float yOffset;
    private float xSize;
    private float ySize;

    public PositionedHitbox(float posX1, float xOffset, float yOffset, float xSize, float ySize, float posX, float posY) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xSize = xSize;
        this.ySize = ySize;
        this.posX = posX1;
        this.posY = posY;
    }

    public PositionedHitbox(Hitbox hitbox, float posX, float posY) {
        this.xOffset = hitbox.xOffset;
        this.yOffset = hitbox.yOffset;
        this.xSize = hitbox.xSize;
        this.ySize = hitbox.ySize;
        this.posX = posX;
        this.posY = posY;
    }

    public void drawDebugRect(ShapeRenderer renderer) {
        renderer.rect(posX + xOffset, posY + yOffset, xSize, ySize);
    }
}
