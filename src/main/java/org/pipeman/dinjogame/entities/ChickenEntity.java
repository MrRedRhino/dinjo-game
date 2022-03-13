package org.pipeman.dinjogame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.dinjogame.physics.Hitbox;
import org.pipeman.dinjogame.physics.PositionedHitbox;

public class ChickenEntity extends Entity {
    Sprite sprite;
    float posX;
    float posY;
    Vector2 velocity;
    Hitbox hitbox;

    public ChickenEntity(Texture t) {
        sprite = new Sprite(t);
        hitbox = new Hitbox(0f, 0f, 16f, 16f);
        velocity = new Vector2(0, 0);
    }

    public PositionedHitbox getHitbox() {
        return new PositionedHitbox(hitbox, posX, posY);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void tick() {
        posX += velocity.x;
        posY += velocity.y;
        sprite.setBounds(posX, posY, 16, 16);
    }

    public void addVelocity(Vector2 vel) {
        velocity.add(vel);
    }

    public void setVelocity(Vector2 vel) {
        velocity = vel;
    }

    public Vector2 getPos() {
        return new Vector2(posX, posY);
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
