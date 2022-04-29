package org.pipeman.dinjogame.physics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

import java.util.ArrayList;

public record Hitbox(float xOffset, float yOffset, float xSize, float ySize) {

    public void drawDebugRect(ShapeRenderer shapeRenderer, Vector2 pos) {
        shapeRenderer.rect(pos.x + xOffset, pos.y + yOffset, xSize, ySize);
    }

    public ArrayList<TileRayCastResult> rayCastCorners(Vector2 chickenVel, Vector2 pos, ShapeRenderer r) {
        ArrayList<TileRayCastResult> out = new ArrayList<>();
        if (chickenVel.x < 0) {
            r.circle(pos.x, pos.y, 2);
            out.add(utilRayCast(pos.x, pos.y, chickenVel, r));
            r.circle(pos.x, pos.y + ySize, 2);
            out.add(utilRayCast(pos.x, pos.y + ySize, chickenVel, r));

            if (chickenVel.y < 0) {
                r.circle(pos.x + xSize, pos.y, 2);
                out.add(utilRayCast(pos.x + xSize, pos.y, chickenVel, r));
            } else {
                r.circle(pos.x + xSize, pos.y + ySize, 2);
                out.add(utilRayCast(pos.x + xSize, pos.y + ySize, chickenVel, r));
            }
        } else {
            r.circle(pos.x + xSize, pos.y + ySize, 2);
            out.add(utilRayCast(pos.x + xSize, pos.y + ySize, chickenVel, r));
            r.circle(pos.x + xSize, pos.y, 2);
            out.add(utilRayCast(pos.x + xSize, pos.y, chickenVel, r));

            if (chickenVel.y < 0) {
                r.circle(pos.x, pos.y, 2);
                out.add(utilRayCast(pos.x, pos.y, chickenVel, r));
            } else {
                r.circle(pos.x, pos.y + ySize, 2);
                out.add(utilRayCast(pos.x, pos.y + ySize, chickenVel, r));
            }
        }

        return out;
    }

    private TileRayCastResult utilRayCast(float x, float y, Vector2 dir, ShapeRenderer r) {
//        return RayCaster.debugRayCastToTile(new Vector2(x, y), dir, r, dir.len());
        return RayCaster.rayCastToTile(new Vector2(x, y), dir, dir.len());
    }
}
