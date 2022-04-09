package org.pipeman.dinjogame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.dinjogame.map.TileType;

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
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        Vector2 mouse = Main.camera.unProjectMousePos();
        Main.map.setCell((int) mouse.x / 16, (int) mouse.y / 16, i3 == 0 ? TileType.GRASS : TileType.EMPTY, true);
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
