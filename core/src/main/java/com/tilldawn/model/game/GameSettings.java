package com.tilldawn.model.game;

import com.tilldawn.model.texture.Heros;

public class GameSettings {
    public final float totalTime;
    public final WeaponType weaponType;
    public final Heros heroType;

    public GameSettings(int totalTime, WeaponType type, Heros heroType) {
        this.totalTime = totalTime * 60;
        this.weaponType = type;
        this.heroType = heroType;
    }

    public static GameSettings createDefault() {
        return new GameSettings(120, WeaponType.REVOLVER, Heros.DASHER);
    }
}
