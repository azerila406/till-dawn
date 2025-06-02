package com.tilldawn.model.game;

import com.tilldawn.model.texture.Heros;

import java.io.Serializable;

public class GameSettings implements Serializable {
    public float totalTime;
    public WeaponType weaponType;
    public Heros heroType;
    public boolean autoReload = false;
    public boolean sfx = true;
    public float musicVolume = 1f;
    public boolean isMusicEnabled = true;

    public GameSettings() {
    }

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime * 60;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public Heros getHeroType() {
        return heroType;
    }

    public void setHeroType(Heros heroType) {
        this.heroType = heroType;
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }
}
