package org.pipeman.dinjogame.map;

import com.badlogic.gdx.graphics.Texture;

public enum TileType {
    DIRT(new Texture("dirt16.png"), false),
    GRASS(new Texture("grass16.png"), false),
    EMPTY(null, true);

    public final Texture texture;
    public final boolean transparent;

    TileType(Texture t, boolean transparent) {
        this.texture = t;
        this.transparent = transparent;
    }
}
