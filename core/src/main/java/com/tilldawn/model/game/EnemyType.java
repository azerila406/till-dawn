package com.tilldawn.model.game;

public enum EnemyType {
    TREE(Integer.MAX_VALUE),
    TENTACLE_MONSTER(25), 
    EYEBAT(50),
    ELDER(400);

    private final int HP;

    EnemyType(int HP) {
        this.HP = HP;
    }

    public float getSpeed(float totalGame, float passed) {
        return switch(this) {
            case TREE -> 0;
            case TENTACLE_MONSTER, EYEBAT -> 1;
            case ELDER ->  (float)(Math.sin(passed / totalGame * Math.PI * 2) * 0.5f + 0.5f);
        };
    }
}
