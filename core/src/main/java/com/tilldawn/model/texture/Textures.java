package com.tilldawn.model.texture;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.model.Assets;
import com.tilldawn.model.game.WeaponType;

public enum Textures {
    AIM("aim.png", 32, 32),

    ;

    public final String path;
    public final int width;
    public final int height;

    Textures(String path, int width, int height) {
        this.path = path;
        this.width = width;
        this.height = height;
    }

    public static void queueAssets(AssetManager mgr) {
        for (Textures type : values()) {
            mgr.load(type.path, Texture.class);
        }
    }

    public Texture getTexture() {
        return Assets.get(path, Texture.class);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
