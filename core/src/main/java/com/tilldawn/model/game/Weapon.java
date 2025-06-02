package com.tilldawn.model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.model.Vector;
import com.tilldawn.model.texture.Textures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Weapon implements Serializable {
    private int ammo = 0;
    private int maxAmmo;
    private int reloadTime;
    private int projectile;
    public transient Texture texture;
    public final WeaponType weaponType;

    public int damage;

    private boolean isReloading = false;
    private float timeOfReload = 0.0f;

    public Weapon(WeaponType type) {
        maxAmmo = type.ammoMax;
        projectile = type.projectile;
        reloadTime = type.timeReload;
        ammo = maxAmmo;
        texture = type.getTexture();
        this.damage = type.damage;
        this.weaponType = type;
    }

    public void reload() {
        texture = weaponType.getTexture();
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

    private Vector rotateVector(Vector vec, float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float x = vec.x * cos - vec.y * sin;
        float y = vec.x * sin + vec.y * cos;
        return new Vector(x, y);
    }

    public static final int SHOOT_SPEED  = 15;
    public static final float ANGLE_OFFSET = (float) Math.toRadians(10);
    public List<Bullet> shoot(float time, Vector direction, Player player) {
        updateReload(time);
        if (isReloading || ammo == 0) return null;
        Vector bulletVector = getBulletSpawnPosition(direction, player);
        --ammo;

        List<Bullet> bullets = new ArrayList<>();
        bullets.add(new Bullet(damage, bulletVector, direction, SHOOT_SPEED, BulletType.PLAYER));

        for (int i = 1; i <= projectile; i += 1) {
            float angle = ANGLE_OFFSET * i;

            Vector rotatedLeft = rotateVector(direction, -angle);
            Vector rotatedRight = rotateVector(direction, angle);

            bullets.add(new Bullet(damage, bulletVector, rotatedLeft, SHOOT_SPEED, BulletType.PLAYER));
            bullets.add(new Bullet(damage, bulletVector, rotatedRight, SHOOT_SPEED, BulletType.PLAYER));
        }

        return bullets;
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


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
