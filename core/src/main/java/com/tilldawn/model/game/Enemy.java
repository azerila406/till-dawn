package com.tilldawn.model.game;

import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.model.Vector;

public class Enemy {
    private int health;
    private Vector pos;
    private float width = 20, height = 20;
    public final EnemyType enemyType;

    public Enemy(float x, float y, int health, EnemyType enemyType) {
        this.health = health;
        pos = new Vector(x, y);
        this.enemyType = enemyType;
    }

    public int getHealth() {
        return health;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public void update(float delta, Vector posToGo, float totalGame, float passed) {
        Vector direction = new Vector(posToGo).subtract(pos).normalize();
        pos.add(direction.scale(enemyType.getSpeed(totalGame, passed) * delta));
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }
}
