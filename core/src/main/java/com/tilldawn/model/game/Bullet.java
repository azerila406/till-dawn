package com.tilldawn.model.game;

import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.model.Vector;

public class Bullet {
    private int damage;
    private Vector pos;
    private Vector direction;
    private float speed;
    private float width = 8, height = 8;

    public Bullet(int damage, float x, float y, float dirX, float dirY, float speed) {
        this.damage = damage;
        this.pos = new Vector(x, y);
        this.direction = new Vector(dirX, dirY).normalizeTo(speed);
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public void update(float delta) {
        Vector velocity = new Vector(direction).scale(speed * delta);
        pos.add(velocity);
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }
}
