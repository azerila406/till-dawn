package com.tilldawn.model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.model.Assets;
import com.tilldawn.model.Vector;
import com.tilldawn.model.texture.Textures;

import java.io.Serializable;

public class Bullet implements Serializable {
    public int damage;
    private Vector pos;
    private Vector direction;
    private float speed;
    private float width = 8, height = 8;
    private transient Texture texture = Textures.BULLET.getTexture();
    public boolean isDead = false;
    private BulletType bulletType;

    public BulletType getBulletType() {
        return bulletType;
    }

    public Bullet(int damage, Vector pos, Vector dir, float speed, BulletType bulletType) {
        this.damage = damage;
        this.pos = pos.copy();
        this.direction = dir.copy().normalizeTo(speed);
        this.speed = speed;
        this.bulletType = bulletType;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public void update(float delta) {
        Vector velocity = (new Vector(direction)).scale(speed * delta);
        pos.add(velocity);
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public void reload() {
        texture = Textures.BULLET.getTexture();
    }
}
