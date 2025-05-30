package com.tilldawn.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.model.Assets;
import com.tilldawn.model.Vector;
import com.tilldawn.model.texture.Textures;

public abstract class Enemy {
    private int health;
    private Vector pos;
    public final float width, height;
    public final EnemyType enemyType;
    public final static float DIST = 1000;
    private final Animation<TextureRegion> animation;

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

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public void update(float delta, Vector posToGo, float totalGame, float passed) {
        Vector direction = new Vector(posToGo).subtract(pos).normalize();
        pos.add(direction.scale(getSpeed(totalGame, passed) * delta));
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

    public float getSpeed(float totalGame, float passed) {
        return enemyType.getSpeed(totalGame, passed);
    }

    public void render(SpriteBatch batch, float passed) {
        TextureRegion frame = animation.getKeyFrame(passed, true);

        batch.draw(frame, getX(), getY(), width, height);
    }

}
