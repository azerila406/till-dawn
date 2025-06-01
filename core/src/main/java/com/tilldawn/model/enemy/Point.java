package com.tilldawn.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.model.Vector;
import com.tilldawn.model.texture.Textures;

public class Point {
    private Vector pos;
    private float width = 30, height = 30;
    public final Texture texture = Textures.SOFT.getTexture();
    public boolean isDead = false;

    public Point(Vector pos) {
        this.pos = pos.copy();
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
