package com.tilldawn.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.model.Vector;
import com.tilldawn.model.texture.Textures;

import java.io.Serializable;

public class Point implements Serializable {
    private Vector pos;
    private float width = 15, height = 15;
    public transient Texture texture = Textures.SOFT.getTexture();
    public boolean isDead = false;
    public final int XP;

    public Point(Vector pos, int xp) {
        this.pos = pos.copy();
        XP = xp;
    }

    public void reload() {
        texture = Textures.SOFT.getTexture();
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, width, height);
    }
}
