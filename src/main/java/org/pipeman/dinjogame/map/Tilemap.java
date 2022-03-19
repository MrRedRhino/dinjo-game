package org.pipeman.dinjogame.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class Tilemap {
    Map<Integer, TileRow> tiles = new HashMap<>(); // TODO tile type enum for storing them

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

    private void privateHighlightCell(int x, int y, ShapeRenderer r) {
        r.setColor(new Color(0, 0.5f, 0.5f, 0.1f));
        if (r.getCurrentType() != ShapeRenderer.ShapeType.Filled) {
            r.end();
            r.begin(ShapeRenderer.ShapeType.Filled);
        }
//        r.rect(x * 16, y * 16, 16, 16);
    }

    public void highlightCell(Vector2 publicPos, ShapeRenderer r) {
        privateHighlightCell((int) publicPos.x / 16, (int) publicPos.y / 16, r);
    }

    public void highlightCell(int x, int y, ShapeRenderer r) {
        privateHighlightCell(x, y, r);
    }

    public void setCell(int x, int y, Tile t) {
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

    public TileType getCell(int x, int y) {
        TileRow r = tiles.get(x);
        return r == null || r.tiles.get(y) == null ? TileType.EMPTY : r.tiles.get(y).t;
    }

    public TileType getCell(Vector2 publicPos) {
        TileRow r = tiles.get((int) publicPos.x / 16);
        return r == null || r.tiles.get((int) publicPos.y / 16) == null ?
                TileType.EMPTY : r.tiles.get((int) publicPos.y / 16).t;
    }
}

class TileRow {
    public HashMap<Integer, Tile> tiles;

    public TileRow() {
        tiles = new HashMap<>();
    }

    public TileRow(Tile newTile, int y) {
        tiles = new HashMap<>();
        tiles.put(y, newTile);
    }

    public void draw(SpriteBatch batch) {
        for (Tile t : tiles.values()) {
            t.draw(batch);
        }
    }

    public String toString() {
        return tiles.toString();
    }
}