package com.tilldawn.model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.model.Vector;
import com.tilldawn.model.enemy.Enemy;
import com.tilldawn.model.enemy.TentacleMonster;
import com.tilldawn.model.enemy.Tree;
import com.tilldawn.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Game {
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Enemy> enemies = new ArrayList<Enemy>();
    Player player;
    public final GameSettings settings;
    public float timePassed = 0;

    public GameSettings getSettings() {
        return settings;
    }

    public Game(User user, GameSettings settings) {
        player = new Player(user, 100, settings.heroType, settings.weaponType);
        this.settings = settings;
        spawnTree();
    }

    private void spawnTree() {
        for (int i = 0; i < 5000; i++) {
            enemies.add(Tree.trySpawn());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Bullet> getBullet() {
        return bullets;
    }

    public void setBullet(List<Bullet> bullet) {
        bullets = bullet;
    }

    public float getTotalTime() {
        return settings.totalTime;
    }

    public float getRemainingTime() {
        return settings.totalTime - timePassed;
    }

    public void update(float delta) {
        timePassed += delta;
        for (Bullet bullet : bullets) {
            bullet.update(delta);
        }
        for (Enemy enemy: enemies) {
            enemy.update(delta, player.getPos(), getTotalTime(), timePassed);
        }
        player.update(delta);
        spawnEnemy();
    }

    private void spawnEnemy() {
        Enemy tentacle = TentacleMonster.trySpawn(timePassed, player);
        if (tentacle != null) {
            enemies.add(tentacle);
        }
    }

    public void shoot(Vector direction) {
        Bullet bullet = player.getWeapon().shoot(timePassed, direction, player);
        if (bullet != null) {
            bullets.add(bullet);
        }
    }

    public void render(SpriteBatch batch) {
        player.render(batch);

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }
        for (Enemy enemy: enemies) {
            enemy.render(batch, timePassed);
        }
    }
}
