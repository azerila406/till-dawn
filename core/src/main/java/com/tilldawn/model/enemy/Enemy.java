package com.tilldawn.model.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.model.Vector;
import com.tilldawn.model.game.Game;

public abstract class Enemy {
    private int health;
    private Vector pos;
    public final float width, height;
    public final EnemyType enemyType;
    public final static float DIST = 1000;
    private final Animation<TextureRegion> animation;
    private boolean flip = false;
    public boolean isDead = false;

    public Enemy(Vector pos, EnemyType enemyType) {
        this.health = enemyType.HP;
        this.pos = pos;
        this.enemyType = enemyType;
        animation = enemyType.createAnimation(1f);
        this.width = enemyType.width;
        this.height = enemyType.height;
    }

    public int getHealth() {
        return health;
    }

    public Vector getPos() {
        return pos;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public void update(float delta, Game game) {
        Vector direction = game.getPlayer().getPos().copy().subtract(pos).normalize();
        pos.add(direction.scale(getSpeed(game.getTotalTime(), game.timePassed) * delta));
        flip = direction.x < 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

    public float getSpeed(float totalGame, float passed) {
        return enemyType.getSpeed(totalGame, passed);
    }

    public void render(SpriteBatch batch, float passed) {
        TextureRegion frame = animation.getKeyFrame(passed, true);

        if (frame.isFlipX() != flip) {
            frame.flip(true, false);
        }
        batch.draw(frame, getX(), getY(), width, height);
    }

    public void damage(Game game, int damage) {
        if (isDead) return;
        health -= damage;
        if (health <= 0) {
            isDead = true;
            game.points.add(new Point(pos));
        }
        Vector direction = game.getPlayer().getPos().copy().subtract(pos).normalize();
        direction = direction.scale(-0.5f * getSpeed(game.getTotalTime(), game.timePassed)).rotate(0.001f);
        pos.add(direction);
        flip = direction.x < 0;
    }
}
