package com.tilldawn.model.Ability;

import java.io.Serializable;

public enum AbilityType implements Serializable {
    VITALITY(new VitalityCommand(), Float.MAX_VALUE),
    DAMAGER(new DamagerCommand(), 10f),
    PROCREASE(new ProcreaseCommand(), Float.MAX_VALUE),
    AMOCREASE(new AmmoCreaseCommand(), Float.MAX_VALUE),
    SPEEDY(new SpeedyCommand(), 10f),
    ;

    public final Command command;
    public final float duration;

    AbilityType(Command command, float duration) {
        this.command = command;
        this.duration = duration;
    }
}
