package org.pipeman.dinjogame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.pipeman.dinjogame.entities.ChickenEntity;
import org.pipeman.dinjogame.map.TileType;
import org.pipeman.dinjogame.map.Tilemap;
import org.pipeman.dinjogame.physics.collisions.Rect;
import org.pipeman.dinjogame.physics.collisions.RectangleCollisions;

public class Main extends ApplicationAdapter {
    public static Tilemap map;
    SpriteBatch batch;
    public static Camera camera;
    public static ChickenEntity chickenEntity;
    public static InputMultiplexer inputMultiplexer;

    // tmp
    static Rect rectangle = new Rect(200, 400, 30, 40);

    @Override
    public void create() {
        System.out.println("something");
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputProcessor());
        Gdx.input.setInputProcessor(inputMultiplexer);

        batch = new SpriteBatch();
        camera = new Camera(batch);

        chickenEntity = new ChickenEntity(new Texture("chicken.png"));
        chickenEntity.teleport(200, 405);

        map = new Tilemap();

        // left wall
        for (int i = 20; i > 10; i--) {
            map.setCell(10, i, TileType.GRASS, true);
        }
        // top
        for (int i = 10; i < 20; i++) {
            map.setCell(i, 20, TileType.GRASS, true);
        }
        // right wall
        for (int i = 20; i > 10; i--) {
            map.setCell(20, i, TileType.GRASS, true);
        }
        // floor
        for (int i = 10; i < 20; i++) {
            map.setCell(i, 10, TileType.GRASS, true);
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        chickenEntity.tick();
        batch.begin();
        map.draw(batch);
        chickenEntity.draw(batch);
        camera.setPos(chickenEntity.getPos().x, chickenEntity.getPos().y);
        batch.end();

        camera.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        camera.shapeRenderer.setColor(Color.RED);
        chickenEntity.getHitbox().drawDebugRect(camera.shapeRenderer, chickenEntity.getPos());
        camera.shapeRenderer.setColor(Color.BLACK);
        map.drawDebugLines(camera.shapeRenderer);
        camera.shapeRenderer.setColor(Color.MAGENTA);

        // tmp
//        rectangle.draw(camera.shapeRenderer);
        Vector2 vec = camera.unProjectMousePos();
//        Rect r = new Rect(vec.x, vec.y, 20, 30);
//        if (RectangleCollisions.rectVsRect(r, rectangle)) camera.shapeRenderer.setColor(Color.BLACK);
//        r.draw(camera.shapeRenderer);

        camera.shapeRenderer.end();
        camera.update();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
    }
}
