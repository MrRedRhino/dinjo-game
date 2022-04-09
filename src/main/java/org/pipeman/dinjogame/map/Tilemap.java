package org.pipeman.dinjogame.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class Tilemap {
    final Map<Integer, TileRow> tiles = new HashMap<>();

    public void draw(SpriteBatch batch) {
        for (TileRow r : tiles.values()) {
            r.draw(batch);
        }
    }

    public void drawDebugLines(ShapeRenderer r) {
        for (int x = -50; x < 50; x++) {
            r.line(x * 16, 0, x * 16, Gdx.graphics.getHeight());
        }
        for (int y = -50; y < 50; y++) {
            r.line(0, y * 16, Gdx.graphics.getWidth() , y * 16);
        }
    }

    public void highlightCell(Vector2 publicPos, ShapeRenderer r) {
        r.setColor(new Color(0, 0.5f, 0.5f, 0.5f));
        if (r.getCurrentType() != ShapeRenderer.ShapeType.Filled) {
            r.end();
            r.begin(ShapeRenderer.ShapeType.Filled);
        }
        r.rect((int) publicPos.x >> 4 << 4, (int) publicPos.y >> 4 << 4, 16, 16);
    }

    public void setCell(int x, int y, Tile t, boolean update) {
        if (t.t() == TileType.EMPTY) {
            emptyCell(x, y);
        } else {
            if (tiles.containsKey(x)) {
                TileRow r = tiles.get(x);
                if (r.tiles.containsKey(y)) {
                    r.tiles.replace(y, t);
                } else {
                    r.tiles.put(y, t);
                }
            } else {
                tiles.put(x, new TileRow(t, y));
            }
        }

        if (!update) return;
        if (getCell(x, y - 1) == TileType.GRASS) {
            setCell(x, y - 1, TileType.DIRT, false);
        }
        if (getCell(x, y + 1) != TileType.EMPTY) {
            setCell(x, y, TileType.DIRT, false);
        }
    }

    public void emptyCell(int x, int y) {
        TileRow row = tiles.get(x);
        if (row != null) {
            row.tiles.remove(y);
            if (row.tiles.values().isEmpty()) {
                tiles.remove(x);
            }
        }
    }

    public void setCell(int x, int y, TileType t, boolean update) {
        setCell(x, y, new Tile(t, x, y), update);
    }

    public TileType getCell(int x, int y) {
        TileRow r = tiles.get(x);
        return r == null || r.tiles.get(y) == null ? TileType.EMPTY : r.tiles.get(y).t();
    }
}

class TileRow {
    public final HashMap<Integer, Tile> tiles;

    public TileRow(Tile newTile, int y) {
        tiles = new HashMap<>();
        tiles.put(y, newTile);
    }

    public void draw(SpriteBatch batch) {
        for (Tile t : tiles.values()) {
            t.draw(batch);
        }
    }

    @Override
    public String toString() {
        return tiles.toString();
    }
}