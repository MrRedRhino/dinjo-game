package org.pipeman.dinjogame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.dinjogame.Main;
import org.pipeman.dinjogame.map.TileType;
import org.pipeman.dinjogame.physics.Hitbox;
import org.pipeman.dinjogame.physics.RayCaster;

public class Entity {
    Sprite sprite;
    float posX;
    float posY;
    Vector2 velocity;
    Hitbox hitbox;
    boolean canJump;

    public Entity() {
        velocity = new Vector2();
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void tick() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && canJump) {
            velocity.y = 2f;
            canJump = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) velocity.x = -1f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) velocity.x = 1f;

        velocity.x *= 0.8f;
        velocity.y -= 0.1f;

        Main.camera.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Vector2 cell = RayCaster.debugRayCastToTile(
                getPos().add(8, 8),
                velocity.cpy(),
                Main.camera.shapeRenderer);

        posX += velocity.x;
        posY += velocity.y;

        if (cell != null) {
            int cellY = (int) cell.y >> 4 << 4;
            if (posY + 16 > cellY && velocity.y > 0) {
                velocity.y = 0;
                posY = cellY - 16;
            }
            if (posY - 16 < cellY && velocity.y < 0) {
                velocity.y = 0;
                posY = cellY + 16;
                canJump = true; // TODO Air-jump possible once
            }
        }

        if (posY < 0) posY = 140;
        Main.camera.shapeRenderer.end();
        sprite.setBounds(posX, posY, 16, 16);
    }

    public Vector2 getPos() {
        return new Vector2(posX, posY);
    }

    public void teleport(float x, float y) {
        posX = x;
        posY = y;
    }
}
