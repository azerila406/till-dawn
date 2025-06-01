package com.tilldawn.model.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.model.Assets;

import java.awt.*;

public enum WeaponType {
    REVOLVER(20, 0, 1, 6, "revolver.png"),
    SHOTGUN(10, 3, 1, 2, "revolver.png"), //TODO
    SMG_DUAL(8, 0, 2, 24, "smg.png");

    public final int damage;
    public final int projectile;
    public final int timeReload;
    public final int ammoMax;
    public final String file;
    private Texture texture = null;

    WeaponType(int damage, int projectile, int timeReload, int ammoMax, String file) {
        this.damage = damage;
        this.projectile = projectile;
        this.timeReload = timeReload;
        this.ammoMax = ammoMax;
        this.file = file;
    }

    public static void queueAssets(AssetManager mgr) {
        for (WeaponType type : values()) {
            mgr.load(type.file, Texture.class);
        }
    }

    public Texture getTexture() {
        return Assets.get(file, Texture.class);
    }
}
