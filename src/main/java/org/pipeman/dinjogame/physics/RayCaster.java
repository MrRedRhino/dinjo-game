package org.pipeman.dinjogame.physics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.dinjogame.Direction;

import static org.pipeman.dinjogame.Main.map;
import static java.lang.Math.sqrt;

public class RayCaster {

    public static TileRayCastResult rayCastToTile(Vector2 start, Vector2 dir, float maxDistance) {
        Vector2 rayUnitStepSize = new Vector2(
                (float) sqrt(1 + (dir.y / dir.x) * (dir.y / dir.x)),
                (float) sqrt(1 + (dir.x / dir.y) * (dir.x / dir.y))
        );

        Vector2 mapCheck = new Vector2();
        mapCheck.x = start.x;
        mapCheck.y = start.y;

        Vector2 rayLength1d = new Vector2();
        Vector2 step = new Vector2();

        if (dir.x < 0) {
            step.x = -1;
            rayLength1d.x = (start.x - mapCheck.x) * rayUnitStepSize.x;
        } else {
            step.x = 1;
            rayLength1d.x = ((mapCheck.x + 1) - start.x) * rayUnitStepSize.x;
        }

        if (dir.y > 0) {
            step.y = 1;
            rayLength1d.y = (start.y - mapCheck.y) * rayUnitStepSize.y;
        } else {
            step.y = -1;
            rayLength1d.y = (mapCheck.y + 1 - start.y) * rayUnitStepSize.y;
        }

        float distance = 0;
        while (distance < maxDistance) {
            if (rayLength1d.x < rayLength1d.y) {
                mapCheck.x += step.x;
                distance = rayLength1d.x;
                rayLength1d.x += rayUnitStepSize.x;
            } else {
                mapCheck.y += step.y;
                distance = rayLength1d.y;
                rayLength1d.y += rayUnitStepSize.y;
            }

            if (!map.getCell((int) mapCheck.x >> 4, (int) mapCheck.y >> 4).transparent) {
                return new TileRayCastResult(mapCheck, Direction.DOWN);
            }
        }
        return null;
    }

    public static TileRayCastResult debugRayCastToTile(Vector2 start, Vector2 dir, ShapeRenderer r, float maxDistance) {
        r.line(start.x, start.y, start.x + dir.x, start.y + dir.y);
        TileRayCastResult result = rayCastToTile(start, dir, maxDistance);
        if (result != null) {
            map.highlightCell(result.position, r);
            r.circle(result.position.x, result.position.y, 1);
        }

        return result;
    }
}
