package com.tilldawn.controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Main;
import com.tilldawn.model.Vector;
import com.tilldawn.model.enemy.Enemy;
import com.tilldawn.model.game.Bullet;
import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Player;
import com.tilldawn.model.texture.Textures;
import com.tilldawn.view.GameScreen;
import com.tilldawn.view.PauseScreen;

import java.util.Arrays;

public class GameController {
    private final Main main = Main.getInstance();
    private final Game game;
    private final GameScreen screen;

    private final boolean[] keys = new boolean[256];
    boolean mousePressed = false;

    private boolean autoAim = false;

    public GameController(Game game, GameScreen screen) {
        this.game = game;
        this.screen = screen;
    }

    public void handleKeyDown(int keycode) {
        if (keycode >= keys.length)
            return;
        if (!keys[keycode]) {
            onKeyPress(keycode);
        }
        keys[keycode] = true;
    }

    public void onKeyPress(int keycode) {
        if (keycode == Input.Keys.R) {
            getPlayer().getWeapon().reload(game.timePassed);
        }
        if (keycode == Input.Keys.ESCAPE) {
            main.setScreen(new PauseScreen());
        }
        if (keycode == Input.Keys.SPACE) {
            System.err.println("AUTO AIM ENABLED");
            toggleAutoAim();
        }
    }

    private void toggleAutoAim() {
        autoAim = !autoAim;
    }

    public void handleKeyUp(int keycode) {
        if (keycode >= keys.length)
            return;
        keys[keycode] = false;
    }


    public void render(SpriteBatch batch) {
        game.render(batch);

        Vector position = screen.getMousePosition();
        batch.draw(Textures.AIM.getTexture(), (float) (position.x - Textures.AIM.getWidth() * 0.5), (float) (position.y - Textures.AIM.getHeight() * 0.5));

        game.getPlayer().renderWeapon(batch, screen.getMousePosition().subtract(game.getPlayer().getPos()));
    }

    public int getXp() {
        return game.getPlayer().getXP();
    }

    public int getLevel() {
        return game.getPlayer().getLevel();
    }

    public int getAmmo() {
        return game.getPlayer().getWeapon().getAmmo();
    }

    public int getHp() {
        return game.getPlayer().getHealth();
    }

    public int getRemainingTime() {
        return (int) game.getRemainingTime();
    }

    public int getKills() {
        return game.getPlayer().getKills();
    }

    public void update(float delta) {
        Vector vector = new Vector();

        if (keys[Input.Keys.W]) vector.y += 1;
        if (keys[Input.Keys.S]) vector.y -= 1;
        if (keys[Input.Keys.A]) vector.x -= 1;
        if (keys[Input.Keys.D]) vector.x += 1;

        game.getPlayer().setSpeed(vector);

        game.update(delta);

        game.getPlayer().setFacingRight(screen.getMousePosition().x >= game.getPlayer().getX());

        boolean mousePressedNow = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        if (mousePressedNow && !mousePressed) {
            shoot();
        }
        mousePressed = mousePressedNow;
    }

    public void shoot() {
        Vector position = screen.getMousePosition();
        if (autoAim) {
            position = game.getPlayer().getPos().findNearest(game.getEnemies().stream().map(Enemy::getPos).toList()).copy();
            screen.setMouse(position);
        }
        game.shoot(position.subtract(game.getPlayer().getPos()));
    }

    public Player getPlayer() {
        return game.getPlayer();
    }
}
