package com.tilldawn.model.enemy;

import com.tilldawn.model.Vector;
import com.tilldawn.model.game.Bullet;
import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Player;

import java.util.List;

public class EyeMonster extends Enemy {

    private static float lastSpawnTime = 0f;
    private static int numOfSpawns = 0;

    public EyeMonster(Vector pos) {
        super(pos, EnemyType.EYEBAT);
    }

    public static final float DIST = 500f;
    public static final float SPEED = 10f;
    public static final float BETWEEN_SHOT = 5f;


    public float lastShot = 0.0f;

    @Override
    public void update(float delta, Game game) {
        super.update(delta, game);
        if (game.timePassed - lastShot <= BETWEEN_SHOT) return;
        List<Bullet> bullets = game.getBullet();
        Vector vector = game.getPlayer().getPos().copy().subtract(this.getPos());
        if (vector.length() < DIST) {
            bullets.add(new Bullet(5, this.getPos(), vector.normalize(), SPEED));
        }
        lastShot = game.timePassed;
    }

    public static boolean shouldSpawn(float timePassed, float allTime) {
        if (timePassed * 4 < allTime) return false;
        System.err.println();
        if (timePassed - lastSpawnTime > 10) {
            numOfSpawns = 0;
            return true;
        }
        int num = (int) ((4 * timePassed - allTime) / 30) + 1;
        if (num <= numOfSpawns) return false;
        return true;
    }

    public static Enemy trySpawn(float timePassed, float allTime, Player player) {
        if (!shouldSpawn(timePassed, allTime)) return null;
        lastSpawnTime = timePassed;
        numOfSpawns++;

        float angle = (float) (Math.random() * Math.PI * 2);
        float x = player.getX() + (float) Math.cos(angle) * Enemy.DIST;
        float y = player.getY() + (float) Math.sin(angle) * Enemy.DIST;

        return new EyeMonster(new Vector(x, y));
    }
}
