package org.pipeman.dinjogame.physics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import static org.pipeman.dinjogame.Main.map;
import static java.lang.Math.sqrt;

public class RayCaster {
    static final float maxDistance = 400;

    public static Vector2 rayCastToTile(Vector2 start, Vector2 dir) {
        dir.nor();

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

            if (!map.getCell((int) mapCheck.x / 16, (int) mapCheck.y / 16).transparent) {
                return mapCheck;
            }
        }
        return null;
    }
    
    public static Vector2 debugRayCastToTile(Vector2 start, Vector2 dir, ShapeRenderer r) {
        r.line(start.x, start.y, start.x + dir.x * 100, start.y + dir.y * 100);
        Vector2 result = rayCastToTile(start, dir);
        if (result != null) {
            map.highlightCell(result, r);
            r.circle(result.x, result.y, 1);
        }

        return result;
    }
}
