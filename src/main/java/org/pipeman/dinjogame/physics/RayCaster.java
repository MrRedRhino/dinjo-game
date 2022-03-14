package org.pipeman.dinjogame.physics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.dinjogame.Main;

public class RayCaster {
    static float radiant = 0;

    public static Vector2 rayCastToTile(Vector2 pos, Vector2 dir) {
        return null;
    }
    
    public static Vector2 debugRayCastToTile(Vector2 pos, Vector2 dir, ShapeRenderer r, int maxDistance) {
        radiant += 0.01;
        dir = new Vector2(0, 1).rotateRad(radiant);
        dir.nor();
        Vector2 firstIntersection = new Vector2();

        if (Math.abs(dir.x) > Math.abs(dir.y)) { // horizontal
            firstIntersection.x = dir.x < 0 ? (int) (pos.x / 16 + 1) * 16 : (int) (pos.x / 16) * 16;
            firstIntersection.y = pos.y + pos.x / dir.x * dir.y - firstIntersection.x / dir.x * dir.y;

            if (!Main.map.getCell(firstIntersection).transparent) {
                Main.map.highlightCell(firstIntersection, r);
            } else {
                for (int i = 0; i < maxDistance; i++) {
                    Vector2 pos2 = new Vector2(firstIntersection.x + (i + 1) * (dir.x < 0 ? 16 : -16),
                            firstIntersection.y + (16 * i + 16) / dir.x * dir.y * (dir.x < 0 ? -1 : 1));
                    r.circle(pos2.x, pos2.y, 1f);

                    if (!Main.map.getCell(pos2).transparent) {
                        Main.map.highlightCell(pos2, r);
                        break;
                    }
                }
            }
        } else {
            firstIntersection.y = (int) (dir.y < 0 ? pos.y / 16 : pos.y / 16 + 1) * 16;
            firstIntersection.x = pos.x - (firstIntersection.y - pos.y) / dir.y * dir.x;

            if (!Main.map.getCell(firstIntersection).transparent) {
                Main.map.highlightCell(firstIntersection, r);
            } else {
                for (int i = 0; i < maxDistance; i++) {
                    Vector2 pos2 = new Vector2(firstIntersection.x - (16 * i + 16) / dir.y * dir.x,
                            firstIntersection.y + (16 * i * sign(dir.y) + 16));
                    r.circle(pos2.x, pos2.y, 1f);

//                    if (!Main.map.getCell(pos2).transparent) {
//                        Main.map.highlightCell(pos2, r);
//                        break;
//                    }
                }
            }
        }

        r.circle(firstIntersection.x, firstIntersection.y, 1f);
        r.line(pos.x, pos.y, pos.x + dir.x * -100, pos.y + dir.y * 100);
        return null;
    }

    private static int sign(float i) {
        return i > 0 ? 1 : -1;
    }
}
