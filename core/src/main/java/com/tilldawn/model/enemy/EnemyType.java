package com.tilldawn.model.enemy;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.model.Assets;

public enum EnemyType {
    TREE(Integer.MAX_VALUE, 0, "tree", 64, 64, 5),
    TENTACLE_MONSTER(25, 5, "tentacle", 32, 32, 6),
    EYEBAT(50, 10, "eye", 32, 32, 3),
    ELDER(400, 100, "elder", 128, 128, 5),;

    public final int HP;
    public final int award;
    public final String file;
    public final float width, height;
    public final int numberOfFrames;

    EnemyType(int HP, int award, String file, float width, float height, int numberOfFrames) {
        this.HP = HP;
        this.award = award;
        this.file = file;
        this.width = width;
        this.height = height;
        this.numberOfFrames = numberOfFrames;
    }


    public static void queueAssets(AssetManager mgr) {
        for (EnemyType type : values()) {
            for (int i = 0; i < type.numberOfFrames; i++) {
                mgr.load(type.getFile(i), Texture.class);
            }
        }
    }

    public float getSpeed(float totalGame, float passed) {
        return 50 * switch(this) {
            case TREE -> 0;
            case TENTACLE_MONSTER -> 1;
            case EYEBAT -> 2;
            case ELDER -> (float)((Math.sin(passed * 5) > 0.95) ? 40f : 0.3f);
        };
    }

    public float getRawSpeed() {
        return switch (this) {
            case TREE -> 0;
            default -> 1;
        };
    }

    public String getParent() {
        return "enemy/" + file + "/" + file;
    }

    public String getFile(int frame) {
        return getParent() + frame + ".png";
    }

    public Animation<TextureRegion> createAnimation(float time) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < numberOfFrames; i++) {
            Texture tex = Assets.get(getFile(i), Texture.class);
            frames.add(new TextureRegion(tex));
        }
        return new Animation<>(time, frames, Animation.PlayMode.LOOP);
    }
}
