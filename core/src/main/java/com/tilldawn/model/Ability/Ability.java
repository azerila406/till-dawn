package com.tilldawn.model.Ability;

import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Player;

import java.io.Serializable;

public class Ability implements Serializable {
    public Command command;
    public float activatedTime;
    public float duration;
    private boolean isDead = false;
    public final AbilityType abilityType;


    public Ability(AbilityType abilityType, float activatedTime) {
        this.command = abilityType.command;
        this.activatedTime = activatedTime;
        this.duration = abilityType.duration;
        this.abilityType = abilityType;
    }

    public void execute(Game game) {
        command.execute(game);
    }

    public void update(Game game) {
        if (game.timePassed - activatedTime > duration) {
            command.end(game);
            isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public String toString() {
        return abilityType.name();
    }
}
