package com.tilldawn.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.model.enemy.EnemyType;
import com.tilldawn.model.game.State;
import com.tilldawn.model.game.WeaponType;
import com.tilldawn.model.sounds.GameSound;
import com.tilldawn.model.texture.Heros;
import com.tilldawn.model.texture.Textures;

public class Assets {
    private static final AssetManager manager = new AssetManager();
    private static Skin skin;

    private Assets() {}

    public static void load() {
        manager.load("pixthulhu-ui.json", Skin.class);
        manager.load("background.png", Texture.class);

        for (GameSound gameSound: GameSound.values())
            manager.load(gameSound.getPath(), Sound.class);

        for (Heros hero : Heros.values()) {
            hero.queueAssets(manager);
        }

        WeaponType.queueAssets(manager);
        Textures.queueAssets(manager);
        EnemyType.queueAssets(manager);
    }

    public static void finishLoading() {
        manager.finishLoading();
        skin = manager.get("pixthulhu-ui.json", Skin.class);

        for (GameSound gameSound: GameSound.values()) {
            gameSound.setSound(manager.get(gameSound.getPath(), Sound.class));
        }
    }

    public static Texture getBackground() {
        return manager.get("background.png", Texture.class);
    }

    public static <T> T get(String path, Class<T> type) {
        return manager.get(path, type);
    }

    public static Skin getSkin() {
        return skin;
    }

    public static boolean update() {
        return manager.update();
    }

    public static float getProgress() {
        return manager.getProgress();
    }

    public static void dispose() {
        manager.dispose();
    }

    public static float getVolume() {
        return 1.0f;
    }

    public static Animation<TextureRegion> getHeroAnimation(Heros hero, State state, float frameDuration) {
        return hero.createAnimation(state, frameDuration, manager);
    }

}
