package org.pipeman.dinjogame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import org.pipeman.dinjogame.entities.ChickenEntity;
import org.pipeman.dinjogame.map.Tile;
import org.pipeman.dinjogame.map.TileType;
import org.pipeman.dinjogame.map.Tilemap;
import org.pipeman.dinjogame.physics.RayCaster;

public class Main extends ApplicationAdapter {
    public static Tilemap map;
    SpriteBatch batch;
    Texture dirtTexture;
    Texture grassTexture;
    public static Camera camera;
    int x = 0;
    public static ChickenEntity chickenEntity;
    public static InputMultiplexer inputMultiplexer;

    @Override
    public void create () {
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputProcessor());
        Gdx.input.setInputProcessor(inputMultiplexer);

        batch = new SpriteBatch();
        camera = new Camera(batch);

        chickenEntity = new ChickenEntity(new Texture("chicken.png"));

        map = new Tilemap();

        // left wall
        for (int i = 20; i > 10; i--) {
            tempSetCell(10, i);
        }
        // top
        for (int i = 10; i < 20; i++) {
            tempSetCell(i, 20);
        }
        // right wall
        for (int i = 20; i > 10; i--) {
            tempSetCell(20, i);
        }
        // floor
        for (int i = 10; i < 20; i++) {
            tempSetCell(i, 10);
        }
    }

    private void tempSetCell(int x, int y) {
        if (map.getCell(x, y + 1).transparent) {
            map.setCell(x, y, new Tile(TileType.GRASS, x, y));
        } else {
            map.setCell(x, y, new Tile(TileType.DIRT, x, y));
        }
    }

    @Override
    public void render () {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        chickenEntity.tick();

        batch.begin();
        map.draw(batch);
        chickenEntity.draw(batch);
        camera.setPos(chickenEntity.getPos().x, chickenEntity.getPos().y);
        batch.end();

        camera.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        camera.shapeRenderer.setColor(Color.RED);
        chickenEntity.getHitbox().drawDebugRect(camera.shapeRenderer);
        camera.shapeRenderer.setColor(Color.BLACK);
        map.drawDebugLines(camera.shapeRenderer);
        camera.shapeRenderer.setColor(Color.MAGENTA);
        RayCaster.debugRayCastToTile(
                chickenEntity.getPos().add(8, 8),
                chickenEntity.getVelocity().cpy(),
                camera.shapeRenderer, 5);
        camera.shapeRenderer.end();


        x += 100;
        camera.update();
        if (x == 1000) {
            camera.setPos(100, 0);
        }
    }

    @Override
    public void dispose () {

    }

    @Override
    public void resize (int width, int height) {
        camera.resize(width, height);
    }
}
