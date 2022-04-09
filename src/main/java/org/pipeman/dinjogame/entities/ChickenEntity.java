package org.pipeman.dinjogame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.dinjogame.physics.Hitbox;

public class ChickenEntity extends Entity {

    public ChickenEntity(Texture t) {
        sprite = new Sprite(t);
        hitbox = new Hitbox(0f, 0f, 16f, 16f);
        velocity = new Vector2(0, 0);
    }
}
