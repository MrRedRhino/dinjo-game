package org.pipeman.dinjogame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class InputProcessor implements com.badlogic.gdx.InputProcessor {
    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Input.Keys.SLASH -> {
                Main.camera.camera.zoom += 0.1f;
                Main.camera.hasMoved = true;
            }
            case Input.Keys.RIGHT_BRACKET -> {
                Main.camera.camera.zoom -= 0.1f;
                Main.camera.hasMoved = true;
            }

            case Input.Keys.W -> Main.chickenEntity.addVelocity(new Vector2(0f, 1f));
            case Input.Keys.S -> Main.chickenEntity.addVelocity(new Vector2(0f, -1f));
            case Input.Keys.A -> Main.chickenEntity.addVelocity(new Vector2(-1f, 0f));
            case Input.Keys.D -> Main.chickenEntity.addVelocity(new Vector2(1f, 0f));
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        switch (i) {
            case Input.Keys.W, Input.Keys.S ->
                    Main.chickenEntity.setVelocity(new Vector2(Main.chickenEntity.getVelocity().x, 0));
            case Input.Keys.A, Input.Keys.D ->
                    Main.chickenEntity.setVelocity(new Vector2(0, Main.chickenEntity.getVelocity().y));
        }
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
