package com.tilldawn.model.texture;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.model.game.State;

import java.util.ArrayList;
import java.util.List;

import static com.tilldawn.model.game.State.WALK;

public enum Heros {
    DASHER("Dasher", 6, 4, 0),
    DIAMOND("Diamond", 6, 4, 8),
    LILITH("Lilith", 6, 4, 8),
    SCARLET("Scarlet", 6, 4, 0),
    SHANA("Shana", 6, 4, 6);

    private final String folderName;
    private final int idleCount, runCount, walkCount;

    Heros(String folderName, int idleCount, int runCount, int walkCount) {
        this.folderName = folderName;
        this.idleCount = idleCount;
        this.runCount = runCount;
        this.walkCount = walkCount;
    }

    public void queueAssets(AssetManager mgr) {
        for (State st : State.values()) {
            int count = getFrameCount(st);
            for (int i = 0; i < count; i++) {
                mgr.load(getPath(st, i), Texture.class);
            }
        }
    }

    public Animation<TextureRegion> createAnimation(State state, float frameDuration, AssetManager mgr) {
        int count = getFrameCount(state);
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < count; i++) {
            Texture tex = mgr.get(getPath(state, i), Texture.class);
            frames.add(new TextureRegion(tex));
        }
        return new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

    private String getPath(State state, int index) {
        return String.format("heros/%s/%s/%s_%d.png", folderName, state.getSubfolder(), state.getPrefix(), index);
    }

    private int getFrameCount(State state) {
        switch (state) {
            case IDLE: return idleCount;
            case RUN:  return runCount;
            case WALK: return walkCount;
            default:   return 0;
        }
    }

    public String toString() {
        return name().toLowerCase();
    }
}

