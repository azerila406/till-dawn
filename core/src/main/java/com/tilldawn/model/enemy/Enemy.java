package com.tilldawn.model.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tilldawn.model.Vector;
import com.tilldawn.model.game.Game;

import java.io.Serializable;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Tree.class, name = "tree"),
    @JsonSubTypes.Type(value = TentacleMonster.class, name = "ten"),
    @JsonSubTypes.Type(value = EyeMonster.class, name = "eye")
})

public abstract class Enemy implements Serializable {
    private int health;
    private Vector pos;
    public float width, height;
    public EnemyType enemyType;
    public final static float DIST = 1000;
    private transient Animation<TextureRegion> animation;
    private boolean flip = false;
    public boolean isDead = false;

    public Enemy(Vector pos, EnemyType enemyType) {
        this.health = enemyType.HP;
        this.pos = pos;
        this.enemyType = enemyType;
        animation = enemyType.createAnimation(1f);
        this.width = enemyType.width;
        this.height = enemyType.height;
        System.err.println("CREATED ENEMY WITH HP: " + this.health);
    }

    public void reload() {
        animation = enemyType.createAnimation(1f);
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
        Vector direction = game.getPlayer().getPos().copy().subtract(pos);
        if (direction.length() < 6f) return;
        direction.normalize();
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
        System.err.println("DAMAGE: " + damage);
        System.err.println("HP: " + health);
        if (health <= 0) {
            isDead = true;
            game.points.add(new Point(pos, enemyType.award));
            game.getPlayer().handleKilling();
        }
        Vector direction = game.getPlayer().getPos().copy().subtract(pos).normalize();
        direction = direction.scale(-0.5f * enemyType.getRawSpeed()).rotate(0.001f);
        pos.add(direction);
        flip = direction.x < 0;
    }
}
