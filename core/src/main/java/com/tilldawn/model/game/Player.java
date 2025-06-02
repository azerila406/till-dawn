package com.tilldawn.model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.model.Ability.Ability;
import com.tilldawn.model.Assets;
import com.tilldawn.model.Vector;
import com.tilldawn.model.texture.Heros;
import com.tilldawn.model.texture.Textures;
import com.tilldawn.model.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private transient User user;
    private int health;
    private Vector pos;
    private float width = 32, height = 32;
    private int XP = 0;
    private int level = 1;
    private Weapon weapon;
    private int kills = 0;
    public final Heros heroType;
    private transient State state = State.IDLE;
    private float stateTime = 0f;
    private transient Animation<TextureRegion> animation;
    public final List<Ability> abilities = new ArrayList<>();
    private boolean facingRight;
    private float speed;

    public void setUser(User user) {
        this.user = user;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getXP() {
        return XP;
    }

    public int getMaxXP() {
        return getLevel() * 20;
    }

    public boolean addXP(int xp) {
        XP += xp;
        if (XP >= getMaxXP()) {
            int t = getMaxXP();
            addLevel(XP / getMaxXP());
            XP = XP % t;
            return true;
        }
        return false;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel(int level) {
        this.level += level;
    }

    public User getUser() {
        return user;
    }

    public Player(User user, int health, Heros heroType, WeaponType weaponType) {
        this.user = user;
        this.health = health;
        pos = new Vector();
        weapon = new Weapon(weaponType);
        this.heroType = heroType;
        animation = Assets.getHeroAnimation(heroType, state, 0.1f);
        this.speed = heroType.speed;
    }

    public void reload() {
        state = State.IDLE;
        animation = Assets.getHeroAnimation(heroType, state, 0.1f);
        weapon.reload();
    }

    public Player(User user, int health, Heros heroType, Weapon weapon, Vector pos) {
        this.user = user;
        this.health = health;
        this.pos = pos.copy();
        this.weapon = weapon;
        this.heroType = heroType;
        animation = Assets.getHeroAnimation(heroType, state, 0.1f);
        this.speed = heroType.speed;
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

    public void update(float delta) {
        stateTime += delta;
        abilities.removeIf(Ability::isDead);
    }

    public void setState(State state) {
        if (state != this.state) {
            this.state = state;
            animation = Assets.getHeroAnimation(heroType, state, 0.1f);
            stateTime = 0f;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = animation.getKeyFrame(stateTime, true);
        if ((facingRight && frame.isFlipX()) || (!facingRight && !frame.isFlipX())) {
            frame.flip(true, false);
        }
        batch.draw(frame, pos.x, pos.y, width, height);

    }

    public void renderWeapon(SpriteBatch batch, Vector direction) {
        weapon.renderWeapon(batch, direction, this);
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getKills() {
        return kills;
    }

    public void setSpeed(Vector direction) {
        direction.normalizeTo(speed * Gdx.graphics.getDeltaTime());

        if (direction.length() <= 0.1f) {
            setState(State.IDLE);
        } else {
            setState(State.RUN);
            pos.add(direction);
        }
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    private float lastHit = -1;
    public final static float BETWEEN_HIT = 1f;

    public void damage(Game game, int damage) {
        if (game.timePassed - lastHit <= BETWEEN_HIT) return;
        lastHit = game.timePassed;
        health -= damage;
    }

    public void handleKilling() {
        increaseKill(1);
    }

    private void increaseKill(int amount) {
        kills += amount;
    }

    public void onExit(float passedTime) {
        user.increaseKills(kills);
        user.setLongestTime((int) passedTime);
        user.increaseScore(getScore());
        user.update();
    }

    public int getScore() {
        return level * 10 + kills;
    }

    public void increaseHealth(int amount) {
        health += amount;
    }
}
