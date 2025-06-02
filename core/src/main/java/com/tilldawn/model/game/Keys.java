package com.tilldawn.model.game;

import com.badlogic.gdx.Input;

import java.io.Serializable;

public enum Keys implements Serializable {
    UP(Input.Keys.W),
    DOWN(Input.Keys.S),
    RIGHT(Input.Keys.D),
    LEFT(Input.Keys.A),
    RELOAD(Input.Keys.R);

    private int keyCode;

    Keys(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public String getKeyName() {
        return Input.Keys.toString(keyCode);
    }
}
