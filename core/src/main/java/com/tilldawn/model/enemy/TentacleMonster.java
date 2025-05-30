package com.tilldawn.model.enemy;

import com.tilldawn.model.Vector;
import com.tilldawn.model.game.Player;

public class TentacleMonster extends Enemy {

    private static float lastSpawnTime = 0f;
    private static int numOfSpawns = 0;

    public TentacleMonster(Vector pos) {
        super(pos, EnemyType.TENTACLE_MONSTER);
    }

    public static boolean shouldSpawn(float timePassed) {
        if (timePassed - lastSpawnTime > 3) {
            numOfSpawns = 0;
            return true;
        }
        int num = (int) (timePassed / 30) + 1;
        if (num == numOfSpawns) return false;
        return true;
    }

    public static Enemy trySpawn(float timePassed, Player player) {
        if (!shouldSpawn(timePassed)) return null;
        lastSpawnTime = timePassed;
        numOfSpawns++;

        float angle = (float) (Math.random() * Math.PI * 2);
        float x = player.getX() + (float) Math.cos(angle) * DIST;
        float y = player.getY() + (float) Math.sin(angle) * DIST;

        return new TentacleMonster(new Vector(x, y));
    }
}
