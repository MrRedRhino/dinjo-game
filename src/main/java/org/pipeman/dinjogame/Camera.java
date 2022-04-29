package org.pipeman.dinjogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Camera {
    final OrthographicCamera camera;
    public final SpriteBatch batch;
    public final ShapeRenderer shapeRenderer;
    Vector2 targetPos;
    boolean hasMoved = false;
    public BitmapFont font;

    public Camera(SpriteBatch batch) {
        camera = new OrthographicCamera();
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
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

    public Vector2 unProjectMousePos() {
        Vector3 vector3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Vector2(vector3.x, vector3.y);
    }
}
