package org.pipeman.dinjogame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
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
    static Camera camera;
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

        for (int x = 0; x < 10; x++) {
            for (int y = 9; y >= 0; y--) {
                if (map.getCell(x, y + 1) == null) {
                    map.setCell(x, y, new Tile(TileType.GRASS, x, y));
                } else {
                    map.setCell(x, y, new Tile(TileType.DIRT, x, y));
                }
                System.out.println(x + " " + y);
            }
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
