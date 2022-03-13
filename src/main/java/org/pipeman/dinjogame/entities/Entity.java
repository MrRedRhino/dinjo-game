package org.pipeman.dinjogame.entities;

import org.pipeman.dinjogame.physics.Hitbox;

public class Entity {
    Hitbox hitbox;

    public void doSomething() {
        System.out.println(hitbox);
    }
}
