package org.pipeman.dinjogame.physics.collisions;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Rect {
    public float x, y;
    public float width, height;

    public Vector2 velocity;

    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(ShapeRenderer r) {
        r.rect(x, y, width, height);
    }
}
