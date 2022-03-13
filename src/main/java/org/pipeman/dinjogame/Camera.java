package org.pipeman.dinjogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Camera {
    OrthographicCamera camera;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Vector2 targetPos;
    boolean hasMoved = false;

    public Camera(SpriteBatch batch) {
        camera = new OrthographicCamera();
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
    }

    public void update() {
        if (targetPos != null) {
            camera.position.x += (targetPos.x - camera.position.x) / 7;
            camera.position.y += (targetPos.y - camera.position.y) / 7;
            hasMoved = true;
        }

        if (hasMoved) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            shapeRenderer.setProjectionMatrix(camera.combined);
            hasMoved = false;
        }
    }

    public void setPos(float x, float y) {
        targetPos = new Vector2(x, y);
        hasMoved = true;
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        hasMoved = true;
    }
}
