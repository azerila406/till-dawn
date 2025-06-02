package com.tilldawn.model.enemy;

import com.tilldawn.model.Vector;
import com.tilldawn.model.game.Player;

public class ElderMonster extends Enemy {
    public ElderMonster(Vector pos) {
        super(pos, EnemyType.ELDER);
    }

    private static float lastSpawnTime = 0f;

    public static boolean shouldSpawn(float timePassed, float allTime) {
        if (timePassed * 2 < allTime) return false;
        if (timePassed - lastSpawnTime > 30) {
            return true;
        }
        return false;
    }

    public static Enemy trySpawn(float timePassed, float allTime, Player player, boolean check) {
        if (check && !shouldSpawn(timePassed, allTime)) return null;
        lastSpawnTime = timePassed;

        float angle = (float) (Math.random() * Math.PI * 2);
        float x = player.getX() + (float) Math.cos(angle) * DIST;
        float y = player.getY() + (float) Math.sin(angle) * DIST;

        return new ElderMonster(new Vector(x, y));
    }
}
