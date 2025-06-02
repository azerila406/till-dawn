package com.tilldawn.controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tilldawn.Main;
import com.tilldawn.model.Ability.Ability;
import com.tilldawn.model.Ability.AbilityType;
import com.tilldawn.model.Vector;
import com.tilldawn.model.enemy.Enemy;
import com.tilldawn.model.game.Bullet;
import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Keys;
import com.tilldawn.model.game.Player;
import com.tilldawn.model.texture.Textures;
import com.tilldawn.view.*;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
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
        this.game.setController(this);
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
        if (keycode == Keys.RELOAD.getKeyCode()) {
            getPlayer().getWeapon().reload(game.timePassed);
        }
        if (keycode == Input.Keys.ESCAPE) {
            main.setScreen(new PauseScreen(this));
        }
        if (keycode == Input.Keys.SPACE) {
            toggleAutoAim();
        }
        if (keycode == Input.Keys.H) {
            getPlayer().increaseHealth(10);
        }
        if (keycode == Input.Keys.T) {
            game.timePassed += 5f;
        }
        if (keycode == Input.Keys.P) {
            getPlayer().addLevel(1);
            handleNewLevel();
        }
        if (keycode == Input.Keys.M) {
            game.spawnElder(false);
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

        if (keys[Keys.UP.getKeyCode()]) vector.y += 1;
        if (keys[Keys.DOWN.getKeyCode()]) vector.y -= 1;
        if (keys[Keys.LEFT.getKeyCode()]) vector.x -= 1;
        if (keys[Keys.RIGHT.getKeyCode()]) vector.x += 1;

        game.getPlayer().setSpeed(vector);

        game.update(delta);

        game.getPlayer().setFacingRight(screen.getMousePosition().x >= game.getPlayer().getX());

        boolean mousePressedNow = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        if (mousePressedNow && !mousePressed) {
            shoot();
        }
        mousePressed = mousePressedNow;

        if (game.getPlayer().getHealth() <= 0) {
            handleDeath();
        }
        if (game.getRemainingTime() <= 0) {
            handleWin();
        }
    }

    private void handleWin() {
        onExit();
        main.setScreen(new EndGameScreen(EndGameScreen.GameResult.WIN));
        main.setGame(null);
    }

    private void handleDeath() {
        onExit();
        main.setScreen(new EndGameScreen(EndGameScreen.GameResult.DEAD));
        main.setGame(null);
    }

    public void shoot() {
        Vector position = screen.getMousePosition();
        if (autoAim) {
            position = game.getPlayer().getPos().findNearest(game.getEnemies().stream().map(Enemy::getPos).toList()).copy();
            screen.setMouse(position);
        }
        game.shoot(position.subtract(game.getPlayer().getPos()));
        if (game.settings.autoReload)
            game.getPlayer().getWeapon().reload(game.timePassed);
    }

    public Player getPlayer() {
        return game.getPlayer();
    }

    public float getMaxXP() {
        return getPlayer().getMaxXP();
    }

    public void handleNewLevel() {
        main.setScreen(new NewLevelScreen(this));
    }

    public void choose(AbilityType type) {
        Ability ability = new Ability(type, game.timePassed);
        getPlayer().abilities.add(ability);
        ability.execute(game);
        main.setScreen(new GameScreen());
    }

    public void onExit() {
        getPlayer().onExit(game.timePassed);
    }

    public void giveUp() {
        onExit();
        main.setScreen(new EndGameScreen(EndGameScreen.GameResult.GAVE_UP));
        main.setGame(null);
    }

    public void saveAndExit() {
        save();
        onExit();
        main.setScreen(new MainMenuScreen());
    }

    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        final String jsonPath = getPlayer().getUser().getUsername() + ".json";
        final String filePath = getPlayer().getUser().getUsername() + ".ser";

        try (FileWriter writer = new FileWriter(jsonPath)) {
            mapper.writeValue(writer, game);
            System.err.println("Game saved to: " + jsonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(game);
            System.out.println("Game saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
