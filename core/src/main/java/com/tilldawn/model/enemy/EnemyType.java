package com.tilldawn.model.enemy;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.model.Assets;
import com.tilldawn.model.texture.Textures;

public enum EnemyType {
    TREE(Integer.MAX_VALUE, "tree", 64, 64, 5),
    TENTACLE_MONSTER(25, "tentacle", 32, 32, 6),
    EYEBAT(50, "eye", 32, 32, 3),
    ELDER(400, "elder", 32, 32, 0),;

    public final int HP;
    public final String file;
    public final float width, height;
    public final int numberOfFrames;

    EnemyType(int HP, String file, float width, float height, int numberOfFrames) {
        this.HP = HP;
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
            case TENTACLE_MONSTER, EYEBAT -> 1;
            case ELDER ->  (float)(Math.sin(passed / totalGame * Math.PI * 2) * 0.5f + 0.5f);
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
