package com.tilldawn.model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.model.Vector;
import com.tilldawn.model.texture.Textures;

public class Weapon {
    private int ammo = 0;
    private int maxAmmo;
    private int reloadTime;
    private int projectile;
    public final Texture texture;

    private boolean isReloading = false;
    private float timeOfReload = 0.0f;

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
        ammo = maxAmmo;
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

    public void reload(float time) {
        updateReload(time);
        if (maxAmmo == ammo || isReloading) return;
        isReloading = true;
        timeOfReload = time;
        ammo = maxAmmo;
    }

    public void updateReload(float time) {
        if (isReloading && time - timeOfReload > reloadTime) {
            isReloading = false;
        }
    }

    public Bullet shoot(float time, Vector direction, Player player) {
        updateReload(time);
        if (isReloading || ammo == 0) return null;
        Vector bulletVector = getBulletSpawnPosition(direction, player);
        --ammo;
        return new Bullet(1, bulletVector, direction, 35);
    }

    public void renderWeapon(SpriteBatch batch, Vector direction, Player player) {
        TextureRegion weapon = new TextureRegion(texture);
        float angleDegrees = (float) Math.toDegrees(Math.atan2(direction.y, direction.x));
        int weaponWidth = 50;
        int weaponHeight = 35;
        float radius = 35;

        float angleRad = (float) Math.atan2(direction.y, direction.x);
        float offsetX = (float) Math.cos(angleRad) * radius;
        float offsetY = (float) Math.sin(angleRad) * radius;

        float centerX = player.getPos().x + player.getWidth() * 0.5f;
        float centerY = player.getPos().y + player.getHeight() * 0.5f;

        float weaponX = centerX + offsetX - weaponWidth * 0.5f;
        float weaponY = centerY + offsetY - weaponHeight * 0.5f;

        if (direction.x < 0 && !weapon.isFlipY()) {
            weapon.flip(false, true);
        } else if (direction.x >= 0 && weapon.isFlipY()) {
            weapon.flip(false, true);
        }

        batch.draw(
            weapon,
            weaponX, weaponY,
            weaponWidth * 0.5f,
            weaponHeight * 0.5f,
            weaponWidth, weaponHeight,
            1f, 1f,
            angleDegrees
        );
    }

    public Vector getBulletSpawnPosition(Vector direction, Player player) {
        float angleRad = (float) Math.atan2(direction.y, direction.x);
        float radius = 35;

        float centerX = player.getPos().x + player.getWidth() * 0.5f;
        float centerY = player.getPos().y + player.getHeight() * 0.5f;

        float bulletX = centerX + (float) Math.cos(angleRad) * radius;
        float bulletY = centerY + (float) Math.sin(angleRad) * radius;

        return new Vector(bulletX, bulletY);
    }

}
