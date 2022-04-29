package org.pipeman.dinjogame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.dinjogame.Main;
import org.pipeman.dinjogame.physics.Hitbox;
import org.pipeman.dinjogame.physics.TileRayCastResult;

import java.util.ArrayList;

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
        if (Gdx.input.isKeyPressed(Input.Keys.A) && velocity.x > -4f) {
            velocity.x -= 0.3f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && velocity.x < 4f) {
            velocity.x += 0.3f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && canJump) {
            velocity.y = 5f;
            canJump = false;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x *= canJump ? 0.8f : 0.95f;
        }

        Main.camera.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        Vector2 pos = getPos();

        ArrayList<TileRayCastResult> results = hitbox.rayCastCorners(velocity.cpy(), pos, Main.camera.shapeRenderer);

        boolean hasCollidedY = false;
        for (TileRayCastResult rayCastResult : results) {

            if (rayCastResult != null) {
                Vector2 cell = rayCastResult.position;

                int cellBottom = (int) cell.y >> 4 << 4;
                int cellLeft = (int) cell.x >> 4 << 4;
                int cellRight = cellLeft + 16;
                int cellTop = cellBottom + 16;

                float eLeft = pos.x;
                float eRight = pos.x + 16;
                float eTop = pos.y + 16;
                float eBottom = pos.y;

                float distToCorrectVerticalPosRight = cellRight - eLeft;
                float distToCorrectVerticalPosLeft = cellLeft - eRight;

                float distToCorrectHorizontalPosTop = cellTop - eBottom;
                float distToCorrectHorizontalPosBottom = cellBottom - eTop;

                boolean upside = Math.abs(distToCorrectHorizontalPosTop) < Math.abs(distToCorrectHorizontalPosBottom);
                float distanceY = upside ? distToCorrectHorizontalPosTop : distToCorrectHorizontalPosBottom;

                boolean rightSmaller = Math.abs(distToCorrectVerticalPosRight) < Math.abs(distToCorrectVerticalPosLeft);
                float distanceX = rightSmaller ? distToCorrectVerticalPosRight : distToCorrectVerticalPosLeft;

                float hIntersection = 16 - Math.abs(eTop - cellTop);

                if (Math.abs(distanceX) < Math.abs(distanceY)) {
                    if (hIntersection > 0.4) {
                        posX += distanceX;
                        velocity.x = 0;
                    }
                } else {
                    if (!hasCollidedY) {
                        posY += distanceY;
                        velocity.y = 0;
                        canJump = upside;
                        hasCollidedY = true;
                    }
                }
            }
        }
        velocity.y -= 0.2f;

        posX += velocity.x;
        posY += velocity.y;

        Main.camera.shapeRenderer.end();
        sprite.setBounds(posX, posY, 16, 16);
    }

    public Vector2 getPos() {
        return new Vector2(posX, posY);
    }

    public void teleport(float x, float y) {
        posX = x;
        posY = y;
        velocity = new Vector2();
    }
}
