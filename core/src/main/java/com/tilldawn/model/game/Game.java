package com.tilldawn.model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tilldawn.controller.GameController;
import com.tilldawn.model.Ability.Ability;
import com.tilldawn.model.Vector;
import com.tilldawn.model.enemy.*;
import com.tilldawn.model.enemy.Point;
import com.tilldawn.model.user.User;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {
    private transient GameController controller;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Enemy> enemies = new ArrayList<Enemy>();
    public List<Point> points = new ArrayList<>();
    Player player;
    public GameSettings settings;
    public float timePassed = 0;

    public void setController(GameController controller) {
        this.controller = controller;
    }

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

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void handleDead() {
        bullets.removeIf(bullet -> bullet.isDead);
        enemies.removeIf(enemy -> enemy.isDead);
        points.removeIf(point -> point.isDead);
    }

    public void update(float delta) {
        handleDead();
        timePassed += delta;
        for (Bullet bullet : bullets) {
            bullet.update(delta);
        }
        for (Enemy enemy: enemies) {
            enemy.update(delta, this);
        }
        for (Ability ability: player.abilities) {
            ability.update(this);
        }
        player.update(delta);
        spawnEnemy();

        handleCollision();
        handleDead();
    }

    private void handleCollision() {
        for (Bullet bullet : bullets) {
            Rectangle bulletR = bullet.getBounds();
            if (bullet.getBulletType() == BulletType.PLAYER) {
                for (Enemy enemy : enemies) {
                    Rectangle enemyR = enemy.getBounds();
                    if (enemyR.overlaps(bulletR)) {
                        bullet.isDead = true;
                        enemy.damage(this, bullet.damage);
                        break;
                    }
                }
            } else {
                Rectangle playerR = player.getBounds();
                if (playerR.overlaps(bulletR)) {
                    bullet.isDead = true;
                    player.damage(this, bullet.damage);

                }
            }
        }

        for (Enemy enemy : enemies) {
            Rectangle enemyR = enemy.getBounds();
            Rectangle playerR = player.getBounds();
            if (enemyR.overlaps(playerR)) {
                player.damage(this, 5);
            }
        }

        for (Point point : points) {
            Rectangle playerR = player.getBounds();
            Rectangle pointR = point.getBounds();
            if (playerR.overlaps(pointR)) {
                point.isDead = true;
                if (player.addXP(point.XP)) {
                    controller.handleNewLevel();
                }
            }
        }
    }

    private void spawnEnemy() {
        Enemy tentacle = TentacleMonster.trySpawn(timePassed, player);
        if (tentacle != null) {
            enemies.add(tentacle);
        }
        Enemy eye = EyeMonster.trySpawn(timePassed,  getTotalTime(), player);
        if (eye != null) {
            enemies.add(eye);
        }
        spawnElder(true);
    }

    public void spawnElder(boolean check) {
        Enemy elder = ElderMonster.trySpawn(timePassed, getTotalTime(), player, check);
        if (elder != null) {
            enemies.add(elder);
        }
    }

    public void shoot(Vector direction) {
        List<Bullet> bullets = player.getWeapon().shoot(timePassed, direction, player);
        if (bullets == null || bullets.isEmpty()) return;
        System.err.println(this.bullets.size());
        this.bullets.addAll(bullets);
        System.err.println(bullets.size());
    }

    public void render(SpriteBatch batch) {
        player.render(batch);

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }
        for (Enemy enemy: enemies) {
            enemy.render(batch, timePassed);
        }
        for (Point point: points) {
            point.render(batch);
        }
    }

    public void reload() {
        player.reload();

        for (Bullet bullet : bullets) {
            bullet.reload();
        }
        for (Enemy enemy: enemies) {
            enemy.reload();
        }
        for (Point point: points) {
            point.reload();
        }
    }
}
