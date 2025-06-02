package com.tilldawn.model.sounds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Sound;
import com.tilldawn.model.Assets;
import com.tilldawn.model.user.UserRepository;

public enum GameSound {
    CLICK("click.wav"),
    GUN_COCK("gun-cock.wav"),
    GUNSHOT("gunshot.wav"),
    ;

    private final String path;
    private Sound sound;

    GameSound(String path) {
        this.path = "sound-effects/" + path;
    }

    public String getPath() {
        return path;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void play(float volume) {
        if (sound != null)
            sound.play(volume);
    }

    public void dispose() {
        if (sound != null) sound.dispose();
    }
}
