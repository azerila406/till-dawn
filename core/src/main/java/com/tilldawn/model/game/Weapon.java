package com.tilldawn.model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.model.texture.Textures;

public class Weapon {
    private int ammo = 0;
    private int maxAmmo;
    private int reloadTime;
    private int projectile;
    public final Texture texture;

    public Weapon(int maxAmmo, int reloadTime, int projectile, Texture texture) {
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.projectile = projectile;
        this.texture = texture;
    }

    public Weapon(WeaponType type) {
        maxAmmo = type.ammoMax;
        projectile = type.projectile;
        reloadTime = type.timeReload;
        texture = type.getTexture();
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getProjectile() {
        return projectile;
    }

    public void setProjectile(int projectile) {
        this.projectile = projectile;
    }

    public Texture getTexture() {
        return texture;
    }

}
