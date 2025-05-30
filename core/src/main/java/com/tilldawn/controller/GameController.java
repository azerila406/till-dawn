package com.tilldawn.controller;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Main;
import com.tilldawn.model.Vector;
import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Player;
import com.tilldawn.model.texture.Textures;
import com.tilldawn.view.GameScreen;

public class GameController {
    private final Main main = Main.getInstance();
    private final Game game;
    private final GameScreen screen;

    private final boolean[] keys = new boolean[256];

    public GameController(Game game, GameScreen screen) {
        this.game = game;
        this.screen = screen;
    }

    public void handleKeyDown(int keycode) {
        if (keycode < keys.length) {
            keys[keycode] = true;
        }
    }

    public void handleKeyUp(int keycode) {
        if (keycode < keys.length) {
            keys[keycode] = false;
        }
    }


    public void render(SpriteBatch batch) {
        game.getPlayer().render(batch);

        Vector position = screen.getMousePosition(game.getPlayer());
        batch.draw(Textures.AIM.getTexture(), (float) (position.x - Textures.AIM.getWidth() * 0.5), (float) (position.y - Textures.AIM.getHeight() * 0.5));

        game.getPlayer().renderWeapon(batch, screen.getMousePosition(game.getPlayer()).subtract(game.getPlayer().getPos()));
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
        game.getPlayer().update(delta);

        game.getPlayer().setFacingRight(screen.getMousePosition(game.getPlayer()).x >= game.getPlayer().getX());

    }

    public Player getPlayer() {
        return game.getPlayer();
    }
}
