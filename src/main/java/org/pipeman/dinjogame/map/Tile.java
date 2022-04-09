package org.pipeman.dinjogame.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public record Tile(TileType t, int x, int y) {
    public Tile(TileType t, int x, int y) {
        this.t = t;
        this.x = x * 16;
        this.y = y * 16;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(t.texture, x, y);
    }

    @Override
    public String toString() {
        return "Tile: " + t;
    }
}
