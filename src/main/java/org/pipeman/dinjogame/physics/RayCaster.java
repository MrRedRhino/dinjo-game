package org.pipeman.dinjogame.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.dinjogame.Main;
import org.pipeman.dinjogame.map.TileType;

public class RayCaster {
    static float radiant = 0;

    public static Vector2 rayCastToTile(Vector2 pos, Vector2 dir) {
        return null;
    }
    
    public static Vector2 debugRayCastToTile(Vector2 pos, Vector2 dir, ShapeRenderer r, int maxDistance) {
        radiant += 0.001;
        dir = new Vector2(1, 0).rotateRad(radiant);
        dir.nor();

        Vector2 firstHorizontalIntersection = new Vector2();
        firstHorizontalIntersection.x = dir.x < 0 ? (int) (pos.x / 16 + 1) * 16 : (int) (pos.x / 16) * 16;
        firstHorizontalIntersection.y = pos.y + (firstHorizontalIntersection.x - pos.x) / dir.x * dir.y;

        Vector2 firstVerticalIntersection = new Vector2();
        firstVerticalIntersection.y = (int) (dir.y < 0 ? pos.y / 16 + 1 : pos.y / 16) * 16;
        firstVerticalIntersection.x = pos.x - (pos.y - firstVerticalIntersection.y) / dir.y * dir.x;

        Vector2 verticalVec = new Vector2();
        verticalVec.x = 16 / dir.y * dir.x * sign(dir.y);
        verticalVec.y = sign(dir.y) * 16;

        Vector2 curPos2 = firstVerticalIntersection.cpy();

        Vector2 horizontalVec = new Vector2();
        horizontalVec.x = sign(dir.x) * 16;
        horizontalVec.y = 16 / dir.x * dir.y * sign(dir.x);

        Vector2 curPos = new Vector2(firstHorizontalIntersection);
        for (int i = 0; i < 100; i++) {
            curPos.add(horizontalVec);
            curPos2.add(verticalVec);

            r.setColor(Color.GOLDENROD);
            r.circle(curPos.x, curPos.y, 1); // vertical |
            r.circle(curPos2.x, curPos2.y, 2); // horizontal ---
            r.setColor(Color.MAGENTA);

            Vector2 tmp = curPos.cpy().add(-1, 0);
            if (!Main.map.getCell(tmp).transparent) r.circle(tmp.x, tmp.y, 3);


        }

        r.line(pos.x, pos.y, pos.x + dir.x * 100, pos.y + dir.y * 100);
        return null;
    }

    private static int sign(float i) {
        return i > 0 ? 1 : -1;
    }
}
