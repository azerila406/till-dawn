package com.tilldawn.model.Ability;

import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Player;
import com.tilldawn.model.game.Weapon;

public class DamagerCommand implements Command {
    @Override
    public void execute(Game game) {
        Weapon weapon = game.getPlayer().getWeapon();
        weapon.setDamage(weapon.getDamage() * 2);
    }

    @Override
    public void end(Game game) {
        Weapon weapon = game.getPlayer().getWeapon();
        weapon.setDamage(weapon.getDamage() / 2);
    }
}
