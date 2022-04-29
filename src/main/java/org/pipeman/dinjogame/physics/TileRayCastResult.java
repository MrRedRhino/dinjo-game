package org.pipeman.dinjogame.physics;

import com.badlogic.gdx.math.Vector2;
import org.pipeman.dinjogame.Direction;

public class TileRayCastResult {
    public final Vector2 position;
    public final Direction hitSide;

    public TileRayCastResult(Vector2 position, Direction direction) {
        this.position = position;
        this.hitSide = direction;
    }
}
