package com.tilldawn.model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.controller.GameController;
import com.tilldawn.model.Assets;
import com.tilldawn.model.Vector;
import com.tilldawn.model.texture.Heros;
import com.tilldawn.model.texture.Textures;
import com.tilldawn.model.user.User;

public class Player {
    private final User user;
    private int health;
    private Vector pos;
    private float width = 32, height = 32;
    private int XP = 0;
    private int level = 0;
    private Weapon weapon;
    private int kills = 0;
    private final Heros heroType;
    private State state = State.IDLE;
    private float stateTime = 0f;
    private Animation<TextureRegion> animation;
    private boolean facingRight;


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

    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        TextureRegion weapon = new TextureRegion(this.weapon.texture);
        float angleDegrees = (float) Math.toDegrees(Math.atan2(direction.y, direction.x));
        int weaponWidth = 50;
        int weaponHeight = 35;
        float radius = 35;

        float angleRad = (float) Math.atan2(direction.y, direction.x);
        float offsetX = (float) Math.cos(angleRad) * radius;
        float offsetY = (float) Math.sin(angleRad) * radius;

        float centerX = pos.x + width * 0.5f;
        float centerY = pos.y + height * 0.5f;

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
        float speed = 100 * Gdx.graphics.getDeltaTime();
        direction.normalizeTo(speed);

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
}
