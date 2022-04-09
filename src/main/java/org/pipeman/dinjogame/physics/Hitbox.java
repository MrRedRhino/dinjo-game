package org.pipeman.dinjogame.physics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public record Hitbox(float xOffset, float yOffset, float xSize, float ySize) {

    public void drawDebugRect(ShapeRenderer shapeRenderer, Vector2 pos) {
        shapeRenderer.rect(pos.x + xOffset, pos.y + yOffset, xSize, ySize);
    }
}
