package com.tilldawn.model.enemy;

import com.tilldawn.model.Vector;
import com.tilldawn.model.game.Player;

public class Tree extends Enemy{

    public Tree(Vector pos) {
        super(pos, EnemyType.TREE);
    }

    @Override
    public float getSpeed(float totalGame, float passed) {
        return 0;
    }

    public static Enemy trySpawn() {
        float x = -20000 + (float) Math.random() * 40000;
        float y = -20000 + (float) Math.random() * 40000;
        return new Tree(new Vector(x, y));
    }
}
