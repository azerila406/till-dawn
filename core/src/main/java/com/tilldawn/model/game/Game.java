package com.tilldawn.model.game;

import com.tilldawn.Main;
import com.tilldawn.model.user.User;

import java.util.List;

public class Game {
    List<Bullet> Bullet;
    Player player;
    public final GameSettings settings;
    public float timePassed = 0;

    public GameSettings getSettings() {
        return settings;
    }

    public Game(User user, GameSettings settings) {
        player = new Player(user, 100, settings.heroType, settings.weaponType);
        this.settings = settings;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Bullet> getBullet() {
        return Bullet;
    }

    public void setBullet(List<Bullet> bullet) {
        Bullet = bullet;
    }

    public float getTotalTime() {
        return settings.totalTime;
    }

    public float getRemainingTime() {
        System.err.println(settings.totalTime + " IS " + timePassed);
        return settings.totalTime - timePassed;
    }

    public void update(float delta) {
        timePassed += delta;
    }
}
